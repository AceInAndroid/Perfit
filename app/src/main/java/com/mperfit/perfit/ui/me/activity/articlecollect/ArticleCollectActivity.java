package com.mperfit.perfit.ui.me.activity.articlecollect;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.model.bean.ArticleCollectBean;
import com.mperfit.perfit.presenter.contract.ArticleCollectContract;
import com.mperfit.perfit.presenter.presenter.ArticleCollectPresenterImpl;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.me.activity.articlecollect.adapter.ArticleCollectAdapter;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangbing on 2016/11/28.
 */

public class ArticleCollectActivity extends BaseActivity<ArticleCollectPresenterImpl> implements ArticleCollectContract.View,OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.swipe_target)
    RecyclerView rvList;
    @BindView(R.id.ll_remove)
    LinearLayout llRemove;
    @BindView(R.id.tv_cancelcollect)
    TextView tvCancel;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private int mCurrenPage = 2;
    private int mTotalPage;


    private List<ArticleCollectBean.DataBean.ListBean> mDataList = new ArrayList<>();
    private ArticleCollectAdapter collectAdapter;
    private boolean isEditMode = false;
    private List<ArticleCollectBean.DataBean.ListBean> mList;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_article_collect;
    }

    @Override
    protected void initEventAndData() {

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);

        //reload按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getArticleCollectInfo(1);
            }
        });

        //加载布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态

        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //reload按钮监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getArticleCollectInfo(1);
                }
            });
            return;
        }


        mPresenter.getArticleCollectInfo(1);
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

    @Override
    public void showError(String msg) {
        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }
        loadingLayout.setStatus(LoadingLayout.Success);//加载成功
        ToastUtils.showShrotMsg(mContext, msg);
    }


    @Override
    public void showContent(ArticleCollectBean articleCollectBean) {
        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (isEditMode) {
            isEditMode = false;
            collectAdapter.changeMode();
            tvMode.setText("编辑");
            llRemove.setVisibility(View.GONE);

        } else {
            isEditMode = false;

            tvMode.setText("编辑");
            llRemove.setVisibility(View.GONE);

        }


        if (articleCollectBean.getData().getList() == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }

        if (articleCollectBean.getData().getList().size() == 0) {

            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            tvMode.setVisibility(View.GONE);
            llRemove.setVisibility(View.GONE);
            return;
        }
        tvMode.setVisibility(View.VISIBLE);
        //如果刷新 重置初始页
        if (loadingLayout != null) {
            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
        }
        mCurrenPage = 2;
        mTotalPage = articleCollectBean.getData().getTotal_page();
        mList = articleCollectBean.getData().getList();
        this.mDataList = mList;
        collectAdapter = new ArticleCollectAdapter(mContext, mList);
        rvList.setAdapter(collectAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvList.setLayoutManager(staggeredGridLayoutManager);




        collectAdapter.setOnStateChangeListener(new ArticleCollectAdapter.OnStateChangeListener() {
            @Override
            public void onSateChange(int position, boolean isEditmode) {
                if (isEditmode) {
                    tvMode.setText("取消");
                    llRemove.setVisibility(View.VISIBLE);
                } else {
                    tvMode.setText("编辑");
                    llRemove.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCheckSateChange(int position, boolean isCheck) {
                //当条目选中状态改变的监听


            }
        });
        collectAdapter.setOnRemoveStateChangeListener(new ArticleCollectAdapter.OnStateRemoveChangeListener() {
            @Override
            public void onRemove(List<Integer> recodeRemove) {
                //当点下取消收藏按钮后  ---》 回调 请求网络删除

                StringBuffer stringBuffer = new StringBuffer();
                //拼接删除的字符串
                for (int i = 0; i < collectAdapter.getSelectedItems().size(); i++) {
                    Integer integer = collectAdapter.getSelectedItems().get(i);
                    if (i == collectAdapter.getSelectedItems().size() - 1) {
                        stringBuffer.append(mDataList.get(integer).getArticle_id());
                    } else {
                        stringBuffer.append(mDataList.get(integer).getArticle_id() + ",");
                    }
                }
                //请求删除
                mPresenter.deleteLike(stringBuffer.toString(), Constants.LIKE_TYPE_ARTICLE);
            }
        });


        collectAdapter.setOnItemClickListener(new ArticleCollectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent();
                intent.putExtra("id", String.valueOf(mList.get(position).getArticle_id()));
                Logger.e("" + mList.get(position).getArticle_id());
                intent.setClass(mContext, ArticleDetailActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in, 0);
            }
        });
    }

    @Override
    public void showDeleteResult(int result, String msg) {

        //删除结果回调，如果从服务器删除成功 则通知adapter删除数据
        if (result == Constants.LIKE_RESULT_SINGFILED) {
            ToastUtils.showShrotMsg(mContext, msg);
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
        } else if (result == Constants.LIKE_TYPE_FILED) {
            ToastUtils.showShrotMsg(mContext, msg);
        } else {
            //刷新
            if (mPresenter!=null)
            mPresenter.getArticleCollectInfo(1);

            collectAdapter.setOnItemClickListener(new ArticleCollectAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Intent intent = new Intent();
                    intent.putExtra("id", String.valueOf(mDataList.get(position).getArticle_id()));
                    intent.setClass(mContext, ArticleDetailActivity.class);
                    mContext.startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);

                }
            });
            ToastUtils.showShrotMsg(mContext, msg);
        }
    }

    @Override
    public void showMore(ArticleCollectBean articleCollectBean) {

        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setLoadingMore(false);
        }



        if (articleCollectBean.getCode() == 100) {
            mCurrenPage = articleCollectBean.getData().getCurrent_page();
            mCurrenPage++;
            mTotalPage = articleCollectBean.getData().getTotal_page();
            collectAdapter.addMore(articleCollectBean.getData().getList());
        }


    }


    @OnClick(R.id.tv_mode)
    void changeMode() {
        if (isEditMode) {
            isEditMode = false;
            llRemove.setVisibility(View.GONE);
            tvMode.setText("编辑");
            collectAdapter.clearSelectedState();
            collectAdapter.changeMode();

        } else {
            collectAdapter.clearSelectedState();
            collectAdapter.setOnItemClickListener(null);
            collectAdapter.changeMode();
            tvMode.setText("取消");
            llRemove.setVisibility(View.VISIBLE);
            isEditMode = true;

        }
    }


    @OnClick(R.id.ll_remove)
    void removeCollect() {
        if ( collectAdapter.getSelectedItems().size() == 0){
            isEditMode = false;
            tvMode.setText("编辑");
            collectAdapter.clearSelectedState();
            collectAdapter.changeMode();
            llRemove.setVisibility(View.GONE);

            return;
        }
        collectAdapter.removeData();
    }


    @Override
    public void onLoadMore() {

        // load more data here
        if (mCurrenPage <= mTotalPage) {
            mPresenter.getMoreData(mCurrenPage);
        } else if (mCurrenPage > mTotalPage) {
            //没有更多了
            if (swipeToLoadLayout != null) {
                swipeToLoadLayout.setLoadingMore(false);
            }
        }

    }

    @Override
    public void onRefresh() {
        mPresenter.getArticleCollectInfo(1);
    }
}
