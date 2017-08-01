package com.mperfit.perfit.ui.competition.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.model.bean.TabEntity;
import com.mperfit.perfit.ui.community.fragment.FollowFragment;
import com.mperfit.perfit.ui.community.fragment.SquareFragment;
import com.mperfit.perfit.ui.main.adapter.ActivityViewPagerAdaper;
import com.mperfit.perfit.ui.rankingList.activity.QuestionAnswerActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangbing on 2017/3/2.
 */

public class CompetitionFragment extends SimpleFragment {

    @BindView(R.id.ctl_titlebar)
    CommonTabLayout mTitleBar;
    @BindView(R.id.vp_viewpager)
    ViewPager mContentViewPager;
    @BindView(R.id.ib_qa)
    ImageButton mIbQa;

    String[] tabTitle = new String[]{"赛事", "活动"};
    List<Fragment> fragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ActivityViewPagerAdaper mViewPagerAdaper;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    private  int count = 0  ;
    @Override
    protected void initEventAndData() {

        mTabEntities =null;
        count++;

        for (int i = 0; i < tabTitle.length; i++) {
            if (mTabEntities ==null){
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


        fragments.add(new GameFragment());
        fragments.add(new CompetitionActivityFragment());
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


        if (mIbQa.getVisibility()!=View.VISIBLE){
            mIbQa.setVisibility(View.VISIBLE);
        }

        mIbQa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,QuestionAnswerActivity.class);
                intent.putExtra(Constants.TYPE,Constants.QATYPE_GAME);
                startActivity(intent);
            }
        });

    }


}
