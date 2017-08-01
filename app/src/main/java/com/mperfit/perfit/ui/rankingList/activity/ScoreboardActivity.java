package com.mperfit.perfit.ui.rankingList.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.DayRankingListBean;
import com.mperfit.perfit.model.bean.ScoreBoardBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.otherspersonal.activity.OthersPersonalActivity;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
import com.mperfit.perfit.ui.rankingList.adapter.DayRankingListAdapter;
import com.mperfit.perfit.ui.rankingList.adapter.ScoreBoardAdapter;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.LoadingDialog;
import com.mperfit.perfit.widget.ToastUtils;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/3/13.
 */

public class ScoreboardActivity extends SimpleActivity implements OnLoadMoreListener {
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
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.ib_qa)
    ImageButton ibQa;
    @BindView(R.id.ll_myranking)
    LinearLayout mLLMyRanking;


    private RetrofitHelper mRetrofitHelper;
    private ScoreBoardAdapter mScoreBoardAdapter;
    private List<ScoreBoardBean.DataBean.ListBean> mList;
    private int mCurrentPage;
    private int mTotalPage;
    private LoadingDialog mLoadingDialog;


    @Override
    protected int getLayout() {
        return R.layout.activity_score_board;
    }

    @Override
    protected void initEventAndData() {
        tvTbTitle.setText("积分榜");
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        mLoadingDialog = new LoadingDialog(mContext);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.show();

        setBackTouch(ibBack);
        ibQa.setVisibility(View.VISIBLE);

        ibQa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,QuestionAnswerActivity.class);
                intent.putExtra(Constants.TYPE,Constants.QATYPE_SCORE);
                startActivity(intent);
            }
        });

        mScoreBoardAdapter = new ScoreBoardAdapter(mList, mContext);
        rvList.setAdapter(mScoreBoardAdapter);

        getData();
        initEvent();
    }

    private void initEvent() {

        mScoreBoardAdapter.setOnItemClickListener(new ScoreBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, long userId) {



                if (!SharedPreferenceUtil.isMe(userId)) {
                    //进入others个人中心
                    Intent intent = new Intent(mContext, OthersPersonalActivity.class);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                } else {
                    if (checkLogin()) {
                        CheckLoginUtil.reLogin(mContext,Constants.LOGIN_FROMCOLLECT);
                        return;
                    }

                    //进入自己的个人中心
                    long selfId = SharePreferenceUtils.getLong(mContext, Constants.USER_ID, 0);
                    Intent intent = new Intent(mContext, MySelfPersonalCenterActivity.class);
                    intent.putExtra("user_id", selfId);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }


            }
        });


    }


    private void getData() {

        mRetrofitHelper = new RetrofitHelper(mContext);
        Subscription subscription = mRetrofitHelper.fetchScoreRanking(1)
                .compose(RxUtil.<ScoreBoardBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<ScoreBoardBean.DataBean>() {
                    @Override
                    public void call(ScoreBoardBean.DataBean dataBean) {
                        if (mLoadingDialog != null) {
                            mLoadingDialog.close();
                        }
                        mScoreBoardAdapter.setNewData(dataBean.getList());

                        mCurrentPage = 2;
                        mTotalPage = dataBean.getTotal_page();


                        if (dataBean.getList()==null){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            return;
                        }

                        if (dataBean.getList().size() ==0){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            return;
                        }

                        if (checkLogin()) {
                            //未登录状态 隐藏掉我的积分
                            mLLMyRanking.setVisibility(View.GONE);
                            return;
                        } else {
                            //设置自己的信息
                            tvMyRanking.setText(String.valueOf(dataBean.getUser_rank()));
                            tvMyScore.setText(String.valueOf(String.valueOf(dataBean.getUser_score())));
                        }



                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mLoadingDialog != null) {
                            mLoadingDialog.close();
                        }

                        loadingLayout.setStatus(LoadingLayout.Error);
                        loadingLayout.setEmptyText(throwable.getMessage());
                    }
                });

        addSubscrebe(subscription);
    }


    private void getMoreData(int page) {

        Subscription subscription = mRetrofitHelper.fetchScoreRanking(page)
                .compose(RxUtil.<ScoreBoardBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<ScoreBoardBean.DataBean>() {
                    @Override
                    public void call(ScoreBoardBean.DataBean dataBean) {
                        if (swipeToLoadLayout != null) {
                            swipeToLoadLayout.setLoadingMore(false);

                        }
                        mScoreBoardAdapter.addData(dataBean.getList());
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

                        loadingLayout.setStatus(LoadingLayout.Error);
                        loadingLayout.setEmptyText(throwable.getMessage());

                    }
                });

        addSubscrebe(subscription);
    }


    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
//            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
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
            swipeToLoadLayout.setLoadingMore(false);
        }
    }
}
