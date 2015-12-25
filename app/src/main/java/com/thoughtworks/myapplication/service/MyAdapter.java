package com.thoughtworks.myapplication.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thoughtworks.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhihu on 15/12/24.
 */
public class MyAdapter extends ArrayAdapter<String> {
    protected static final int NO_SELECTED_COLOR = 0xFF191919;
    protected static final int SELECTED_COLOR = 0xFF3366CC;

    protected static final String OUTSTANDING = "优";
    protected static final String GOOD = "良";
    protected static final String MEDIUM = "轻度污染";
    protected static final String POOR = "中度污染";
    protected static final String SERIOUS = "严重污染";
    protected static final String UNKNOWN = "未知";

    protected static final String OUTSTANDING_COLOR = "#8BC34A";
    protected static final String GOOD_COLOR = "#CDDC39";
    protected static final String MEDIUM_COLOR = "#FF4081";
    protected static final String POOR_COLOR = "#FF5722";
    protected static final String SERIOUS_COLOR = "#D32F2F";
    protected static final String UNKNOWN_COLOR = "#03A9F4";

    private ArrayList<String> items;
    private LayoutInflater mInflater;
    private int viewResourceId;
    private int selectedPosition;


    public MyAdapter(Activity activity,int resourceId,
                         ArrayList<String> list) {
        super(activity,resourceId,list);

        // Sets the layout inflater
        mInflater = (LayoutInflater)activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Set a copy of the layout to inflate
        viewResourceId = resourceId;

        // Set a copy of the list
        items = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView)convertView;
        if (tv == null) {
            tv = (TextView)mInflater.inflate(viewResourceId, null);
        }
        tv.setText(items.get(position));

        // Change the background color
//        if (position==selectedPosition) tv.setBackgroundColor(SELECTED_COLOR);
//        else tv.setBackgroundColor(NO_SELECTED_COLOR);

        return initColor(tv,position);
    }

    public void setSelected(int position) {
        selectedPosition = position;
    }

    public TextView initColor(TextView tv,int position){
        String content[] = items.get(position).split("    ");
        String quality = content[1];
        if (quality.equals(OUTSTANDING)) tv.setBackgroundColor(Color.parseColor(OUTSTANDING_COLOR));
        else if (quality.equals(GOOD)) tv.setBackgroundColor(Color.parseColor(GOOD_COLOR));
        else if (quality.equals(MEDIUM)) tv.setBackgroundColor(Color.parseColor(MEDIUM_COLOR));
        else if (quality.equals(POOR)) tv.setBackgroundColor(Color.parseColor(POOR_COLOR));
        else if (quality.equals(SERIOUS)) tv.setBackgroundColor(Color.parseColor(SERIOUS_COLOR));
        else if (quality.equals(UNKNOWN)) tv.setBackgroundColor(Color.parseColor(UNKNOWN_COLOR));
        return tv;
    }
}
