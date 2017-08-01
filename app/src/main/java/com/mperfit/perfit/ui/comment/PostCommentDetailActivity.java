package com.mperfit.perfit.ui.comment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.APIExceptionBean;
import com.mperfit.perfit.model.bean.AddPostLikeBean;
import com.mperfit.perfit.model.bean.EventCommentBean;
import com.mperfit.perfit.model.bean.PostCommentBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.comment.adapter.PostCommentListAdapter;
import com.mperfit.perfit.ui.otherspersonal.activity.OthersPersonalActivity;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/2/15.
 */

public class PostCommentDetailActivity extends SimpleActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.tb_detail_toolbar)
    Toolbar tbDetailToolbar;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.et_input)
    EditText editText;

    private RetrofitHelper mRetrofitHelper;
    private int mTotalPage;
    private int mCurrentPage = 1;
    private int mCommentCount;
    private List<PostCommentBean.DataBean.ListBean> mList;
    private long postId;
    private PostCommentListAdapter mCommentListAdapter;
    //帖子的位置
    private int mPosition;

    @Override
    protected int getLayout() {
        return R.layout.activity_post_comment_detail;
    }


    @Override
    protected void initEventAndData() {
        tvTbTitle.setText("评论");
        initEvent();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.divider_gray)));
        mRetrofitHelper = new RetrofitHelper(getApplicationContext());
        Intent intent = getIntent();
        postId = intent.getLongExtra(Constants.POST_ID, 0);
        mPosition = intent.getIntExtra("position", -1);
        Logger.e("postion:" + mPosition + "Id:" + postId);
        getData(postId);
        setBackTouch(ibBack);

    }

    private void initEvent() {
        //设置刷新
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        initListener();




    }


    private void initListener() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_DOWN:
                            if (checkLogin()) return true;

                            sendMessage(editText.getText().toString().trim());
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                            imm.showSoftInput(editText,InputMethodManager.SHOW_FORCED);
                            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
                            editText.setText("");
                            return true;
                        case KeyEvent.ACTION_UP:

                            //发送请求
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });





    }




    private void sendMessage(String content) {
        Subscription subscription = mRetrofitHelper.getAddComment(postId, content)
                .compose(RxUtil.<AddPostLikeBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<AddPostLikeBean.DataBean>() {
                    @Override
                    public void call(AddPostLikeBean.DataBean dataBean) {
                        mCommentCount++;
                        EventCommentBean eventCommentBean = new EventCommentBean(mPosition);
                        eventCommentBean.setCount(mCommentCount);
                        eventCommentBean.setId(postId);
                        RxBusUtils.getDefault().postSticky(eventCommentBean);

                        getData(postId);
                        Intent intent = new Intent();
                        intent.putExtra(Constants.COMMENT_COUNT, mCommentCount);
                        setResult(Activity.RESULT_OK, intent);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShrotMsg(mContext, throwable.getMessage());
                    }
                });

        addSubscrebe(subscription);
    }

    private void getData(long postId) {
        Subscription subscribe = mRetrofitHelper.getPostCommentList(1, postId)
                .compose(RxUtil.<PostCommentBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .subscribe(new Action1<PostCommentBean.DataBean>() {
                    @Override
                    public void call(final PostCommentBean.DataBean dataBean) {
                        if (swipeToLoadLayout != null && swipeToLoadLayout.isRefreshing()) {
                            swipeToLoadLayout.setRefreshing(false);
                        }
                        mTotalPage = dataBean.getTotal_page();
                        mCommentCount = dataBean.getCount();
                        mList = dataBean.getList();
                        tvTbTitle.setText("评论(" + mCommentCount + ")");

                        mCommentListAdapter = new PostCommentListAdapter(mContext, R.layout.item_post_comment_detail, mList);
                        mRecyclerView.setAdapter(mCommentListAdapter);

//                        mCommentListAdapter.addData(dataBean.getList());

                        mCommentListAdapter.setOnItemClickListener(new PostCommentListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, View view) {
                                if (!SharedPreferenceUtil.isMe(dataBean.getList().get(position).getUser_id())) {
                                    //进入others个人中心

                                    if (checkLogin()) return;

                                    Intent intent = new Intent(mContext, OthersPersonalActivity.class);
                                    intent.putExtra("user_id", dataBean.getList().get(position).getUser_id());
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                                } else {
                                    if (checkLogin()) return;
                                    //进入自己的个人中心
                                    long selfId = SharedPreferenceUtil.getUserid();
                                    Intent intent = new Intent(mContext, MySelfPersonalCenterActivity.class);
                                    intent.putExtra("user_id", selfId);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                                }

                            }
                        });


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (swipeToLoadLayout != null && swipeToLoadLayout.isRefreshing()) {
                            swipeToLoadLayout.setRefreshing(false);
                        }
                    }
                });

        addSubscrebe(subscribe);
    }

    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
            return true;
        }
        return false;
    }




    @Override
    public void onLoadMore() {
        if (mCurrentPage < mTotalPage) {
            swipeToLoadLayout.setLoadMoreEnabled(true);
            Subscription subscribe = mRetrofitHelper.getPostCommentList(mCurrentPage, postId)
                    .compose(RxUtil.<PostCommentBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                    .subscribe(new Action1<PostCommentBean.DataBean>() {
                        @Override
                        public void call(PostCommentBean.DataBean dataBean) {
                            mTotalPage = dataBean.getTotal_page();
                            tvTbTitle.setText("评论(" + mCommentCount + ")");
                            mCommentListAdapter.addData(dataBean.getList());
                            mCurrentPage++;

                            swipeToLoadLayout.setLoadingMore(false);

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            swipeToLoadLayout.setLoadingMore(false);


                        }
                    });

            addSubscrebe(subscribe);
        } else {
            swipeToLoadLayout.setLoadingMore(false);
            swipeToLoadLayout.setLoadMoreEnabled(false);
        }
    }

    @Override
    public void onRefresh() {
        getData(postId);
    }
}
