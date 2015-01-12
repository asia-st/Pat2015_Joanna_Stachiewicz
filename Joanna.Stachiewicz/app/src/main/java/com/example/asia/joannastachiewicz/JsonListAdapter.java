package com.example.asia.joannastachiewicz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.List;


public class JsonListAdapter extends ArrayAdapter<String> {
    private final Activity context;

    private final List<String> titles;
    private final List<String> descs;
    private final List<String> urls;

    public JsonListAdapter(Activity context,List<String> titles, List<String> descs, List<String> urls){
        super(context, R.layout.json_element, titles);
        this.context = context;
        this.titles = titles;
        this.descs = descs;
        this.urls = urls;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.json_element, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView titleTV = (TextView) rowView.findViewById(R.id.titleTextView);
        TextView descTV = (TextView) rowView.findViewById(R.id.descTextView);

        imageView.setImageResource(R.drawable.ic_launcher);
        titleTV.setText(titles.get(position));
        descTV.setText(descs.get(position));

        AQuery aq = new AQuery(rowView);
        aq.id(R.id.imageView).image(urls.get(position), true, true, 0, R.drawable.ic_launcher);

        return rowView;
    }
}
