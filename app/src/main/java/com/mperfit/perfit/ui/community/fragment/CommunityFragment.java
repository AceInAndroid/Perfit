package com.mperfit.perfit.ui.community.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.model.bean.TabEntity;
import com.mperfit.perfit.ui.main.adapter.ActivityViewPagerAdaper;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangbing on 2017/2/10.
 */

public class CommunityFragment extends SimpleFragment {

    @BindView(R.id.ctl_titlebar)
    CommonTabLayout mTitleBar;
    @BindView(R.id.vp_viewpager)
    ViewPager mContentViewPager;

    String[] tabTitle = new String[]{"广场", "关注"};
    List<Fragment> fragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ActivityViewPagerAdaper mViewPagerAdaper;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void initEventAndData() {

        mTabEntities = null;

        for (int i = 0; i < tabTitle.length; i++) {
            if (mTabEntities == null){
                mTabEntities = new ArrayList<>();
            }
            mTabEntities.add(new TabEntity(tabTitle[i], 0, 0));
        }

        mTitleBar.setTabData(mTabEntities);
        mTitleBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mContentViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {


            }
        });

        fragments.add(new SquareFragment());
        fragments.add(new FollowFragment());
        mViewPagerAdaper = new ActivityViewPagerAdaper(getChildFragmentManager(), fragments);
        mContentViewPager.setAdapter(mViewPagerAdaper);
        mContentViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                mTitleBar.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
