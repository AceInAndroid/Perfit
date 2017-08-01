package com.mperfit.perfit.ui.competition.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.ActivityBean;
import com.mperfit.perfit.model.bean.CompetitionGameBean;
import com.mperfit.perfit.model.bean.ReLoadPersonalDataBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ActivityContract;
import com.mperfit.perfit.presenter.presenter.ActivityPresenterImpl;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.competition.activity.CompetitionGameActivity;
import com.mperfit.perfit.ui.competition.adapter.MyGameListAdapter;
import com.mperfit.perfit.ui.rankingList.activity.QuestionAnswerActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/3/15.
 */

//item_mygame.xml

//            return R.layout.fragment_game;

    //, OnRefreshListener, OnLoadMoreListener


public class GameFragment extends BaseFragment<ActivityPresenterImpl> implements ActivityContract.View {

    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
//    @BindView(R.id.swipe_refresh_header)
//    RefreshHeaderView swipeRefreshHeader;
//    @BindView(R.id.swipe_load_more_footer)
//    LoadMoreFooterView swipeLoadMoreFooter;
//    @BindView(R.id.swipeToLoadLayout)
//    SwipeToLoadLayout swipeToLoadLayout;

    private int mCurrenPage = 2;
    private int mTotalPage;
    private List<CompetitionGameBean.DataBean.ListBean> list;
    private LinearLayoutManager linearLayoutManager;
    private MyGameListAdapter mMyGameAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initEventAndData() {


//        //设置刷新
//        swipeToLoadLayout.setOnRefreshListener(this);
//        swipeToLoadLayout.setOnLoadMoreListener(this);
//        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
//        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        //加载布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态


        mMyGameAdapter = new MyGameListAdapter(R.layout.item_competition_game,list,mContext);
        linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMyGameAdapter);

        mPresenter.getGameData(1);

        //reload按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getGameData(1);
                loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态

            }
        });

        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //reload按钮监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getGameData(1);
                    loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态
                }
            });
            return;
        }


        Subscription subscription = RxBusUtils.getDefault().toObservable(ReLoadPersonalDataBean.class)
                .subscribe(new Action1<ReLoadPersonalDataBean>() {
                    @Override
                    public void call(ReLoadPersonalDataBean reLoadPersonalDataBean) {
                        //登录后重新加载


                        mPresenter.getGameData(1);
                        RetrofitHelper retrofitHelper = new RetrofitHelper(mContext);
                        Subscription subscribe = retrofitHelper.fetchCompetitionGameListInfo(1)
                                .compose(RxUtil.<CompetitionGameBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                                .subscribe(new Action1<CompetitionGameBean.DataBean>() {
                                    @Override
                                    public void call(CompetitionGameBean.DataBean dataBean) {

//                                        mMyGameAdapter.setNewData(competitionGameBean.getList());



                                        mMyGameAdapter = new MyGameListAdapter(R.layout.item_competition_game,dataBean.getList(),mContext);
                                        mRecyclerView.setAdapter(mMyGameAdapter);




                                        mMyGameAdapter.setOnItemClickListener(new MyGameListAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(int position, View view) {
                                                //去赛事详情
                                                Intent intent = new Intent();
                                                intent.setClass(mContext, CompetitionGameActivity.class);
                                                intent.putExtra(Constants.INTENT_GAMECATEGORY, competitionGameBean.getList().get(position).getCategory_id());
                                                intent.putExtra(Constants.INTENT_GAME_NAME, competitionGameBean.getList().get(position).getName());
                                                mContext.startActivity(intent);
                                                getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
                                            }

                                            @Override
                                            public void onItemClickUnLock(int position, View view) {
                                                //去介绍
                                                Intent intent = new Intent(mContext, QuestionAnswerActivity.class);
                                                intent.putExtra(Constants.TYPE, Constants.QATYPE_GAME);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onItemClickNoLogin(int position, View view) {
                                                CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                                            }
                                        });


                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });

                        addSubscrebe(subscribe);
                        Logger.e("获取到登录成功 刷新列表");

                    }
                });

        addSubscrebe(subscription);

    }

    private CompetitionGameBean.DataBean competitionGameBean;



    @Override
    public void showGameContent(final CompetitionGameBean.DataBean competitionGameBean) {

        this.competitionGameBean = competitionGameBean;

        if (this.competitionGameBean.getList() == null && loadingLayout!=null ) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }

//        if (swipeToLoadLayout != null) {
//            swipeToLoadLayout.setRefreshing(false);
//        }


        if (this.competitionGameBean.getList().size() == 0) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        }

        //如果刷新 重置初始页
        mCurrenPage = 2;

        if (loadingLayout != null) {
            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
        }


        mMyGameAdapter.setNewData(competitionGameBean.getList());

        mMyGameAdapter.setOnItemClickListener(new MyGameListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //去赛事详情
                Intent intent = new Intent();
                intent.setClass(mContext, CompetitionGameActivity.class);
                intent.putExtra(Constants.INTENT_GAMECATEGORY,competitionGameBean.getList().get(position).getCategory_id());
                intent.putExtra(Constants.INTENT_GAME_NAME,competitionGameBean.getList().get(position).getName());
                mContext.startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }

            @Override
            public void onItemClickUnLock(int position, View view) {
                //去介绍
                Intent intent = new Intent(mContext,QuestionAnswerActivity.class);
                intent.putExtra(Constants.TYPE,Constants.QATYPE_GAME);
                startActivity(intent);
            }

            @Override
            public void onItemClickNoLogin(int position, View view) {
                CheckLoginUtil.reLogin(mContext,Constants.LOGIN_FROMCOLLECT);
            }
        });



    }

    @Override
    public void showMoreGameContent(CompetitionGameBean.DataBean competitionGameBean) {
        mMyGameAdapter.addData(competitionGameBean.getList());
    }

    @Override
    public void showError(String msg) {
//        if (swipeToLoadLayout != null) {
//            swipeToLoadLayout.setRefreshing(false);
//            swipeToLoadLayout.setLoadingMore(false);
//        }

        if (loadingLayout!=null )
            loadingLayout.setStatus(LoadingLayout.Error);

        ToastUtils.showShrotMsg(mContext, msg);
    }

//    @Override
//    public void onLoadMore() {
//
//        // load more data here
//        if (mCurrenPage <= mTotalPage) {
//            mPresenter.getMoreDate(mCurrenPage);
//        } else if (mCurrenPage > mTotalPage) {
//            //没有更多了
//            if (swipeToLoadLayout != null) {
//                swipeToLoadLayout.setLoadingMore(false);
//            }
//        }
//
//
//    }

//    @Override
//    public void onRefresh() {
//        mPresenter.getGameData(1);
//
//    }





    @Override
    public void showMoreContent(ActivityBean activityBean) {

    }


    @Override
    public void showContent(final ActivityBean activityBean) {

    }

}