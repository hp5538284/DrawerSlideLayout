package com.falconhang.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.yinghuanhang.slide.HorizonDrawerLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = (HorizonDrawerLayout) findViewById(R.id.base_slide);
        onInitializeView(getLayoutInflater());
    }

    private void onInitializeView(LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.activity_main_content, null);
        mDrawer.setContentView(content);
        mDrawer.setDrawerMargin(0.3f);

        View menu = inflater.inflate(R.layout.activity_main_menu, null);
        mDrawer.setMenuView(menu);
    }

    private HorizonDrawerLayout mDrawer;
}
