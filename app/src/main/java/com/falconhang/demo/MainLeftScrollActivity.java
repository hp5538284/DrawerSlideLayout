package com.falconhang.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.yinghuanhang.slide.ExpandsLeftDrawerLayout;

/**
 * Created by Cao-Human on 2017/4/12
 */

public class MainLeftScrollActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_left_scroll);
        findViewById(R.id.main_left_scroll_open).setOnClickListener(MainLeftScrollActivity.this);
        findViewById(R.id.main_left_scroll_enable).setOnClickListener(MainLeftScrollActivity.this);
        findViewById(R.id.main_left_scroll_disable).setOnClickListener(MainLeftScrollActivity.this);
        mExpandsLeftDrawer = (ExpandsLeftDrawerLayout) findViewById(R.id.main_left_scroll_drawer);

        LayoutInflater inflater = getLayoutInflater();
        View content = mExpandsLeftDrawer.inflate(inflater, R.layout.activity_main_left_scroll_content);
        ListView listView = (ListView) content.findViewById(R.id.main_left_scroll_list);
        listView.setAdapter(new SampleAdapter(MainLeftScrollActivity.this));
        mExpandsLeftDrawer.setContentView(content);

        View menu = mExpandsLeftDrawer.inflate(inflater, R.layout.activity_main_left_scroll_menu);
        mExpandsLeftDrawer.setMenuView(menu);
        mExpandsLeftDrawer.setDrawerMargin(0.3f);
    }

    @Override
    public void onClick(View v) {
        if (R.id.main_left_scroll_open == v.getId()) {
            int state = mExpandsLeftDrawer.getDrawerState();
            if (state == ExpandsLeftDrawerLayout.DRAWER_OFF) {
                mExpandsLeftDrawer.onChangeDrawerOpenByUser(ExpandsLeftDrawerLayout.DRAWER_OPEN);
            } else {
                mExpandsLeftDrawer.onChangeDrawerOpenByUser(ExpandsLeftDrawerLayout.DRAWER_OFF);
            }
            return;
        }
        mExpandsLeftDrawer.setSlidingEnable(v.getId() == R.id.main_left_scroll_enable);
    }

    private ExpandsLeftDrawerLayout mExpandsLeftDrawer;
}