package com.example.android.newsapps1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Create a new NewsAdapter object
     *
     * @param context is the current context that the adapter is being created in
     * @param news    is the information to be displayed from the JSON parsing
     */
    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_view, parent, false);
        }

        News currentNews = getItem(position);

        TextView sectionView = (TextView) convertView.findViewById(R.id.section);
        sectionView.setText(currentNews.getSection());

        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        dateView.setText(currentNews.getDate());

        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        titleView.setText(currentNews.getTitle());

        return convertView;
    }
}