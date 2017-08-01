package com.mperfit.perfit.ui.me.activity.shop;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.presenter.contract.ShopCollectContract;
import com.mperfit.perfit.presenter.presenter.ShopCollectPresenterImpl;
import com.mperfit.perfit.ui.classes.activity.ShopDetailActivity;
import com.mperfit.perfit.ui.me.activity.shop.adapter.ShopCollectListAdapter;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.ToastUtils;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2016/11/29.
 */

public class ShopCollectActivity extends BaseActivity<ShopCollectPresenterImpl> implements ShopCollectContract.View, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.swipe_target)
    RecyclerView rvList;
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
    private List<ShopCollectBean.DataBean.ListBean> list;
    private ShopCollectListAdapter collectListAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_shopcollect;
    }

    @Override
    protected void initEventAndData() {
        //设置刷新
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        mPresenter.getCollectData(1);
        setBackTouch();
        if (initLoading()) return;
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
                overridePendingTransition(0, R.anim.slide_right_out);
            }
        });
    }


    private boolean initLoading() {
        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //reload按钮监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getCollectData(1);
                }
            });
            return true;
        }


        //reload按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getCollectData(1);
            }
        });
        //加载布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态

        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, R.drawable.divider_mileage));
        collectListAdapter = new ShopCollectListAdapter(mContext, list);
        rvList.setAdapter(collectListAdapter);
        collectListAdapter.setOnItemClickListener(new ShopCollectListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent();
                intent.putExtra(Constants.COURSE_SELLERID, String.valueOf(ShopCollectActivity.this.list.get(position).getSeller_id()));
                intent.setClass(mContext, ShopDetailActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in, 0);

            }
        });

        collectListAdapter.setOnItemClickListener(new ShopCollectListAdapter.OnCollectedListener() {
            @Override
            public void onItemClick(int position, View view, boolean isCheck) {
                if (isCheck) {
//                    RealmLikeBean realmLikeBean = new RealmLikeBean();
//                    realmLikeBean.setId(String.valueOf(ShopCollectActivity.this.list.get(position).getSeller_id()));
//                    realmLikeBean.setType(Constants.LIKE_TYPE_SHOP);
                    mPresenter.insetLike(String.valueOf(ShopCollectActivity.this.list.get(position).getSeller_id()), Constants.LIKE_TYPE_SHOP, position);
                } else {
                    mPresenter.deleteLike(String.valueOf(ShopCollectActivity.this.list.get(position).getSeller_id()), Constants.LIKE_TYPE_SHOP, position);

                }
            }
        });


        return false;
    }

    @Override
    public void showContent(ShopCollectBean shopCollectBean) {
        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (shopCollectBean.getData().getList() == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }

        if (shopCollectBean.getData().getList().size() == 0) {
//            mNoNet.setVisibility(View.VISIBLE);
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }

        //如果刷新 重置初始页
        mCurrenPage = 2;
        if (loadingLayout != null) {
            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
        }


        List<ShopCollectBean.DataBean.ListBean> list = shopCollectBean.getData().getList();

        this.list = list;

        collectListAdapter.addData(list);


    }

    @Override
    public void setLikeState(Boolean isLiked, int position) {
        collectListAdapter.updateCheckState(isLiked, position);

    }

    @Override
    public void showLikeResult(int result, String msg) {
        if (result == Constants.LIKE_RESULT_SINGFILED) {
            ToastUtils.showLongMsg(mContext, msg);
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
        } else {
            ToastUtils.showShrotMsg(mContext, msg);
        }
    }

    @Override
    public void showMoreData(ShopCollectBean shopCollectBean) {

        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setLoadingMore(false);
        }


        if (shopCollectBean.getCode() == 100) {
            List<ShopCollectBean.DataBean.ListBean> list = shopCollectBean.getData().getList();
            collectListAdapter.addMore(list);
            mCurrenPage = shopCollectBean.getData().getCurrent_page();
            mCurrenPage++;
            mTotalPage = shopCollectBean.getData().getTotal_page();
        }
    }


    @Override
    public void showError(String msg) {
        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }
        if (loadingLayout!=null)
        loadingLayout.setStatus(LoadingLayout.Success);//加载成功


    }

    @Override
    public void onLoadMore() {

        // load more data here
        if (mCurrenPage <= mTotalPage) {
            mPresenter.getMoreData(mCurrenPage);
        } else if (mCurrenPage > mTotalPage) {
            //没有更多了
            if (swipeToLoadLayout!=null){
                swipeToLoadLayout.setLoadingMore(false);
            }
        }

    }

    @Override
    public void onRefresh() {
        mPresenter.getCollectData(1);
    }


}
