package com.falconhang.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.yinghuanhang.slide.HorizonRightDrawerLayout;
import com.yinghuanhang.slide.ignore.IgnoreRect;

/**
 * Created by Cao-Human on 2017/4/12
 */

public class MainRightScrollActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_right_scroll);
        findViewById(R.id.main_right_scroll_enable).setOnClickListener(MainRightScrollActivity.this);
        findViewById(R.id.main_right_scroll_disable).setOnClickListener(MainRightScrollActivity.this);
        mHorizonRightDrawer = (HorizonRightDrawerLayout) findViewById(R.id.main_right_scroll_drawer);

        LayoutInflater inflater = getLayoutInflater();
        View content = mHorizonRightDrawer.inflate(inflater, R.layout.activity_main_right_scroll_content);
        ListView listView = (ListView) content.findViewById(R.id.main_right_scroll_list);
        listView.setAdapter(new SampleAdapter(MainRightScrollActivity.this));
        mHorizonRightDrawer.setContentView(content);

        View menu = mHorizonRightDrawer.inflate(inflater, R.layout.activity_main_right_scroll_menu);
        mHorizonRightDrawer.setMenuView(menu);

        mHorizonRightDrawer.addIgnore(new IgnoreRect(content.findViewById(R.id.main_right_scroll_ignore)));
    }

    @Override
    public void onClick(View v) {
        mHorizonRightDrawer.setSlidingEnable(v.getId() == R.id.main_left_scroll_enable);
    }

    private HorizonRightDrawerLayout mHorizonRightDrawer;
}