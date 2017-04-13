package com.falconhang.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.scroll_left).setOnClickListener(MainActivity.this);
        findViewById(R.id.scroll_right).setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scroll_left: {
                Intent intent = new Intent(MainActivity.this, MainLeftScrollActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.scroll_right: {
                Intent intent = new Intent(MainActivity.this, MainRightScrollActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}