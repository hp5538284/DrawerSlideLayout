package com.yinghuanhang.slide;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Cao-Human on 2017/2/28
 */
public abstract class HorizonDrawerLayout extends BasicDrawerLayout {
    public HorizonDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizonDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizonDrawerLayout(Context context) {
        this(context, null, 0);
    }

    /*
     * ######################################## 拖动区域距离 ########################################
     */
    @Override
    protected void onSizeChanged(int width, int height, int w, int h) {
        super.onSizeChanged(width, height, w, h);
        if (getMaxMargin() == 0 && width > 0) {
            setMaxMargin(width);
        }
        if (getDrawerMargin() == 0 && mDrawerRatio != 0f) {
            int margin = (int) (getMaxMargin() * mDrawerRatio);
            setDrawerMargin(margin);
        }
    }

    public void setDrawerMargin(float margin) {
        if (getMaxMargin() != 0) {
            int max = (int) (getMaxMargin() * margin);
            setDrawerMargin(max);
            return;
        }
        mDrawerRatio = margin;
    }

    private float mDrawerRatio = 0f;
}
