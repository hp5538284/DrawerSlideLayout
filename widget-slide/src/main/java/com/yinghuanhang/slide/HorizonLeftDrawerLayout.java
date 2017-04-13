package com.yinghuanhang.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.yinghuanhang.slide.helper.DrawerDragHelper;

/**
 * Created by Cao-Human on 2017/2/28
 */
public class HorizonLeftDrawerLayout extends HorizonDrawerLayout {
    public HorizonLeftDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizonLeftDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizonLeftDrawerLayout(Context context) {
        super(context);
    }

    /**
     * 人为切换
     *
     * @param state 切换的状态
     */
    public void onChangeDrawerOpenByUser(int state) {
        DrawerDragHelper helper = getDragHelper();
        helper.setTarget(state == DRAWER_OPEN ? getDrawerMargin() : 0);
        helper.setStart(getContentParam().leftMargin, getContentParam().leftMargin > helper.getTarget() ? -5 : 5);
        onRunning(true);
    }

    @Override
    public boolean onScroll(MotionEvent e, MotionEvent ev, float dx, float dy) {
        if (getDragState() != DRAG_SCROLL) {
            return false;
        }
        onSliding(getContentParam().leftMargin - (int) dx);
        return true;
    }

    @Override
    public boolean onDown(View view, MotionEvent event) {
        View content = getContentView();
        int area = getArea();
        if (event.getX() >= content.getLeft() - area && event.getX() <= content.getLeft() + area) {
            getDragHelper().setPosition((int) event.getX());
            onDragStateChange(DRAG_SCROLL);
            return true;
        }
        return false;
    }

    @Override
    public void onTouchUp(MotionEvent event) {
        DrawerDragHelper helper = getDragHelper();
        FrameLayout.LayoutParams params = getContentParam();
        int area = getArea() / 3;

        if (getDrawerState() == DRAWER_OPEN) {
            if (event.getX() - helper.getPosition() < -area) {
                helper.setTarget(0);
                helper.setStart(params.leftMargin, -5);
            } else {
                helper.setTarget(getDrawerMargin());
                helper.setStart(params.leftMargin, 5);
            }
            onRunning(true);
            return;
        }
        if (getDrawerState() == DRAWER_OFF) {
            if (event.getX() - helper.getPosition() > area) {
                helper.setTarget(getDrawerMargin());
                helper.setStart(params.leftMargin, 5);
            } else {
                helper.setTarget(0);
                helper.setStart(params.leftMargin, -5);
            }
            onRunning(true);
        }
    }

    @Override
    public void onRunning(FrameLayout.LayoutParams params) {
        if (params.leftMargin == getDrawerMargin()) {
            onDrawerStateChange(DRAWER_OPEN);
            return;
        }
        if (params.leftMargin == 0) {
            onDrawerStateChange(DRAWER_OFF);
        }
    }

    @Override
    public void running(int velocity) {
        int value = getContentParam().leftMargin + velocity;
        boolean on = onSliding(value);
        onRunning(on);
    }

    /**
     * 拖动内容视图
     *
     * @param params 内容视图布局参数
     * @param margin 布局边距
     */
    public void onSlidingContent(FrameLayout.LayoutParams params, int margin) {
        if (params.leftMargin != margin) {
            params.leftMargin = margin;
            requestLayout();
        }
    }

    /**
     * 拖动内容视图
     *
     * @param margins 当前距离
     */
    public boolean onSliding(int margins) {
        FrameLayout.LayoutParams params = getContentParam();
        if (margins <= 0) {
            onSlidingContent(params, 0);
            return false;
        }
        if (margins >= getDrawerMargin()) {
            onSlidingContent(params, getDrawerMargin());
            return false;
        }
        onSlidingContent(params, margins);
        return true;
    }
}