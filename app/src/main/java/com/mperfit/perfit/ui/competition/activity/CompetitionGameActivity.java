package com.mperfit.perfit.ui.competition.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.CategoryCompetitionBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.competition.adapter.CompetitionGameListAdapter;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/3/16.
 */

public class CompetitionGameActivity extends SimpleActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.ib_qa)
    ImageButton ibQa;

    private RetrofitHelper mRetrofitHelper;
    private List<CategoryCompetitionBean.DataBean.ListBean> mList;
    private CompetitionGameListAdapter mGameListAdapter;
    private int category;
    private int mTotalPage;
    private int mCurrentPage;

    @Override
    protected int getLayout() {
        return R.layout.activity_competition_game;
    }

    @Override
    protected void initEventAndData() {
        setBackTouch(ibBack);

        //设置刷新
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);

        mRetrofitHelper = new RetrofitHelper(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mGameListAdapter = new CompetitionGameListAdapter(mList, mContext);
//        mRecyclerView.setAdapter(mGameListAdapter);
        category = getIntent().getIntExtra(Constants.INTENT_GAMECATEGORY, 0);
        String mTitle = getIntent().getStringExtra(Constants.INTENT_GAME_NAME);
        if (mTitle!=null)
        tvTbTitle.setText(mTitle);

        getData();
    }


    private void getData() {
        Subscription subscription = mRetrofitHelper.getMyCategoryCompetitionInfoList(1, category)
                .compose(RxUtil.<CategoryCompetitionBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<CategoryCompetitionBean.DataBean>() {
                    @Override
                    public void call(final CategoryCompetitionBean.DataBean dataBean) {
                        changeRefreshState();
                        mCurrentPage = 2;
                        mTotalPage = dataBean.getTotal_page();
                        mList= dataBean.getList();
                        mGameListAdapter = new CompetitionGameListAdapter(mList, mContext);
                        mRecyclerView.setAdapter(mGameListAdapter);
                        mGameListAdapter.setOnItemClickListener(new CompetitionGameListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, View view) {
                                Intent intent = new Intent();
                                intent.setClass(mContext, ArticleDetailActivity.class);
                                intent.putExtra("type", Constants.TYPE_MATCH);
                                intent.putExtra("id", String.valueOf(dataBean.getList().get(position).getMatch_id()));
                                mContext.startActivity(intent);
                                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                            }
                        });

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        changeRefreshState();

                        Logger.e(throwable.getMessage());

                    }
                });

        addSubscrebe(subscription);
    }


    private void getMoreData(int page) {
        Subscription subscription = mRetrofitHelper.getMyCategoryCompetitionInfoList(page, category)
                .compose(RxUtil.<CategoryCompetitionBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<CategoryCompetitionBean.DataBean>() {
                    @Override
                    public void call(CategoryCompetitionBean.DataBean dataBean) {
                        changeRefreshState();
                        mCurrentPage = dataBean.getCurrent_page();
                        mCurrentPage++;
                        mTotalPage = dataBean.getTotal_page();
                        mGameListAdapter.addData(dataBean.getList());


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        changeRefreshState();

                        Logger.e(throwable.getMessage());

                    }
                });

        addSubscrebe(subscription);
    }

    private void changeRefreshState() {
        if (swipeToLoadLayout != null && swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (swipeToLoadLayout != null && swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }


    @Override
    public void onLoadMore() {

        if (mCurrentPage < mTotalPage) {
            getMoreData(mCurrentPage);
        } else {
            changeRefreshState();
            ToastUtils.showShrotMsg(mContext, "没有更多了");
        }


    }

    @Override
    public void onRefresh() {
        getData();
//        if (mCurrentPage > mTotalPage) {
//            changeRefreshState();
//            return;
//        } else {
//        }

    }
}
