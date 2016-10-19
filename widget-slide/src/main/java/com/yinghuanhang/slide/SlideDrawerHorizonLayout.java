package com.yinghuanhang.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.yinghuanhang.slide.execute.HorizonExecutor;

/**
 * Created by Cao-Human on 2016/8/5
 */
public class SlideDrawerHorizonLayout extends SlideDrawerBaseLayout implements HorizonExecutor.Callback {
    public SlideDrawerHorizonLayout(Context context) {
        this(context, null, 0);
    }

    public SlideDrawerHorizonLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDrawerHorizonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    protected int mScreenWidth;
    protected int mParamsWidth;

    // 基础内容视图容器与布局参数
    protected View mBase;
    protected LayoutParams mParamsBase;

    /**
     * @param view 设置基本视图
     */
    public void setContentView(View view) {
        if (mBase != null) {
            removeView(mBase);
            mBase = null;
        }
        if (view != null) {
            mBase = view;
            mParamsBase = new LayoutParams(mScreenWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(mBase, mParamsBase);
        }
    }

    public void setContentView(int view) {
        View content = LayoutInflater.from(getContext()).inflate(view, this, false);
        setContentView(content);
    }

    public View getContentView() {
        return mBase;
    }

    protected View mMenu;
    protected LayoutParams mParamsMenu;

    /**
     * 设置滑动菜单
     *
     * @param menu  菜单视图
     * @param width 宽度
     */
    public void setSlideMenu(View menu, float width) {
        if (width > 0f && width < 1f) {
            int content = (int) (mScreenWidth * width);
            setSlideMenu(menu, content);
        }
    }

    public void setSlideMenu(View menu, int width) {
        if (mMenu != null) {
            removeView(mMenu);
            mMenu = null;
        }
        if (menu != null) {
            mMenu = menu;
            mParamsWidth = width;
            mParamsMenu = new LayoutParams(mParamsWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            mParamsMenu.leftMargin = -mParamsWidth / 2;
            addView(mMenu, 0, mParamsMenu);
        }
        mDragMargin = width / 3;
    }

    /**
     * 显示内容视图
     */
    public void onDisplayContent() {
        executor.setTarget(0);
        executor.setStart(mParamsBase.leftMargin, -5);
        onExecute(true);
    }

    /**
     * 显示菜单视图
     */
    public void onDisplayMenu(int speed) {
        executor.setTarget(mParamsWidth);
        executor.setStart(mParamsBase.leftMargin, speed);
        onExecute(true);
    }

    protected boolean mIsDragging = false;  // 是否正在滑动
    protected boolean mIsStarting = false;  // 是否已经开始
    protected int mDragMargin;      // 触屏启动距离

    /**
     * @param margin 设置滑动启动距离
     */
    public void setDragMargin(float margin) {
        if (margin > 0f && margin < 1f) {
            mDragMargin = (int) (mScreenWidth * margin);
        }
    }

    public void setDragMargin(int margin) {
        mDragMargin = margin;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return mIsDragging && super.onTouchEvent(e);
    }

    @Override
    protected void onTouchUp(MotionEvent event) {
        if (mState == SLIDE_OPEN) {
            if (event.getX() - executor.getPosition() < -mDragMargin / 2) {
                executor.setTarget(0);
                executor.setStart(mParamsBase.leftMargin, -5);
            } else {
                executor.setTarget(mParamsWidth);
                executor.setStart(mParamsBase.leftMargin, 5);
            }
            mIsStarting = false;
            onExecute(true);
            return;
        }
        if (event.getX() - executor.getPosition() > mDragMargin / 2) {
            executor.setTarget(mParamsWidth);
            executor.setStart(mParamsBase.leftMargin, 5);
        } else {
            executor.setTarget(0);
            executor.setStart(mParamsBase.leftMargin, -5);
        }
        mIsStarting = false;
        onExecute(true);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
        if (mIsDragging && !mIsExecuting) {
            if (mSlideDrawerListener != null && !mIsStarting) {
                mIsStarting = true;
                mSlideDrawerListener.onDragStart();
            }
            if (executor.getTarget() != HorizonExecutor.TARGET_NONE) {
                executor.setTarget(HorizonExecutor.TARGET_NONE);
            }
            onSliding(mParamsBase.leftMargin - (int) dx);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (e.getX() >= mBase.getLeft() - mDragMargin && e.getX() <= mBase.getLeft() + mDragMargin) {
            executor.setPosition((int) e.getX());
            mIsDragging = true;
            return true;
        }
        mIsDragging = false;
        return false;
    }

    @Override
    public boolean isEnableSliding() {
        return mBase != null && mMenu != null && super.isEnableSliding();
    }

    /**
     * 滑动时的视图更新
     *
     * @param margins 当前距离
     * @return 是否滑动到当前极限位置
     */
    public boolean onSliding(int margins) {
        if (margins < 0) {
            onSlidingContent(0);
            onSlidingMenu(-mParamsWidth / 2);
            return false;
        }
        if (margins > mParamsWidth) {
            onSlidingContent(mParamsWidth);
            onSlidingMenu(0);
            return false;
        }
        onSlidingContent(margins);
        onSlidingMenu((margins - mParamsWidth) / 2);
        return true;
    }

    /**
     * @param margin 设置内容视图的边距
     */
    protected void onSlidingContent(int margin) {
        if (mParamsBase.leftMargin != margin) {
            mParamsBase.leftMargin = margin;
            mBase.requestLayout();
        }
    }

    /**
     * @param margin 设置菜单视图的边距
     */
    protected void onSlidingMenu(int margin) {
        if (mParamsMenu.leftMargin != margin) {
            mParamsMenu.leftMargin = margin;
            mMenu.requestLayout();
        }
    }

    /**
     * 视图移动的显示任务
     */
    protected HorizonExecutor executor = new HorizonExecutor(SlideDrawerHorizonLayout.this);
    protected int mState = SLIDE_CLOSE;         // 侧滑动栏状态
    private SlideDrawerListener mSlideDrawerListener;
    private boolean mIsExecuting = false;

    public void setSlideDrawerListener(SlideDrawerListener listener) {
        mSlideDrawerListener = listener;
    }

    public void onSlideDrawerChange(int state) {
        if (mState != state) {
            if (mSlideDrawerListener != null) {
                mSlideDrawerListener.onSlideStateChange(state);
            }
            mState = state;
        }
    }

    /**
     * @return 获取侧滑栏当前状态
     */
    public int getState() {
        return mState;
    }

    @Override
    public void run(int velocity) {
        mIsExecuting = onExecute(velocity);
        onExecute(mIsExecuting);
    }

    /**
     * 执行视图滑动任务
     *
     * @param excute 是否继续执行
     */
    public void onExecute(boolean excute) {
        if (excute) {   // 继续执行
            postDelayed(executor, 10);
            return;
        }
        if (mParamsBase.leftMargin == mParamsWidth) {
            onSlideDrawerChange(SLIDE_OPEN);        // 从关闭状态打开
        } else if (mParamsBase.leftMargin == 0) {
            onSlideDrawerChange(SLIDE_CLOSE);       // 全部关闭状态
        }
    }

    /**
     * 执行视图滑动任务
     *
     * @param velocity 移动速度
     * @return 是否执行完成
     */
    public boolean onExecute(int velocity) {
        int value = mParamsBase.leftMargin + velocity;
        return onSliding(value);
    }
}