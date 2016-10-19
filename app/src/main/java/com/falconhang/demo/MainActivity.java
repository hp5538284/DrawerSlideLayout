package com.falconhang.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.yinghuanhang.slide.SlideDrawerHorizonLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = (SlideDrawerHorizonLayout) findViewById(R.id.base_slide);
        onInitializeView(getLayoutInflater());
    }

    private void onInitializeView(LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.activity_main_content, null);
        mDrawer.setContentView(content);

        View menu = inflater.inflate(R.layout.activity_main_menu, null);
        mDrawer.setSlideMenu(menu, 0.3f);
    }

    private SlideDrawerHorizonLayout mDrawer;
}
