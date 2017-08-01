package com.mperfit.perfit.ui.otherspersonal.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.mperfit.perfit.model.bean.AddPostLikeBean;
import com.mperfit.perfit.model.bean.EventFollowBean;
import com.mperfit.perfit.model.bean.FansListBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.otherspersonal.adapter.FansListAdapter;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by zhangbing on 2017/2/15.
 */

public class FansListActivity extends SimpleActivity implements OnRefreshListener,OnLoadMoreListener{
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.tb_detail_toolbar)
    Toolbar tbDetailToolbar;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView rvList;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;

    private RetrofitHelper mRetrofitHelper;
    private int mCurrentPage = 1;
    private int mTotalPage;
    private List<FansListBean.DataBean.ListBean> mList;
    private FansListAdapter mFansAdapter;
    private long mUserId;


    @Override
    protected int getLayout() {
        return R.layout.activity_fans;
    }

    @Override
    protected void initEventAndData() {
        if (checkLogin()) return;
        tvTbTitle.setText("粉丝");
        Intent intent = getIntent();
        mUserId = intent.getLongExtra(Constants.USER_ID, 0);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_drawable_line));
        setBackTouch(ibBack);
        mRetrofitHelper = new RetrofitHelper(mContext);
        getData();
        initEvnet();
    }

    private void initEvnet() {



    }

    private void getData() {

        Subscription subscription = mRetrofitHelper.getFansList(1,mUserId)
                .compose(RxUtil.<FansListBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<FansListBean.DataBean>() {
                    @Override
                    public void call(FansListBean.DataBean dataBean) {
                        setRefreshFinish();
                        mCurrentPage = dataBean.getCurrent_page();
                        mTotalPage = dataBean.getTotal_page();
                        mList = dataBean.getList();
                        if (mList == null){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            loadingLayout.setEmptyText("还没有粉丝哟~");
                        } else if(mList.size() == 0){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            loadingLayout.setEmptyText("还没有粉丝哟~");
                        } else {
                            loadingLayout.setStatus(LoadingLayout.Success);
                        }

                        mFansAdapter = new FansListAdapter(mContext,R.layout.item_fans_list, mList);
                        rvList.setAdapter(mFansAdapter);
                        mFansAdapter.setOnItemClickListener(new FansListAdapter.OnFollowClickListener() {
                            @Override
                            public void onFollowClick(int position, long id) {
                                if (checkLogin()) return;

                                //去关注
                                addFollow(position,id,ActivityLifeCycleEvent.DESTROY,lifecycleSubject);
                            }
                            @Override
                            public void onRemoveFollowClick(int position, long id) {
                                if (checkLogin()) return;

                                //移除关注
                                removeFollow(position,id,ActivityLifeCycleEvent.DESTROY,lifecycleSubject);

                            }
                        });


                        mFansAdapter.setOnHeadAndNameClickListener(new FansListAdapter.OnHeadAndNameClickListener() {
                            @Override
                            public void onItemClick(int position, View view) {
                                if (!SharedPreferenceUtil.isMe(mList.get(position).getUser_id())) {
                                    //进入others个人中心
                                    if (checkLogin()) return;

                                    Intent intent = new Intent(mContext, OthersPersonalActivity.class);
                                    intent.putExtra("user_id", mList.get(position).getUser_id());
                                    startActivity(intent);
                                    mContext.overridePendingTransition(R.anim.right_in, R.anim.right_out);
                                } else {
                                    if (checkLogin()) return;

                                    //进入自己的个人中心
                                    long selfId = SharedPreferenceUtil.getUserid();
                                    Intent intent = new Intent(mContext, MySelfPersonalCenterActivity.class);
                                    intent.putExtra("user_id", selfId);
                                    startActivity(intent);
                                    mContext.overridePendingTransition(R.anim.right_in, R.anim.right_out);
                                }
                            }
                        });


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setRefreshFinish();

                    }
                });

        addSubscrebe(subscription);

    }



    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
            return true;
        }
        return false;
    }


    public void addFollow(final int position, long userId, ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject) {
        Subscription subscribe = mRetrofitHelper.getFollowInfo(userId)
                .compose(RxUtil.<AddPostLikeBean.DataBean>handleResult(lifeCycleEvent, subject))
                .subscribe(new Action1<AddPostLikeBean.DataBean>() {
                    @Override
                    public void call(AddPostLikeBean.DataBean dataBean) {
                        //更新状态
                        mFansAdapter.updateState(position,1);
                        RxBusUtils.getDefault().postSticky(new EventFollowBean(1, mUserId));
                        //设置margin

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscribe);

    }

    public void removeFollow(final int position, long userId, ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject) {

        Subscription subscribe = mRetrofitHelper.getRemoveFollowInfo(userId)
                .compose(RxUtil.<AddPostLikeBean.DataBean>handleResult(lifeCycleEvent, subject))
                .subscribe(new Action1<AddPostLikeBean.DataBean>() {
                    @Override
                    public void call(AddPostLikeBean.DataBean dataBean) {
                        mFansAdapter.updateState(position,0);
                        //发送消息给广场页等
                        RxBusUtils.getDefault().postSticky(new EventFollowBean(0, mUserId));
                        //设置margin

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });


        addSubscrebe(subscribe);

    }





    private void setRefreshFinish() {
        if (swipeToLoadLayout!=null && swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout!=null && swipeToLoadLayout.isLoadingMore()){
            swipeToLoadLayout.setLoadingMore(false);
        }
    }


    @Override
    public void onLoadMore() {

        if (mCurrentPage< mTotalPage){
            getMore();
        } else {
            setRefreshFinish();
        }

    }

    private void getMore() {
        Subscription subscription = mRetrofitHelper.getFansList(mCurrentPage++,mUserId)
                .compose(RxUtil.<FansListBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<FansListBean.DataBean>() {
                    @Override
                    public void call(FansListBean.DataBean dataBean) {
                        setRefreshFinish();
                        mCurrentPage = dataBean.getCurrent_page();
                        mTotalPage = dataBean.getTotal_page();
                        List<FansListBean.DataBean.ListBean> list = dataBean.getList();
                        mFansAdapter.addMore(list);

                        if (dataBean.getList().size() == 0){
                            loadingLayout.setStatus(LoadingLayout.Empty);
                            loadingLayout.setEmptyText("还没有人关注哟!");
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setRefreshFinish();

                    }
                });

        addSubscrebe(subscription);
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
