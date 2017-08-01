package com.mperfit.perfit.ui.community.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.AddPostLikeBean;
import com.mperfit.perfit.model.bean.EventCommentBean;
import com.mperfit.perfit.model.bean.EventFollowBean;
import com.mperfit.perfit.model.bean.EventLikeBean;
import com.mperfit.perfit.model.bean.MyFollowPostBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.comment.PostCommentDetailActivity;
import com.mperfit.perfit.ui.community.activity.HomePostPreviewActivity;
import com.mperfit.perfit.ui.community.adapter.FollowListAdapter;
import com.mperfit.perfit.ui.otherspersonal.activity.OthersPersonalActivity;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.utils.WeiboShareUtils;
import com.mperfit.perfit.utils.WeixinShareUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.SharePopwindow;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/2/10.
 */

public class FollowFragment extends SimpleFragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    private RetrofitHelper mRetrofitHelper;
    private int mTotalPage;
    private int mCurrentPage;
    private List<MyFollowPostBean.DataBean.ListBean> mList;
    private FollowListAdapter mFollowAdapter;
    private Subscription mRemoveLikesubscribe;
    private Subscription mAddLikeSubscribe;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initEventAndData() {

        if (checkLogin()) return;

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_drawable_line));
        mRecyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mFollowAdapter = new FollowListAdapter(mContext, mList);
        mRecyclerView.setAdapter(mFollowAdapter);
        mRetrofitHelper = new RetrofitHelper(mContext);
        getData();
        setLoading();
        initEvent();

    }

    private void initEvent() {
        initClick();


        Subscription subscription = RxBusUtils.getDefault().toObservableSticky(EventCommentBean.class)
                .subscribe(new Action1<EventCommentBean>() {
                    @Override
                    public void call(EventCommentBean eventCommentBean) {
                        //用于接收评论的事件
                        //改变状态 commentcount+1 更新adapter
                        mFollowAdapter.updateCommentState(eventCommentBean.getPosition(), eventCommentBean.getCount(), eventCommentBean.getId());
                    }
                });
        addSubscrebe(subscription);

        Subscription likeSubscription = RxBusUtils.getDefault().toObservableSticky(EventLikeBean.class)
                .subscribe(new Action1<EventLikeBean>() {
                    @Override
                    public void call(EventLikeBean eventLikeBean) {
                        //用于接收喜欢的事件
                        //0是取消 1是喜欢
                        //改变状态
                        mFollowAdapter.updateLikeState(eventLikeBean.getPosition(), eventLikeBean.getType(), eventLikeBean.getId());
                    }
                });

        addSubscrebe(likeSubscription);


        Subscription subscribe = RxBusUtils.getDefault().toObservableSticky(EventFollowBean.class)
                .subscribe(new Action1<EventFollowBean>() {
                    @Override
                    public void call(EventFollowBean eventFollowBean) {
                        //接收follow消息刷新主页
                        getData();

                    }
                });

        addSubscrebe(subscribe);


    }

    private void setLoading() {
        //加载布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态
        //加载更多
        //reload按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                getData();
            }
        });
        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //reload按钮监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    if (checkLogin()) return;
                    getData();
                }
            });
            return;
        }
    }


    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMLOGIN);
            return true;
        }
        return false;
    }


    private void initClick() {

        mFollowAdapter.setOnClickListener(new FollowListAdapter.OnClickListener() {
            @Override
            public void onPostItemClick(int position, View view) {
                try {
                    //进入帖子详情
//                    Intent intent = new Intent(mContext, PostDetaillActivity.class);
//                    intent.putExtra(Constants.POST_ID, mList.get(position).getId());
//                    intent.putExtra("position", position);
//                    startActivity(intent);


                    //进入帖子详情
                    Intent intent = new Intent(mContext, HomePostPreviewActivity.class);
                    intent.putExtra(Constants.POST_ID, mList.get(position).getId());
                    intent.putExtra("position", position);
                    intent.putExtra(Constants.IMAGE_URL_EXTRA, mList.get(position).getImg_url_android());
                    intent.putExtra(Constants.VIEW_INFO_EXTRA, createViewInfoBundle(view));
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, getString(R.string.post_imagview));
                    mContext.startActivity(intent);
                    mActivity.overridePendingTransition(0, 0);


                } catch (IndexOutOfBoundsException e) {

                }

            }

            @Override
            public void onBtnLikeClick(final int position) {
                if (mList == null) {
                    return;
                }
                if (checkLogin()) return;

                if (mAddLikeSubscribe == null) {

                    //mFollowAdapter.updateLikeState(position, 1);
                    mAddLikeSubscribe = mRetrofitHelper.getAddPostLike(mList.get(position).getId())
                            .compose(RxUtil.<AddPostLikeBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                            .subscribe(new Action1<AddPostLikeBean.DataBean>() {
                                @Override
                                public void call(AddPostLikeBean.DataBean dataBean) {
//                                mFollowAdapter.updateLikeState(position, 1);

                                    EventLikeBean eventLikeBean = new EventLikeBean(1, position);
                                    eventLikeBean.setId(mList.get(position).getId());
                                    RxBusUtils.getDefault().postSticky(eventLikeBean);


                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {

                                }
                            });
                    addSubscrebe(mAddLikeSubscribe);
                }


            }

            @Override
            public void onBtnUnLikeClick(final int position) {

                if (!CheckLoginUtil.isLogin(mContext, Constants.LOGIN_FROMCOLLECT)) {
                    return;
                }

                if (mList == null) {
                    return;
                }
                if (mRemoveLikesubscribe == null) {
                    //                                mFollowAdapter.updateLikeState(position, 0);
                    mRemoveLikesubscribe = mRetrofitHelper.getRemovePostLike(mList.get(position).getId())
                            .compose(RxUtil.<AddPostLikeBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                            .subscribe(new Action1<AddPostLikeBean.DataBean>() {
                                @Override
                                public void call(AddPostLikeBean.DataBean dataBean) {
//                                mFollowAdapter.updateLikeState(position, 0);

                                    EventLikeBean eventLikeBean = new EventLikeBean(0, position);
                                    eventLikeBean.setId(mList.get(position).getId());
                                    RxBusUtils.getDefault().postSticky(eventLikeBean);


                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {

                                }
                            });


                    addSubscrebe(mRemoveLikesubscribe);
                }


            }

            @Override
            public void onBtnCommentClick(int position) {
                //去评论列表
                Intent intent = new Intent(mContext, PostCommentDetailActivity.class);
                intent.putExtra("postId", mList.get(position).getId());
                intent.putExtra("position", position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }

            @Override
            public void onBtnShareClick(int position) {
                showPopFormBottom(mRecyclerView, position);
            }

            @Override
            public void onBtnHeadAndNameClick(int position) {
                //进入others个人中心
                if (checkLogin()) return;

                if (mList==null){
                    return;
                }
                if (mList.size() ==0){
                    return;
                }

                long userId = mList.get(position).getUser_id();
                if (!SharedPreferenceUtil.isMe(userId)) {
                    //进入others个人中心
                    Intent intent = new Intent(mContext, OthersPersonalActivity.class);
                    intent.putExtra("user_id", mList.get(position).getUser_id());
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
                } else {
                    if (checkLogin()) return;

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

    /**
     * 给图片预览传递原始view的信息
     *
     * @param view
     * @return
     */
    private Bundle createViewInfoBundle(View view) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        Bundle b = new Bundle();
        int left = screenLocation[0];
        int top = screenLocation[1];
        int width = view.getWidth();
        int height = view.getHeight();
        b.putInt("left", left);
        b.putInt("top", top);
        b.putInt("width", width);
        b.putInt("height", height);
        return b;
    }


    private void getData() {
        changeRefreshState();
        if (mRecyclerView != null) {
            Subscription subscription = mRetrofitHelper.getMyFollowPost(1)
                    .compose(RxUtil.<MyFollowPostBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                    .subscribe(new Action1<MyFollowPostBean.DataBean>() {
                        @Override
                        public void call(MyFollowPostBean.DataBean dataBean) {
                            mList = dataBean.getList();
                            if (mList == null) {
                                loadingLayout.setStatus(LoadingLayout.Empty);
                                loadingLayout.setEmptyText("还没有关注的人,快去关注吧~");

                            } else {
                                loadingLayout.setStatus(LoadingLayout.Success);
                            }
                            mCurrentPage = dataBean.getCurrent_page();
                            mTotalPage = dataBean.getTotal_page();
                            mFollowAdapter.addData(mList);


                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingLayout.setStatus(LoadingLayout.Error);
                            loadingLayout.setErrorText(throwable.getMessage());
                            Logger.e(throwable.getMessage());
                        }
                    });
            addSubscrebe(subscription);
        }


    }


    private void getData(int page) {
        changeRefreshState();
        if (mRecyclerView != null)
            mRetrofitHelper.getMyFollowPost(page)
                    .compose(RxUtil.<MyFollowPostBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                    .subscribe(new Action1<MyFollowPostBean.DataBean>() {
                        @Override
                        public void call(MyFollowPostBean.DataBean dataBean) {
                            List<MyFollowPostBean.DataBean.ListBean> list = dataBean.getList();
                            if (list == null) {
                                loadingLayout.setStatus(LoadingLayout.Empty);
                                loadingLayout.setEmptyText("暂无数据哟~");

                            } else {
                                loadingLayout.setStatus(LoadingLayout.Success);
                            }
                            mCurrentPage = dataBean.getCurrent_page();
                            mTotalPage = dataBean.getTotal_page();
                            mFollowAdapter.loadMoreData(list);


                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
//                            loadingLayout.setStatus(LoadingLayout.Error);
                            Logger.e(throwable.getMessage());
                        }
                    });
    }


    private void changeRefreshState() {
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }

        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }


    @Override
    public void onLoadMore() {

        if (mCurrentPage < mTotalPage) {
            mCurrentPage++;
            getData(mCurrentPage);
        } else {
            changeRefreshState();
        }

    }

    @Override
    public void onRefresh() {

        getData();

    }

    public void showPopFormBottom(View view, int position) {
        SharePopwindow sharePopwindow = new SharePopwindow(mContext, position);
        //showAtLocation(View parent, int gravity, int x, int y)
        if (mList != null && mList.size() != 0) {
            sharePopwindow.isMe(mList.get(position).getUser_id());
        }
        sharePopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        sharePopwindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        sharePopwindow.setOnClickListener(new SharePopwindow.OnClickListener() {
            @Override
            public void onWeixinItemClick(final int position) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap thumBitmap = null;

                        try {
                            thumBitmap = Glide.with(mContext)
                                    .load(mList.get(position).getImg_url_android())
                                    .asBitmap() //必须
                                    .centerCrop()
                                    .into(90, 90)
                                    .get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        WeixinShareUtil weixinShareUtil = WeixinShareUtil.getInstance(mContext);
                        weixinShareUtil.shareByWeixin(weixinShareUtil.new ShareContentWebpage("分享" + mList.get(position).getUser_name() + "的帖子", mList.get(position).getContent(),
                                "http://wap.mperfit.com/share/note/" + mList.get(position).getId(), thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_TALK);

                    }
                }).start();


            }

            @Override
            public void onQuanItemClick(final int position) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap thumBitmap = null;
                        WeixinShareUtil weixinShareUtiltoQuan = WeixinShareUtil.getInstance(mContext);

                        try {
                            thumBitmap = Glide.with(mContext)
                                    .load(mList.get(position).getImg_url_android())
                                    .asBitmap()
                                    .placeholder(R.drawable.icon)
                                    .centerCrop()
                                    .into(90, 90)
                                    .get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        weixinShareUtiltoQuan.shareByWeixin(weixinShareUtiltoQuan.new ShareContentWebpage("分享" +
                                        mList.get(position).getUser_name() + "的帖子", mList.get(position).getContent(),
                                        "http://wap.mperfit.com/note/activity/" + mList.get(position).getId(), thumBitmap),
                                WeixinShareUtil.WEIXIN_SHARE_TYPE_FRENDS);
                    }
                }).start();


            }

            @Override
            public void onWeiboItemClick(final int position) {
                Glide.with(mContext)
                        .load(mList.get(position).getImg_url_android())
                        .asBitmap()
                        .placeholder(R.drawable.icon)
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {

                                Drawable drawable = getResources().getDrawable(R.drawable.icon);
                                Bitmap defaultBitmap = SystemUtil.drawableToBitmap(drawable);
                                WeiboShareUtils.getDefault(getActivity()).sendMultiMessage("分享" + mList.get(position).getUser_name() +
                                                "的帖子", mList.get(position).getContent(),
                                        "http://wap.mperfit.com/share/note/" + mList.get(position).getId(), defaultBitmap);

                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .error(R.drawable.icon)
                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

                                if (bitmap != null) {

                                    WeiboShareUtils.getDefault(getActivity()).sendMultiMessage("分享" + mList.get(position).getUser_name() +
                                                    "的帖子", mList.get(position).getContent(),
                                            "http://wap.mperfit.com/note/activity/" + mList.get(position).getId(), bitmap);
                                }

                            }

                        });

            }

            @Override
            public void onDeleteItemClick(final int position) {
                Subscription subscription = mRetrofitHelper.getDeletePostInfo(mList.get(position).getId())
                        .compose(RxUtil.<AddPostLikeBean>rxSchedulerHelper(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                        .subscribe(new Action1<AddPostLikeBean>() {
                            @Override
                            public void call(AddPostLikeBean addPostLikeBean) {
                                int code = addPostLikeBean.getCode();
                                switch (code) {
                                    case 100:
                                        mFollowAdapter.deleteItem(position);
                                        break;
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });

                addSubscrebe(subscription);
            }
        });
    }


}
