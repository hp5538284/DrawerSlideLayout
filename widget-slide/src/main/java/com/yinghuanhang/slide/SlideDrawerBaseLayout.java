package com.yinghuanhang.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Cao-Human on 2016/8/15
 */
public class SlideDrawerBaseLayout extends FrameLayout implements GestureDetector.OnGestureListener {
    public SlideDrawerBaseLayout(Context context) {
        this(context, null, 0);
    }

    public SlideDrawerBaseLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDrawerBaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDetector = new GestureDetector(context, SlideDrawerBaseLayout.this);
    }

    /**
     * @param ev 系统触屏事件
     * @return 是否消耗
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnableSliding()) {
            return false;
        }
        if (mDetector.onTouchEvent(ev)) {
            return true;
        }
        // 滑动不可用则不拦截Touch事件
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnableSliding()) {
            return false;   // 当前不可滑动
        }
        if (mDetector.onTouchEvent(event)) {
            return true;    // 滑动事件被手势处理消耗
        }
        if (MotionEvent.ACTION_UP == event.getAction() || MotionEvent.ACTION_CANCEL == event.getAction()) {
            onTouchUp(event);
            return true;
        }
        return false;
    }

    /**
     * @param event 手势离开屏幕
     */
    protected void onTouchUp(MotionEvent event) {}

    // 滑动是否可用
    private boolean mIsEnable = true;

    /**
     * @param enable 是否可滑动
     */
    public void setSlidingEnable(boolean enable) {
        mIsEnable = enable;
    }

    /**
     * @return 是否启用滑动
     */
    public boolean isEnableSliding() {
        return mIsEnable;
    }

    // 触屏手势处理
    private GestureDetector mDetector;

    @Override
    public void onShowPress(MotionEvent e) {
        // 短按事件，手势还在
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // 长按事件
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * 侧滑栏滑动的状态
     */
    public static final int SLIDE_OPEN = 1;         // 侧滑栏被打开
    public static final int SLIDE_EXPAND = 2;       // 侧滑栏被展开
    public static final int SLIDE_CLOSE = 3;        // 侧滑栏被关闭

    public interface SlideDrawerListener {
        void onSlideStateChange(int state);

        void onDragStart();
    }
}