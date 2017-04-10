package com.yinghuanhang.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.yinghuanhang.slide.helper.DrawerDragHelper;

/**
 * Created by Cao-Human on 2017/2/28
 */
public class ExpandsRightDrawerLayout extends HorizonRightDrawerLayout {
    public ExpandsRightDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExpandsRightDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandsRightDrawerLayout(Context context) {
        super(context);
    }

    @Override
    public void onTouchUp(MotionEvent event) {
        DrawerDragHelper helper = getDragHelper();
        FrameLayout.LayoutParams params = getContentParam();
        int area = getArea() / 3;

        if (getDrawerState() == DRAWER_EXPAND) {    // 展开转态：移动距离有效则收起，左移但无效则恢复展开状态
            if (event.getX() - helper.getPosition() > area) {
                helper.setTarget(-getDrawerMargin());
                helper.setStart(params.leftMargin, 5);
            } else {
                helper.setTarget(-getMaxMargin());
                helper.setStart(params.leftMargin, -5);
            }
            onRunning(true);
            return;
        }
        if (getDrawerState() == DRAWER_OPEN) {      // 打开状态：移动距离有效则展开，左移但无效则恢复打开状态
            if (event.getX() - helper.getPosition() < -area) {
                helper.setTarget(-getMaxMargin());
                helper.setStart(params.leftMargin, -5);
                onRunning(true);
                return;
            }
            if (event.getX() - helper.getPosition() < 0) {
                helper.setTarget(-getDrawerMargin());
                helper.setStart(params.leftMargin, 5);
                onRunning(true);
                return;
            }
        }
        super.onTouchUp(event);
    }

    /**
     * 判断与处理第二阶段的滑动
     *
     * @param margins 当前距离
     * @return 是否可以继续执行
     */
    @Override
    public boolean onSliding(int margins) {
        DrawerDragHelper helper = getDragHelper();
        FrameLayout.LayoutParams params = getContentParam();

        if (margins < -getMaxMargin()) {                    // 滑动距离超过屏幕距离
            onSlidingContent(params, -getMaxMargin());
            return false;
        }
        if (margins < -getDrawerMargin()) {                 // 滑动距离超过侧栏距离
            if (getDrawerState() == DRAWER_EXPAND) {
                onSlidingContent(params, margins);
                return true;
            }
            if (helper.getTarget() == -getDrawerMargin()) { // 执行的目标为侧栏距离
                onSlidingContent(params, -getDrawerMargin());
                return false;
            }
            if (getDrawerState() == DRAWER_OPEN) {          // 当前的状态为打开状态
                onSlidingContent(params, margins);
                return true;
            }
            return super.onSliding(margins);
        }
        if (getDrawerState() == DRAWER_EXPAND) {            // 当前的状态为展开状态
            onSlidingContent(params, -getDrawerMargin());
            return false;
        }
        if (helper.getTarget() == -getDrawerMargin() && getDrawerState() == DRAWER_OPEN) {
            onSlidingContent(params, -getDrawerMargin());   // 执行的目标为侧栏距离且当前的状态为打开状态
            return false;
        }
        return super.onSliding(margins);
    }

    @Override
    public void onRunning(FrameLayout.LayoutParams params) {
        if (params.leftMargin == -getMaxMargin()) {
            onDrawerStateChange(DRAWER_EXPAND);
            return;
        }
        super.onRunning(params);
    }
}