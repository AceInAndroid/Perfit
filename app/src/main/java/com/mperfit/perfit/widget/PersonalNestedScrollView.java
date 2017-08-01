package com.mperfit.perfit.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

/**
 * Created by zhangbing on 2017/2/22.
 */

public class PersonalNestedScrollView extends NestedScrollView {

    private String tag = "PersonalNestedScrollView";
    private int downX;
    private int downY;
    private int moveX;
    private int moveY;
    private int lastX;
    private int lastY;

    public PersonalNestedScrollView(Context context) {
        super(context);
    }

    public PersonalNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        Logger.e("l"+ x+"t" + y);


        //ScrollView滑动到底部了
        if (onScrollBottomListener!=null){
            onScrollBottomListener.scrollBottom();
        }

    }





    private OnScrollBottomListener onScrollBottomListener;

    public void setScrollBottomListener(OnScrollBottomListener onScrollBottomListener){
        this.onScrollBottomListener = onScrollBottomListener;
    }
    public  interface OnScrollBottomListener {
        void scrollBottom();
    }



}
