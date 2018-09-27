package com.example.android.newsapps1;

public class News {

    private String mSection;

    private String mDate;

    private String mTitle;

    private String mURL;

    /**
     * Create a new News Object
     *
     * @param section The Section the article comes from
     * @param date    The Date the article was published
     * @param title   The title of the article
     * @param url     The website URL to read the full article
     */
    public News(String section, String date, String title, String url) {
        mSection = section;
        mDate = date;
        mTitle = title;
        mURL = url;
    }

    public String getSection() {
        return mSection;
    }

    public String getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getURL() {
        return mURL;
    }
}