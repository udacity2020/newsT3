package com.example.newst3;

public class News {

    public String id;
    public String type;
    public String sectionId;
    public String sectionName;
    public String webPublicationDate;
    public String webTitle;
    public String webUrl;
    public String apiUrl;
    public Boolean isHosted;
    public String pillarId;
    public String pillarName;
    public String author;

    public News(String id, String type, String sectionId, String sectionName, String webPublicationDate, String webTitle, String webUrl, String apiUrl, Boolean isHosted, String pillarId, String pillarName, String author) {
        this.id = id;
        this.type = type;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.apiUrl = apiUrl;
        this.isHosted = isHosted;
        this.pillarId = pillarId;
        this.pillarName = pillarName;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public Boolean getHosted() {
        return isHosted;
    }

    public String getPillarId() {
        return pillarId;
    }

    public String getPillarName() {
        return pillarName;
    }

    public String getAuthor () {
        return author;
    }
}
