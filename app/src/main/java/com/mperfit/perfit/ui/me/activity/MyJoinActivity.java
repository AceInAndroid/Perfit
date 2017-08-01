package com.mperfit.perfit.ui.me.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.ui.me.adapter.MyJoinedViewPagerAdapter;
import com.mperfit.perfit.ui.me.fragment.myjoined.MyLikedFragment;
import com.mperfit.perfit.ui.me.fragment.myjoined.MyjoinedFragment;
import com.mperfit.perfit.widget.NoScrollViewPager;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangbing on 16/11/3.
 */

public class MyJoinActivity extends SimpleActivity {


    @BindView(R.id.iv_back)
    ImageButton ibBack;
    @BindView(R.id.iv_tabjoine_indicator)
    ImageView ivTabindicator;
    @BindView(R.id.bt_like)
    Button btLike;
    @BindView(R.id.iv_tablike_indicator)
    ImageView ivTablikeIndicator;
    @BindView(R.id.vp_containt)
    NoScrollViewPager vpContaint;
    @BindView(R.id.bt_joined)
    Button btJoined;


    @Override
    protected int getLayout() {
        return R.layout.activity_myjoin;
    }

    @Override
    protected void initEventAndData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyjoinedFragment());
        fragmentList.add(new MyLikedFragment());
        vpContaint.setAdapter(new MyJoinedViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        vpContaint.setNoScroll(false);

        vpContaint.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ivTabindicator.setVisibility(View.VISIBLE);
                        ivTablikeIndicator.setVisibility(View.GONE);
                        btLike.setTextColor(getResources().getColor(R.color.text_color_gray));
                        btJoined.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 1:
                        btJoined.setTextColor(getResources().getColor(R.color.text_color_gray));
                        btLike.setTextColor(getResources().getColor(R.color.white));
                        ivTabindicator.setVisibility(View.GONE);
                        ivTablikeIndicator.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setBackTouch();
    }


    private void setBackTouch() {
        Rect delegateArea = new Rect();
        // Hit rectangle in parent's coordinates
        ibBack.getHitRect(delegateArea);


        // 扩大触摸区域矩阵值
        delegateArea.left -= 200;
        delegateArea.top -= 200;
        delegateArea.right += 200;
        delegateArea.bottom += 200;

        /**
         * 构造扩大后的触摸区域对象
         * 第一个构造参数表示  矩形面积
         * 第二个构造参数表示 被扩大的触摸视图对象
         * <也是本demo最核心用到的类之一>
         */
        TouchDelegate expandedArea = new TouchDelegate(delegateArea, ibBack);
        if (View.class.isInstance(ibBack.getParent())) {
            // 设置视图扩大后的触摸区域
            ((View) ibBack.getParent()).setTouchDelegate(expandedArea);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.right_out);
            }
        });
    }


    @OnClick(R.id.bt_joined)
    void myJoined() {
        vpContaint.setCurrentItem(0);
        ivTabindicator.setVisibility(View.VISIBLE);
        ivTablikeIndicator.setVisibility(View.GONE);
        btLike.setTextColor(getResources().getColor(R.color.text_color_gray));
        btJoined.setTextColor(getResources().getColor(R.color.white));
    }

    @OnClick(R.id.bt_like)
    void myLiked() {
        vpContaint.setCurrentItem(1);
        btJoined.setTextColor(getResources().getColor(R.color.text_color_gray));
        btLike.setTextColor(getResources().getColor(R.color.white));
        ivTabindicator.setVisibility(View.GONE);
        ivTablikeIndicator.setVisibility(View.VISIBLE);
    }


}
