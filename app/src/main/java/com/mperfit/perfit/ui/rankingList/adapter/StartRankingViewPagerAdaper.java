package com.mperfit.perfit.ui.rankingList.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/13.
 */

public class StartRankingViewPagerAdaper extends FragmentPagerAdapter{
    private List<Fragment> fragments;


    public StartRankingViewPagerAdaper(FragmentManager fm,List<Fragment>  fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}
