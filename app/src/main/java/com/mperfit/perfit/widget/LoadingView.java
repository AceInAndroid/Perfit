package com.mperfit.perfit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.mperfit.perfit.R;
import com.orhanobut.logger.Logger;

/**
 * Created by zhangbing on 2016/12/5.
 */

public class LoadingView extends ImageView {
    private AnimationDrawable background;


    private int resourseId;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = getContext().obtainStyledAttributes(attrs,
                R.styleable.LoadingView);
        resourseId = array.getResourceId(R.styleable.LoadingView_loadingsrc, R.drawable.loading);
        array.recycle(); // 回收资源
        initView();
    }


    private void initView() {
        setBackgroundResource(resourseId);
        background = (AnimationDrawable) getBackground();
    }


    public void startAnimator() {
        if (background != null) {
            background.start();
        }
    }

    public void stopAnimator() {
        if (background != null) {
            background.stop();
        }
    }

    public int getResourseId() {
        return resourseId;
    }

    public void setResourseId(int resourseId) {
        this.resourseId = resourseId;
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            startAnimator();
        } else {
            stopAnimator();
        }
    }


}

