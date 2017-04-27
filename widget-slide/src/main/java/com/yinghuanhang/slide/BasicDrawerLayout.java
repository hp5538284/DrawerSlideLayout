package com.yinghuanhang.slide;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.yinghuanhang.slide.helper.DrawerDragCallback;
import com.yinghuanhang.slide.helper.DrawerDragHelper;
import com.yinghuanhang.slide.ignore.IgnoreRect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cao-Human on 2017/2/28
 */
public abstract class BasicDrawerLayout extends FrameLayout implements GestureDetector.OnGestureListener, DrawerDragCallback {
    public BasicDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(R.styleable.BasicDrawerLayout);
        int max = array.getDimensionPixelOffset(R.styleable.BasicDrawerLayout_drawer_max_margin, 0);
        if (max > 0) {
            setMaxMargin(max);
        }
        array.recycle();

        mDragHelper = new DrawerDragHelper(BasicDrawerLayout.this);
        mDetector = new GestureDetector(context, BasicDrawerLayout.this);
    }

    public BasicDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicDrawerLayout(Context context) {
        this(context, null, 0);
    }

    /*
     * ######################################## 内容视图布局 ########################################
     */
    private LayoutParams mContentLayout;
    private View mContentView;
    private View mMenuView;
    private View mDrawerView;

    public void setContentView(View view) {
        if (mContentView != null) {
            removeView(mContentView);
            mContentView = null;
        }
        if (view != null) {
            mContentLayout = generateDefaultLayoutParams();
            mContentView = view;
            addView(mContentView, mContentLayout);
        }
    }

    public void setMenuView(View v, int id) {
        if (mMenuView != null) {
            removeView(mMenuView);
            mMenuView = null;
        }
        if (v != null) {
            mMenuView = v;
            mDrawerView = v.findViewById(id);
            addView(mMenuView, 0);
        }
        LayoutParams params = mDrawerView != null ? (LayoutParams) mDrawerView.getLayoutParams() : null;
        if (params != null && params.width > 0) {
            setDrawerMargin(params.width);
        }
    }

    public void setMenuView(View v) {
        if (mMenuView != null) {
            removeView(mMenuView);
            mMenuView = null;
        }
        if (v != null) {
            mMenuView = v;
            mDrawerView = v;
            addView(mMenuView, 0);
        }
        LayoutParams params = mDrawerView != null ? (LayoutParams) mDrawerView.getLayoutParams() : null;
        if (params != null && params.width > 0) {
            setDrawerMargin(params.width);
        }
    }

    public LayoutParams getContentParam() {
        return mContentLayout;
    }

    public View getContentView() {
        return mContentView;
    }

    public View inflate(LayoutInflater inflater, int layout) {
        return inflater.inflate(layout, BasicDrawerLayout.this, false);
    }

    private int mDrawerMargin;
    private int mMaxMargin;
    private int mArea;

    public void setDrawerMargin(int margin) {
        mDrawerMargin = margin;
        setArea(margin / 6);
    }

    public int getDrawerMargin() {
        return mDrawerMargin;
    }

    public void setArea(int area) {
        mArea = area > 45 ? area : 45;
    }

    public int getArea() {
        return mArea;
    }

    public void setMaxMargin(int max) {
        mMaxMargin = max;
    }

    public int getMaxMargin() {
        return mMaxMargin;
    }

    /*
     * ######################################## 视图状态监听 ########################################
     */
    public static final int DRAWER_OFF = 0, DRAWER_OPEN = 1, DRAWER_EXPAND = 2;
    public static final int DRAG_IDLE = 0, DRAG_SCROLL = 1, DRAG_ROLLING = 2;
    private int mDrawerState = DRAWER_OFF, mDragState = DRAG_IDLE;

    private DrawerStateChangeListener mStateChangeListener;

    public void setDrawerStateChangeListener(DrawerStateChangeListener listener) {
        mStateChangeListener = listener;
    }

    public void onDrawerStateChange(int state) {
        if (mDrawerState == state) {
            return;
        }
        mDrawerState = state;
        if (mStateChangeListener != null) {
            mStateChangeListener.onDrawerStateChange(state);
        }
    }

    public void onDragStateChange(int state) {
        if (mDragState == state) {
            return;
        }
        mDragState = state;
        if (mStateChangeListener != null) {
            mStateChangeListener.onDragStateChange(state);
        }
    }

    public int getDrawerState() {
        return mDrawerState;
    }

    public int getDragState() {
        return mDragState;
    }

    public interface DrawerStateChangeListener {
        void onDrawerStateChange(int state);

        void onDragStateChange(int state);
    }

    /*
     * ######################################## 视图触屏事件 ########################################
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isEnableSliding() && !isInsideIgnore(event) && mDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDragState != DRAG_SCROLL || isInsideIgnore(event)) {
            return false;
        }
        if (MotionEvent.ACTION_UP == event.getAction()) {
            onTouchUp(event);
            return true;
        }
        return mDetector.onTouchEvent(event);
    }

    /**
     * 手势离开
     *
     * @param event 触屏事件
     */
    public abstract void onTouchUp(MotionEvent event);

    /*
     * ######################################## 滑动是否可用 ########################################
     */
    private List<IgnoreRect> mIgnores = new ArrayList<>();
    private boolean mIsEnable = true;

    /**
     * @param enable 设置是否可滑动
     */
    public void setSlidingEnable(boolean enable) {
        mIsEnable = enable;
    }

    /**
     * @return 是否启用滑动
     */
    public boolean isEnableSliding() {
        return mIsEnable && mContentView != null;
    }

    /**
     * 设置事件忽略视图
     *
     * @param ignores 需要忽略的视图区域
     */
    public void setIgnores(List<IgnoreRect> ignores) {
        mIgnores.clear();
        if (ignores != null) {
            mIgnores.addAll(ignores);
        }
    }

    /**
     * 添加事件忽略视图
     *
     * @param ignore 需要忽略的视图区域
     */
    public void addIgnore(IgnoreRect ignore) {
        if (ignore != null) {
            mIgnores.add(ignore);
        }
    }

    /**
     * 事件是否落在忽略的视图对象区域
     */
    public boolean isInsideIgnore(MotionEvent event) {
        for (IgnoreRect rect : mIgnores) {
            if (rect.isInside(event)) {
                return true;
            }
        }
        return false;
    }

    /*
     * ######################################## 自动滚动任务 ########################################
     */
    private DrawerDragHelper mDragHelper;

    public DrawerDragHelper getDragHelper() {
        return mDragHelper;
    }

    public void onRunning(boolean on) {
        if (on) {
            onDragStateChange(DRAG_ROLLING);
            postDelayed(mDragHelper, 10);
            return;
        }
        mDragHelper.reset();
        onRunning(mContentLayout);
        onDragStateChange(DRAG_IDLE);
    }

    public abstract void onRunning(LayoutParams params);

    /*
     * ######################################## 触屏手势处理 ########################################
     */
    private GestureDetector mDetector;

    @Override
    public boolean onFling(MotionEvent e, MotionEvent ev, float vx, float vy) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (mContentView == null) {
            return false;
        }
        if (mDrawerMargin == 0 && mDrawerView != null) {
            setDrawerMargin(mDrawerView.getWidth());
        }
        if (mContentLayout.width == LayoutParams.MATCH_PARENT) {
            mContentLayout.width = mContentView.getWidth();
        }
        return onDown(mContentView, e);
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public void onLongPress(MotionEvent e) {}

    public abstract boolean onDown(View view, MotionEvent event);
}