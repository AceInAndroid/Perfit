package com.mperfit.perfit.ui.photogallery;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.component.ImageLoader;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 头像查看
 * Created by zhangbing on 2017/1/3.
 */

public class HeadShowActivity extends SimpleActivity {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;

    private String headUrl;

    @Override
    protected int getLayout() {
        return R.layout.activity_headshow;
    }

    @Override
    protected void initEventAndData() {
        headUrl = mContext.getIntent().getStringExtra("head");
        ImageLoader.load(mContext,headUrl,ivHead);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivHead, "scaleX", 0, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivHead, "scaleY", 0, 1f);
        ObjectAnimator scalebgX = ObjectAnimator.ofFloat(rlBack, "scaleX", 0, 1f);
        ObjectAnimator scalebgY = ObjectAnimator.ofFloat(rlBack, "scaleY", 0, 1f);
        ObjectAnimator alphabg = ObjectAnimator.ofFloat(rlBack, "alpha", 0, 1);
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY).with(scalebgX).with(scalebgY).with(alphabg);//两个动画同时开始
        animatorSet.start();


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        finish();
        return true;
    }



}
