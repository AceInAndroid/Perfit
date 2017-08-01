package com.mperfit.perfit.ui.personal.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.MyPointsListBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.personal.adapter.MypointsAdapter;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.ToastUtils;
import com.sina.weibo.sdk.net.NetUtils;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/3/14.
 */

public class MyPointsActivity extends SimpleActivity implements OnLoadMoreListener {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_total_score)
    TextView tvTotalScore;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    private RetrofitHelper mRetrofitHelper;
    private int mCurrentPage;
    private int mTotalPage;

    private List<MyPointsListBean.DataBean.ListBean> mList;
    private MypointsAdapter myPointsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_points;
    }

    @Override
    protected void initEventAndData() {
        tvTbTitle.setText("我的积分");
        setBackTouch(ibBack);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mRetrofitHelper = new RetrofitHelper(mContext);
        myPointsAdapter = new MypointsAdapter(R.layout.item_my_points, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_drawable_line));
        mRecyclerView.setAdapter(myPointsAdapter);
        if (!CheckLoginUtil.isLogin(mContext, Constants.LOGIN_FROMCOLLECT)) return;
        if (isConnnetNet()) return;
        getData(1);
    }

    private void getData(int page) {
        Subscription subscription = mRetrofitHelper.getMyPointsInfoList(page)
                .compose(RxUtil.<MyPointsListBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<MyPointsListBean.DataBean>() {
                    @Override
                    public void call(MyPointsListBean.DataBean dataBean) {
                        mList = dataBean.getList();
                        if (mList==null){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            return;
                        }

                        if (mList!=null && mList.size() ==0){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            return;
                        }

                        myPointsAdapter.addData(mList);
                        mCurrentPage = 2;
                        mTotalPage = dataBean.getTotal_page();
                        tvTotalScore.setText(dataBean.getTotal_score() + "");

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        if (isConnnetNet()) return;

                        loadingLayout.setStatus(LoadingLayout.Error);

                    }
                });

        addSubscrebe(subscription);
    }

    private boolean isConnnetNet() {
        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    getData(1);
                }
            });
            return true;
        }
        return false;
    }


    private void getMoreData(int page) {
        Subscription subscription = mRetrofitHelper.getMyPointsInfoList(page)
                .compose(RxUtil.<MyPointsListBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<MyPointsListBean.DataBean>() {
                    @Override
                    public void call(MyPointsListBean.DataBean dataBean) {
                        mCurrentPage = dataBean.getCurrent_page();
                        mCurrentPage++;
                        mTotalPage = dataBean.getTotal_page();
                        myPointsAdapter.addData(dataBean.getList());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscription);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
