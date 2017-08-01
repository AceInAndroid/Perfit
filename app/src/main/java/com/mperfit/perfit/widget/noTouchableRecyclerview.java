package com.mperfit.perfit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhangbing on 2016/12/3.
 */

public class noTouchableRecyclerview extends RecyclerView {
    public noTouchableRecyclerview(Context context) {
        super(context);
    }

    public noTouchableRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }
}
