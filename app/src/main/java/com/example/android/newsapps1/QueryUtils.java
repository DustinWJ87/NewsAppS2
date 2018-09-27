package com.example.android.newsapps1;

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

/**
 * Helper method for requesting and receiving news data from the Guardian API
 */
public final class QueryUtils {

    public static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * Return a list of News objects that has been built up from parsing a JSON response
     */
    public static List<News> fetchNewsData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making HTTP request, e");
        }

        List<News> news = extractNewsFromJson(jsonResponse);

        return news;
    }

    /**
     * Returns a new URL object from the given string URL
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the InputStream into a String which contains the whole JSON response
     */
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

    /**
     * Parse the JSON response into the pieces of information that are being called and then
     * displayed within the app
     */
    private static List<News> extractNewsFromJson(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> news = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(newsJSON);
            JSONObject newsObj = jsonObj.getJSONObject("response");
            JSONArray newsArray = newsObj.getJSONArray("results");

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject newsData = newsArray.getJSONObject(i);
                String section = newsData.getString("sectionName");
                String date = newsData.getString("webPublicationDate");
                String title = newsData.getString("webTitle");
                String url = newsData.getString("webUrl");

                News data = new News(section, date, title, url);
                news.add(data);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results");
        }
        return news;
    }
}