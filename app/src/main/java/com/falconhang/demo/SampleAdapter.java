package com.falconhang.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cao-Human on 2017/4/12
 */

public class SampleAdapter extends BaseAdapter {
    public SampleAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        for (int i = 0; i < 60; i++) {
            datas.add("item on line : " + i);
        }
    }

    private List<String> datas = new ArrayList<>();
    private LayoutInflater mInflater;

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_sample_textview, parent, false);
        }
        ((TextView) convertView).setText(getItem(position));
        return convertView;
    }
}
