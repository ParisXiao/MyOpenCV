package com.opencv.myopencv;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class GrabCutView extends ImageView {
    public GrabCutView(Context context) {
        super(context);
    }

    public GrabCutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GrabCutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GrabCutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
