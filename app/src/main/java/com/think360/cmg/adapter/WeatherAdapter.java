package com.think360.cmg.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.think360.cmg.R;
import com.think360.cmg.model.work.Data;

import java.util.List;

/**
 * Created by think360 on 01/05/17.
 */

public class WeatherAdapter extends BaseAdapter {

    private Context context;
    private List<Data> workHistories;

    public WeatherAdapter(Context context, List<Data> data) {
        this.context = context;
        this.workHistories = data;
    }

    @Override
    public int getCount() {
        return workHistories.size();
    }

    @Override
    public Object getItem(int position) {
        return workHistories.get(position);
    }

    @Override
    public long getItemId(int position) {
         return workHistories.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WeatherHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.single_item_adapter, parent, false);
            holder = new WeatherHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (WeatherHolder) convertView.getTag();
        }

        Data data = workHistories.get(position);
        holder.txtTitle.setText(data.getProjectName());

        return convertView;
    }

    static class WeatherHolder {
        TextView txtTitle;
    }
}
