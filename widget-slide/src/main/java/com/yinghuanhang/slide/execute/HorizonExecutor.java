package com.yinghuanhang.slide.execute;

/**
 * Created by Cao-Human on 2016/8/15
 */
public class HorizonExecutor extends SlidingExecutor {
    public HorizonExecutor(Callback callback) {
        super(callback);
    }

    private int position;
    private int start;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start, int min) {
        this.start = start;
        int velocity = (getTarget() - start) / 12 + min;
        setVelocity(velocity);
    }
}
