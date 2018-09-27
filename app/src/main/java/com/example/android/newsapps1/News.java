package com.example.android.newsapps1;

public class News {

    private String Section;

    private String Date;

    private String Title;

    private String URL;

    private String Author;

    /**
     * Create a new News Object
     *
     * @param section The Section the article comes from
     * @param date    The Date the article was published
     * @param title   The title of the article
     * @param url     The website URL to read the full article
     */
    public News(String section, String date, String title, String url, String author) {
        Section = section;
        Date = date;
        Title = title;
        URL = url;
        Author = author;
    }


    public String getSection() {
        return Section;
    }

    public String getDate() {
        return Date;
    }

    public String getTitle() {
        return Title;
    }

    public String getURL() {
        return URL;
    }

    public String getAuthor(){
        return Author;
    }
}