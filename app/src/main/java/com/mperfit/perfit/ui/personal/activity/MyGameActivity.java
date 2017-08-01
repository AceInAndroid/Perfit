package com.mperfit.perfit.ui.personal.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.model.bean.MyJoinedActivityBean;
import com.mperfit.perfit.model.bean.PersonalMyGameBean;
import com.mperfit.perfit.presenter.contract.MyJoinContract;
import com.mperfit.perfit.presenter.presenter.MyJoinPresenterImpl;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.me.adapter.MyJoinListAdapter;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.ToastUtils;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangbing on 2017/3/14.
 */

public class MyGameActivity extends BaseActivity<MyJoinPresenterImpl> implements MyJoinContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.swipe_target)
    RecyclerView rvList;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;


    private int mCurrenPage = 2;
    private int mTotalPage;

    private MyJoinListAdapter myJoinListAdapter;
    private List<PersonalMyGameBean.DataBean.ListBean> list;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_myjoinedactivity;
    }


    @Override
    protected void initEventAndData() {
        setBackTouch(ibBack);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        tvTbTitle.setText("我的比赛");

        //reload按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getMyJoinedGame();

            }
        });
        //加载布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态

        linearLayoutManager = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.addItemDecoration(new RecycleViewDivider(mContext,LinearLayoutManager.HORIZONTAL,10,getResources().getColor(R.color.divider_gray)));

        myJoinListAdapter = new MyJoinListAdapter(mContext,list);
        rvList.setAdapter(myJoinListAdapter);

        if (!CheckLoginUtil.isLogin(mContext, Constants.LOGIN_FROMCOLLECT)) return;

        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //reload按钮监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getMyJoinedGame();
                }
            });
            return;
        }

        mPresenter.getMyJoinedGame();

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
    public void showMyJoinedGame(final PersonalMyGameBean myJoinedGameBean) {

        changeRefreshState();


        if (myJoinedGameBean.getData().getList() == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }
        if (myJoinedGameBean.getData().getList().size() == 0) {
//            mNoNet.setVisibility(View.VISIBLE);
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }
        //如果刷新 重置初始页
        mCurrenPage = 2;
        mTotalPage = myJoinedGameBean.getData().getTotal_page();
        if (loadingLayout != null) {
            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
        }

        myJoinListAdapter.setOnItemClickListener(new MyJoinListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("type", Constants.TYPE_MATCH);
                intent.putExtra("id", myJoinedGameBean.getData().getList().get(position).getMatch_id());
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });

        myJoinListAdapter.addData(myJoinedGameBean.getData().getList());

    }

    @Override
    public void showMyJoinedActivity(MyJoinedActivityBean myJoinedActivityBean) {

    }

    @Override
    public void showMoreMyJoinedGame(PersonalMyGameBean myJoinedGameBean) {

        changeRefreshState();
        if (myJoinedGameBean.getCode() == 100) {
            mCurrenPage = myJoinedGameBean.getData().getCurrent_page();
            mCurrenPage++;
            mTotalPage = myJoinedGameBean.getData().getTotal_page();
            myJoinListAdapter.addMoreDate(myJoinedGameBean.getData().getList());
        } else {
            ToastUtils.showShrotMsg(mContext, myJoinedGameBean.getMessage());
        }

    }

    @Override
    public void showMoreMyJoinedActivity(MyJoinedActivityBean myJoinedActivityBean) {

    }

    @Override
    public void reLogin() {
        CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
    }

    @Override
    public void showError(String msg) {
        if (loadingLayout != null) {
            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
        }
        changeRefreshState();

        ToastUtils.showShrotMsg(mContext, msg);

    }

    @Override
    public void onLoadMore() {
        if (mCurrenPage <= mTotalPage) {
            mPresenter.getMyJoinedGameMore(mCurrenPage);
        } else if (mCurrenPage > mTotalPage) {
            //没有更多了
            changeRefreshState();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getMyJoinedGame();
    }

}
