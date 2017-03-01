package com.yinghuanhang.slide.ignore;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Cao-Human on 2016/12/15
 */
public class IgnoreRect {
    public IgnoreRect(View view) {
        mView = view;
    }

    private View mView;
    private int[] position = new int[2];

    public boolean isInside(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        mView.getLocationOnScreen(position);
        return position[0] < x && position[0] + mView.getWidth() > x && position[1] < y && position[1] + mView.getHeight() > y;
    }
}