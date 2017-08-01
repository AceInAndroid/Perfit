package com.mperfit.perfit.ui.rankingList.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.ui.rankingList.adapter.StartRankingViewPagerAdaper;
import com.mperfit.perfit.ui.rankingList.fragment.DayRankingListFragment;
import com.mperfit.perfit.ui.rankingList.fragment.MonthRankListFragment;
import com.mperfit.perfit.ui.rankingList.fragment.WeekRankingListFragment;
import com.mperfit.perfit.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangbing on 2017/3/13.
 */

public class StarRankingListActivity extends SimpleActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.no_scoll_viewpage)
    NoScrollViewPager noScollViewpage;
    @BindView(R.id.tv_day_ranking)
    TextView tvDayRanking;
    @BindView(R.id.tv_week_ranking)
    TextView tvWeekRanking;
    @BindView(R.id.tv_month_ranking)
    TextView tvMonthRanking;
    @BindView(R.id.ib_qa)
    ImageButton mIbQa;


    private List<String> mTitleList;
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_start_rankinglist;
    }

    @Override
    protected void initEventAndData() {
        tvTbTitle.setText("红人榜");
        setBackTouch(ibBack);
        mTitleList = new ArrayList<>();
        mTitleList.add("日榜");
        mTitleList.add("周榜");
        mTitleList.add("月榜");

        mIbQa.setVisibility(View.VISIBLE);

        mIbQa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,QuestionAnswerActivity.class);
                intent.putExtra(Constants.TYPE,Constants.QATYPE_STAR);
                startActivity(intent);
            }
        });

        fragments.add(new DayRankingListFragment());
        fragments.add(new WeekRankingListFragment());
        fragments.add(new MonthRankListFragment());

        noScollViewpage.setNoScroll(true);
        noScollViewpage.setAdapter(new StartRankingViewPagerAdaper(getSupportFragmentManager(), fragments));
        noScollViewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        noScollViewpage.setOffscreenPageLimit(3);
    }



    @OnClick(R.id.tv_day_ranking)
    void toDay() {
        noScollViewpage.setCurrentItem(0, false);
        //选中字体要加粗
        setTabTextState(0);
    }

    @OnClick(R.id.tv_week_ranking)
    void toWeek() {
        noScollViewpage.setCurrentItem(1, false);
        setTabTextState(1);
    }

    @OnClick(R.id.tv_month_ranking)
    void toMonth() {
        noScollViewpage.setCurrentItem(2, false);
        setTabTextState(2);
    }

    private void setTabTextState(int state){
        switch (state){
            case 0:
                tvDayRanking.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvWeekRanking.setTypeface(Typeface.DEFAULT);
                tvMonthRanking.setTypeface(Typeface.DEFAULT);
                break;
            case 1:
                tvDayRanking.setTypeface(Typeface.DEFAULT);
                tvWeekRanking.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvMonthRanking.setTypeface(Typeface.DEFAULT);
                break;
            case 2:
                tvDayRanking.setTypeface(Typeface.DEFAULT);//加粗
                tvWeekRanking.setTypeface(Typeface.DEFAULT);
                tvMonthRanking.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
        }
    }
}
