package com.example.newst3;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;


import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    //Tag for log messages
    private static final String LOG_TAG = NewsLoader.class.getName();

    //Query URL
    private String mUrl;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<News> newsList = QueryUtils.fetchNewsData(mUrl);
        return newsList;
    }
}
