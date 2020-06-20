package com.example.newst3;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }

    public static List<News> fetchNewsData(String requestUrl) {
        //Create URL object
        URL url = createUrl(requestUrl);

        //Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        //Extract relevant fields from the JSON response and create a list of {@link News}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        //Return the list of {@link News}s
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


    //Make an HTTP request to the given URL and return a String as the response
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonReponse = "";

        //If the URL is null, then return early.
        if (url == null) {
            return jsonReponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000); //milliseconds
            urlConnection.setConnectTimeout(15000); //milliseconds
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If the request was successful response code 200
            //Then read the input stream and parse the response.

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonReponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown
                inputStream.close();
            }
        }
        return jsonReponse;
    }

    //Convert the {@link InputStream} into a String which contains the whole JSON response from the server
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Return a list of {@link News} objects that has been built up from parsing the given JSON response.
    private static List<News> extractFeatureFromJson(String newsJSON) {
        //If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        //Create an empty ArrayList that we can start adding news to
        List<News> newsList = new ArrayList<>();

        //Try to parse the JSON response string. If there a problemo with the way the JSON
        //is formatted, a JSONException exception object will be thrown.
        //Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //Create a JSONOBject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            //Extract the JSONArray associated with the key called "results"
            //which represents a list of results (or news).
            JSONArray newsArray = baseJsonResponse.getJSONArray("results");

            //For each news in the newsArray, create an {@link News} object
            for (int i = 0; i < newsArray.length(); i++) {

                //Get a single news at position i within the list of news
                JSONObject currentNews = newsArray.getJSONObject(i);

                //For a given news, extract the JSONObject associated with the
                //key called "properties", which represents a list of all properties
                //for that news.
                //JSONObject properties = currentNews.getJSONObject("properties");

                //Extract the value for the key called "id"
                String id = currentNews.getString("id");

                //Extract the value for the key called "type"
                String type = currentNews.getString("type");

                //Extract the value for the key called "sectionId"
                String sectionId = currentNews.getString("sectionId");

                //Extract the value for the key called "sectionName"
                String sectionName = currentNews.getString("sectionName");

                //Extract the value for the key called "webPublicationDate"
                String webPublicationDate = currentNews.getString("webPublicationDate");

                //Extract the value for the key called "webTitle"
                String webTitle = currentNews.getString("webTitle");

                //Extract the value for the key called "webUrl"
                String webUrl = currentNews.getString("webUrl");

                //Extract the value for the key called "apiUrl"
                String apiUrl = currentNews.getString("apiUrl");

                //Extract the value for the key called "isHosted"
                boolean isHosted = currentNews.getBoolean("isHosted");

                //Extract the value for the key called "pillarId"
                String pillarId = currentNews.getString("pillarId");

                //Extract the value for the key called "pillarName"
                String pillarName = currentNews.getString("pillarName");

                //Extract the value for the key called "author"
                String author = currentNews.getJSONObject("tags").getString("webTitle");

                //Create a new {@link News} object with the given variables and url from the JSON response
                News news = new News(id, type, sectionId, sectionName, webPublicationDate, webTitle, webUrl, apiUrl, isHosted, pillarId, pillarName, author);


                // Add the new {@link news} to the list of news.
                newsList.add(news);
            }

        } catch (JSONException e) {
            //If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the mssage from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        //Return the list of news
        return newsList;
    }

}
