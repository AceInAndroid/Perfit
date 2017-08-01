package com.mperfit.perfit.ui.competition.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.model.bean.ActivityBean;
import com.mperfit.perfit.model.bean.CompetitionGameBean;
import com.mperfit.perfit.presenter.contract.ActivityContract;
import com.mperfit.perfit.presenter.presenter.ActivityPresenterImpl;
import com.mperfit.perfit.ui.activities.adapter.ActivityAdapter;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.competition.adapter.CompetitionActivityListAdapter;
import com.mperfit.perfit.ui.competition.adapter.CompetitionGameListAdapter;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangbing on 2017/3/16.
 */



//         R.layout.fragment_competition_activity;
//
////item_competition_activity.xml



public class CompetitionActivityFragment extends BaseFragment<ActivityPresenterImpl> implements ActivityContract.View, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private int mCurrenPage = 2;
    private int mTotalPage;
    private List<ActivityBean.DataBean.ListBean> list = new ArrayList<>();
//    private ActivityAdapter mActivityAdapter;
    private LinearLayoutManager linearLayoutManager;
    private CompetitionActivityListAdapter mActivityAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competition_activity;
    }

    @Override
    protected void initEventAndData() {


        //设置刷新
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        //加载布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态


        mActivityAdapter = new CompetitionActivityListAdapter(list, mContext);
        linearLayoutManager = new LinearLayoutManager(mContext);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mActivityAdapter);
        mPresenter.getActivityData();

        //reload按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getActivityData();
                loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态

            }
        });

        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //reload按钮监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getActivityData();
                    loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态
                }
            });
            return;
        }


    }

    private ActivityBean activityBean;

    @Override
    public void showContent(final ActivityBean activityBean) {

        this.activityBean= activityBean;
        if (activityBean.getData().getList() == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }


        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setRefreshing(false);
        }


        if (activityBean.getCode() == 100) {

            mActivityAdapter.setNewData(activityBean.getData().getList());

        } else {
            ToastUtils.showShrotMsg(mContext, activityBean.getMessage());
        }

        if (activityBean.getData().getList().size() == 0) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }

        //如果刷新 重置初始页
        mCurrenPage = 2;

        if (loadingLayout != null) {
            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
        }

        mTotalPage = activityBean.getData().getTotal_page();

        mActivityAdapter.setOnItemClickListener(new CompetitionGameListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                Intent intent = new Intent();
                intent.setClass(mContext, ArticleDetailActivity.class);
                intent.putExtra("type", Constants.TYPE_ACTIVITY);
                intent.putExtra("id", String.valueOf(activityBean.getData().getList().get(position).getActivity_id()));
                mContext.startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    ActivityOptions options = null;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "shareView");
//                    }
//                    mContext.startActivity(intent, options.toBundle());
//                } else {
//                    mContext.startActivity(intent);
//                }


            }
        });
    }

    @Override
    public void showGameContent(CompetitionGameBean.DataBean competitionGameBean) {

    }

    @Override
    public void showMoreContent(ActivityBean activityBean) {
        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }
        if (activityBean.getCode() == 100) {
            mActivityAdapter.addData(activityBean.getData().getList());
            mCurrenPage = activityBean.getData().getCurrent_page();
            mCurrenPage++;
            mTotalPage = activityBean.getData().getTotal_page();

        } else {
            ToastUtils.showShrotMsg(mContext, activityBean.getMessage());
        }
    }

    @Override
    public void showMoreGameContent(CompetitionGameBean.DataBean competitionGameBean) {

    }

    @Override
    public void showError(String msg) {
        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }
        ToastUtils.showShrotMsg(mContext, msg);
    }

    @Override
    public void onLoadMore() {

        // load more data here
        if (mCurrenPage <= mTotalPage) {
            mPresenter.getMoreDate(mCurrenPage);
        } else if (mCurrenPage > mTotalPage) {
            //没有更多了
            if (swipeToLoadLayout!=null){
                swipeToLoadLayout.setLoadingMore(false);
            }
        }


    }

    @Override
    public void onRefresh() {
        mPresenter.getActivityData();
    }

}