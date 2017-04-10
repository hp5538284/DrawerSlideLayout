package com.falconhang.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yinghuanhang.slide.BasicDrawerLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linear = (LinearLayout) findViewById(R.id.main_linear);
        LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < linear.getChildCount(); i++) {
            BasicDrawerLayout drawer = (BasicDrawerLayout) linear.getChildAt(i);
            View content = drawer.inflate(inflater, R.layout.activity_main_content);
            drawer.setContentView(content);

            int layout = i > 1 ? R.layout.activity_main_expand : R.layout.activity_main_menu;
            LinearLayout menu = (LinearLayout) drawer.inflate(inflater, layout);
            if (i == 3) {
                menu.setGravity(Gravity.END);
                drawer.setMenuView(menu, R.id.main_expand);
            } else if (i == 2) {
                drawer.setMenuView(menu, R.id.main_expand);
            } else {
                drawer.setMenuView(menu);
            }
        }
    }
}