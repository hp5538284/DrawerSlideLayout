package com.yinghuanhang.slide.helper;

/**
 * Created by Cao-Human on 2016/8/15
 */
public class DrawerDragHelper implements Runnable {
    public DrawerDragHelper(DrawerDragCallback c) {
        callback = c;
    }

    private DrawerDragCallback callback;

    /**
     * 移动速度
     */
    private int velocity;

    /**
     * 目标位置
     */
    private int target;

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    /**
     * 开始位置
     */
    private int start;

    public void setStart(int start, int min) {
        this.start = start;
        velocity = (target - start) / 12 + min;
    }

    public int getStart() {
        return start;
    }

    /**
     * 按下位置
     */
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void reset() {
        target = -1;
        start = 0;
    }

    @Override
    public void run() {
        if (callback != null) {
            callback.running(velocity);
        }
    }
}