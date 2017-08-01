package com.mperfit.perfit.ui.rankingList.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.DayRankingListBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.otherspersonal.activity.OthersPersonalActivity;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
import com.mperfit.perfit.ui.rankingList.adapter.DayRankingListAdapter;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.LoadingDialog;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/3/13.
 */
public class WeekRankingListFragment extends SimpleFragment implements OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    RecyclerView rvList;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.tv_my_ranking)
    TextView tvMyRanking;
    @BindView(R.id.tv_my_score)
    TextView tvMyScore;
    @BindView(R.id.ll_myranking)
    LinearLayout mLLMyRanking;

    private RetrofitHelper mRetrofitHelper;
    private DayRankingListAdapter mRankingListAdapter;
    private List<DayRankingListBean.DataBean.ListBean> mList;
    private int mCurrentPage;
    private int mTotalPage;
    private LoadingDialog mLoadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking_day;
    }

    @Override
    protected void initEventAndData() {

        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);

        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        mLoadingDialog = new LoadingDialog(mContext);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.show();


        mRankingListAdapter = new DayRankingListAdapter(mList, mContext);
        rvList.setAdapter(mRankingListAdapter);

        getData();
        initEvent();
    }

    private void initEvent() {

        mRankingListAdapter.setOnItemClickListener(new DayRankingListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, long userId) {



                if (!SharedPreferenceUtil.isMe(userId)) {
                    //进入others个人中心
                    Intent intent = new Intent(mContext, OthersPersonalActivity.class);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
                } else {

                    if (checkLogin()) {
                        CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                        return;
                    }
                    //进入自己的个人中心
                    long selfId = SharePreferenceUtils.getLong(mContext, Constants.USER_ID, 0);
                    Intent intent = new Intent(mContext, MySelfPersonalCenterActivity.class);
                    intent.putExtra("user_id", selfId);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }

            }
        });


    }


    private void getData() {

        mRetrofitHelper = new RetrofitHelper(mContext);
        Subscription subscription = mRetrofitHelper.fetchSatrRanking(7, 1)
                .compose(RxUtil.<DayRankingListBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<DayRankingListBean.DataBean>() {
                    @Override
                    public void call(DayRankingListBean.DataBean dataBean) {
                        if (mLoadingDialog != null) {
                            mLoadingDialog.close();
                        }


                        if (dataBean == null){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            return;
                        }

                        if (dataBean.getList() ==null){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            return;
                        }

                        if (dataBean.getList().size()==0){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            return;
                        }


                        mRankingListAdapter.setNewData(dataBean.getList());

                        mCurrentPage = 2;
                        mTotalPage = dataBean.getTotal_page();



                        if (checkLogin()) {
                            //未登录状态 隐藏掉我的排名
                            mLLMyRanking.setVisibility(View.GONE);
                            return;
                        } else {
                            //设置自己的信息
                            tvMyRanking.setText(String.valueOf(dataBean.getUser_rank()));
                            tvMyScore.setText(String.valueOf(String.valueOf(dataBean.getUser_hot())));
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mLoadingDialog != null) {
                            mLoadingDialog.close();
                        }
                        loadingLayout.setStatus(LoadingLayout.Error);
                    }
                });

        addSubscrebe(subscription);
    }


    private void getMoreData(int page) {

        Subscription subscription = mRetrofitHelper.fetchSatrRanking(7, page)
                .compose(RxUtil.<DayRankingListBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<DayRankingListBean.DataBean>() {
                    @Override
                    public void call(DayRankingListBean.DataBean dataBean) {
                        if (swipeToLoadLayout != null) {
                            swipeToLoadLayout.setLoadingMore(false);

                        }
                        mRankingListAdapter.addData(dataBean.getList());
                        mCurrentPage = dataBean.getCurrent_page();
                        mCurrentPage++;
                        mTotalPage = dataBean.getTotal_page();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (swipeToLoadLayout != null) {
                            swipeToLoadLayout.setLoadingMore(false);

                        }
                    }
                });

        addSubscrebe(subscription);
    }


    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            return true;
        }
        return false;
    }

    @Override
    public void onLoadMore() {
        if (mCurrentPage <= mTotalPage) {
            getMoreData(mCurrentPage);
            return;
        } else {
            ToastUtils.showShrotMsg(mContext, "没有更多了...");
            swipeToLoadLayout.setLoadingMore(false);
        }
    }


}