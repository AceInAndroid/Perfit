package com.mperfit.perfit.ui.article.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.model.bean.ArticleListBean;
import com.mperfit.perfit.presenter.contract.FitnessFragmentContract;
import com.mperfit.perfit.presenter.presenter.FitnessFragmentPresenterImpl;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.article.adapter.FitnessAdapter;
import com.mperfit.perfit.ui.home.adapter.HomeListAdapter;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.ToastUtils;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 健康文章
 * Created by zhangbing on 16/10/18.
 */

public class FitnessFragment extends BaseFragment<FitnessFragmentPresenterImpl> implements FitnessFragmentContract.View, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    List<ArticleListBean.DataBean.ListBean> mList = new ArrayList<>();
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;

    FitnessAdapter fitnessAdapter;
    private int mCurrentPage = 2;
    private int mPageCount = 1;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fitness;
    }

    @Override
    protected void initEventAndData() {
        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //reload按钮监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getArticleList(1, 1);
                }
            });
            return;
        }

        //reload按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getArticleList(1, 1);
            }
        });

        LayoutInflater inflater = LayoutInflater.from(mContext);


        //加载布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        fitnessAdapter = new FitnessAdapter(mContext, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(fitnessAdapter);
        fitnessAdapter.setOnItemClickListener(new FitnessAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent goDetailInten = new Intent();
                goDetailInten.setClass(mContext, ArticleDetailActivity.class);
                goDetailInten.putExtra("id", mList.get(position).getArticle_id());
                mContext.startActivity(goDetailInten);
                //暂时关闭
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "shareView");
//                    mContext.startActivity(goDetailInten, options.toBundle());
//                } else {
//                    mContext.startActivity(goDetailInten);
//                }
            }
        });

        mPresenter.getArticleList(1, 1);

    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showContent(final ArticleListBean articleListBean) {
        if (articleListBean.getData().getList() == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }

        if (articleListBean.getData().getList().size() == 0) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }

        loadingLayout.setStatus(LoadingLayout.Success);//加载成功


        swipeToLoadLayout.setRefreshing(false);
        mPageCount = articleListBean.getData().getTotal_page();
        mCurrentPage = 2;
        fitnessAdapter.addDate(articleListBean.getData().getList());
        //文章的跳转
        fitnessAdapter.setOnItemClickListener(new FitnessAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, ArticleDetailActivity.class);
                intent.putExtra("id", String.valueOf(articleListBean.getData().getList().get(position).getArticle_id()));
                mContext.startActivity(intent);


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
    public void showMore(ArticleListBean articleListBean) {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
        if (articleListBean.getCode() == 100) {
            mCurrentPage++;
            mPageCount = articleListBean.getData().getTotal_page();
            fitnessAdapter.addMoreDate(articleListBean.getData().getList());
        } else {
            ToastUtils.showShrotMsg(mContext, articleListBean.getMessage());
        }

    }

    @Override
    public void onLoadMore() {
        if (mCurrentPage > mPageCount) {
            swipeToLoadLayout.setLoadingMore(false);
            ToastUtils.showShrotMsg(mContext, "没有更多了");
            return;
        }

        mPresenter.getArticleList(mCurrentPage, 1);
    }

    @Override
    public void onRefresh() {
        mPresenter.getArticleList(1, 1);
    }
}
