package com.yinghuanhang.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Cao-Human on 2016/8/15
 */
public class SlideExpandHorizonLayout extends SlideDrawerHorizonLayout {
    public SlideExpandHorizonLayout(Context context) {
        super(context);
    }

    public SlideExpandHorizonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideExpandHorizonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSlideMenu(View menu, int width) {
        super.setSlideMenu(menu, width);
        mParamsMenu.width = mScreenWidth;
    }

    @Override
    protected void onTouchUp(MotionEvent event) {
        if (mState == SLIDE_OPEN) {     // 打开状态：移动距离有效则展开，右移但无效则恢复打开状态
            if (event.getX() - executor.getPosition() > mDragMargin / 2) {
                executor.setTarget(mScreenWidth);
                executor.setStart(mParamsBase.leftMargin, 5);
                onExecute(true);
                return;
            }
            if (event.getX() - executor.getPosition() > 0) {
                executor.setTarget(mParamsWidth);
                executor.setStart(mParamsBase.leftMargin, -5);
                onExecute(true);
                return;
            }
        }
        if (mState == SLIDE_EXPAND) {   // 展开转态：移动距离有效则收起，左移但无效则恢复展开状态
            if (event.getX() - executor.getPosition() < -mDragMargin / 2) {
                executor.setTarget(mParamsWidth);
                executor.setStart(mParamsBase.leftMargin, -5);
            } else {
                executor.setTarget(mScreenWidth);
                executor.setStart(mParamsBase.leftMargin, 5);
            }
            onExecute(true);
            return;
        }
        super.onTouchUp(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (mState == SLIDE_EXPAND && e.getX() >= mScreenWidth - mDragMargin && e.getX() <= mScreenWidth) {
            executor.setPosition((int) e.getX());
            mIsDragging = true;
            return true;
        }
        // 展开状态并从右方开始滑动
        return super.onDown(e);
    }

    /**
     * 如果不执行且边距为屏幕宽度，更改状态为展开
     *
     * @param excute 是否继续执行
     */
    @Override
    public void onExecute(boolean excute) {
        if (!excute) {
            if (mParamsBase.leftMargin == mScreenWidth) {
                onSlideDrawerChange(SLIDE_EXPAND);
            } else {
                super.onExecute(false);
            }
            return;
        }
        super.onExecute(true);
    }

    /**
     * 判断与处理第二阶段的滑动
     *
     * @param margins 当前距离
     * @return 是否可以继续执行
     */
    @Override
    public boolean onSliding(int margins) {
        if (margins > mScreenWidth) {                       // 滑动距离超过屏幕距离
            onSlidingContent(mScreenWidth);
            onSlidingMenu(0);
            return false;
        }
        if (margins > mParamsWidth) {                       // 滑动距离超过侧栏距离
            if (mState == SLIDE_EXPAND) {
                onSlidingContent(margins);
                onSlidingMenu(0);
                return true;
            }
            if (executor.getTarget() == mParamsWidth) {     // 执行的目标为侧栏距离
                onSlidingContent(mParamsWidth);
                onSlidingMenu(0);
                return false;
            }
            if (mState == SLIDE_OPEN) {                     // 当前的状态为打开状态
                onSlidingContent(margins);
                onSlidingMenu(0);
                return true;
            }
            return super.onSliding(margins);
        }
        if (mState == SLIDE_EXPAND) {                       // 当前的状态为展开状态
            onSlidingContent(mParamsWidth);
            onSlidingMenu(0);
            return false;
        }
        if (executor.getTarget() == mParamsWidth) {         // 执行的目标为侧栏距离
            if (mState == SLIDE_OPEN) {                     // 当前的状态为打开状态
                onSlidingContent(mParamsWidth);
                onSlidingMenu(0);
                return false;
            }
        }
        return super.onSliding(margins);
    }
}