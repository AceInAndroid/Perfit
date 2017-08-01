package com.mperfit.perfit.ui.me.activity.course;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.ui.me.activity.course.adapter.MyCourseViewPagerAdapter;
import com.mperfit.perfit.ui.me.activity.course.fragments.AllCourseFragment;
import com.mperfit.perfit.ui.me.activity.course.fragments.NoCommentFragment;
import com.mperfit.perfit.ui.me.activity.course.fragments.PaidFragment;
import com.mperfit.perfit.ui.me.activity.course.fragments.RefundFragment;
import com.mperfit.perfit.ui.me.activity.course.fragments.UnpaidFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangbing on 2016/11/30.
 */

public class MyCourseActivity extends SimpleActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tl_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_viewpager)
    ViewPager vpViewpager;


    String[] tabTitle = new String[]{"全部","待付款","待上课","待评价","退款"};
    private List<Fragment> fragmentList;

    @Override
    protected int getLayout() {
        return R.layout.activity_mycourse;
    }

    @Override
    protected void initEventAndData() {
        tvTitle.setText("我的课程");
        fragmentList = new ArrayList<>();
        fragmentList.add(new AllCourseFragment());
        fragmentList.add(new UnpaidFragment());
        fragmentList.add(new PaidFragment());
        fragmentList.add(new NoCommentFragment());
        fragmentList.add(new RefundFragment());

        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[3]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[4]));
        mTabLayout.setupWithViewPager(vpViewpager);
        vpViewpager.setAdapter(new MyCourseViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);
        mTabLayout.getTabAt(2).setText(tabTitle[2]);
        mTabLayout.getTabAt(3).setText(tabTitle[3]);
        mTabLayout.getTabAt(4).setText(tabTitle[4]);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.text_black),
                getResources().getColor(R.color.groupbuying_price));
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
        if(View.class.isInstance(ibBack.getParent())){
            // 设置视图扩大后的触摸区域
            ((View)ibBack.getParent()).setTouchDelegate(expandedArea);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,R.anim.right_out);

            }
        });
    }


}
