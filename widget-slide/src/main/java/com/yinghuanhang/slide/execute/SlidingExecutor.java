package com.yinghuanhang.slide.execute;

/**
 * Created by Cao-Human on 2016/8/15
 */
public class SlidingExecutor implements Runnable {
    public SlidingExecutor(Callback callback) {
        this.callback = callback;
    }

    private Callback callback;

    public static final int TARGET_NONE = -1;
    private int target;
    private int velocity;

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public void run() {
        callback.run(velocity);
    }

    public interface Callback {
        void run(int velocity);
    }
}