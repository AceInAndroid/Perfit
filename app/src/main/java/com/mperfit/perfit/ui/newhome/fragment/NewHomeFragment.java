package com.mperfit.perfit.ui.newhome.fragment;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

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
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.EventCommentBean;
import com.mperfit.perfit.model.bean.EventFollowBean;
import com.mperfit.perfit.model.bean.EventLikeBean;
import com.mperfit.perfit.model.bean.NewHomeBean;
import com.mperfit.perfit.model.bean.SquareListBean;
import com.mperfit.perfit.model.bean.SquareMorePostBean;
import com.mperfit.perfit.presenter.contract.NewHomeContract;
import com.mperfit.perfit.presenter.presenter.NewHomePresenterImpl;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.comment.PostCommentDetailActivity;
import com.mperfit.perfit.ui.community.activity.HomePostPreviewActivity;
import com.mperfit.perfit.ui.newhome.adapter.NewHomeAdapter;
import com.mperfit.perfit.ui.otherspersonal.activity.OthersPersonalActivity;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
import com.mperfit.perfit.ui.rankingList.activity.ScoreboardActivity;
import com.mperfit.perfit.ui.rankingList.activity.StarRankingListActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.utils.WeiboShareUtils;
import com.mperfit.perfit.utils.WeixinShareUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.SharePopwindow;
import com.mperfit.perfit.widget.ToastUtils;
import com.mperfit.perfit.widget.recycleview.SquareListDivider;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/3/6.
 */

public class NewHomeFragment extends BaseFragment<NewHomePresenterImpl> implements NewHomeContract.View, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView mRvHomeList;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.ib_back)
    ImageButton ibBack;

    private NewHomeBean.DataBean dataBean;
    private NewHomeAdapter mNewHomeListAdapter;
    private List<NewHomeBean.DataBean.NoteListBean> mNoteList;
    private Subscription subscription;
    private Subscription likeSubscription;
    private Subscription mFollowSubscribe;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newhome;
    }

    @Override
    protected void initEventAndData() {
        tvTbTitle.setText("首页");
        ibBack.setVisibility(View.GONE);
        if (loadingLayout.isShown()){
           if (loadingLayout.getStatus()==LoadingLayout.Loading){
               loadingLayout.setStatus(LoadingLayout.Success);
           }
        }
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        mRvHomeList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvHomeList.setHasFixedSize(true);
        mRvHomeList.addItemDecoration(new SquareListDivider(mContext, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.divider_gray)));
        mNewHomeListAdapter = new NewHomeAdapter(getActivity(), dataBean);
        mRvHomeList.setAdapter(mNewHomeListAdapter);
        ((SimpleItemAnimator) mRvHomeList.getItemAnimator()).setSupportsChangeAnimations(false);
        mPresenter.getHomePageList(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        initEvent();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中

        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getHomePageList( ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }
        });
        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getHomePageList( ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
                }
            });
            return;
        }

    }


    private void initEvent() {
        mNewHomeListAdapter.setOnClickListener(new NewHomeAdapter.OnClickListener() {
            @Override
            public void onPostItemClick(int position, View view) {
                //进入帖子详情
                Intent intent = new Intent(mContext, HomePostPreviewActivity.class);
                intent.putExtra(Constants.POST_ID, mNoteList.get(position).getId());
                intent.putExtra("position", position);
                intent.putExtra(Constants.IMAGE_URL_EXTRA, mNoteList.get(position).getImg_url_android());
                intent.putExtra(Constants.VIEW_INFO_EXTRA, createViewInfoBundle(view));
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, getString(R.string.post_imagview));
                mContext.startActivity(intent);
                mActivity.overridePendingTransition(0, 0);


            }

            @Override
            public void onStarRankingListClick() {
                //去网红榜
                Intent intent = new Intent(mContext, StarRankingListActivity.class);
                mContext.startActivity(intent);
            }

            @Override
            public void onScoreboardClick() {
                //去积分榜
                Intent intent = new Intent(mContext, ScoreboardActivity.class);
                mContext.startActivity(intent);

            }


            @Override
            public void onBannerItemClick(int position, View view) {

                if (dataBean == null) {
                    return;
                }
                if (dataBean.getBanner_list().get(position).getIs_click() != 1) {
                    return;
                }

                int type = dataBean.getBanner_list().get(position).getBiz_type();
                Logger.e( "名字" + dataBean.getBanner_list().get(position).getImg_url() +"类别" + dataBean.getBanner_list().get(position).getBiz_type()  + "ID" +dataBean.getBanner_list().get(position).getBiz_id());
                if (type ==2){
                    //判断是活动还是文章 1，活动2文章,6）
                    //广告条
                    //根据id打开文章
                    Intent intent = new Intent();
                    intent.setClass(mContext, ArticleDetailActivity.class);
                    intent.putExtra("type", Constants.TYPE_ARTICLE);
                    intent.putExtra("id", String.valueOf(dataBean.getBanner_list().get(position).getBiz_id()));
                    mContext.startActivity(intent);

                } else if (type == 1){
                    Intent intent = new Intent();
                    intent.setClass(mContext, ArticleDetailActivity.class);
                    intent.putExtra("type", Constants.TYPE_ACTIVITY);
                    intent.putExtra("id", String.valueOf(dataBean.getBanner_list().get(position).getBiz_id()));
                    mContext.startActivity(intent);
                } else {
                    //赛事
                    Intent intent = new Intent();
                    intent.setClass(mContext, ArticleDetailActivity.class);
                    intent.putExtra("type", Constants.TYPE_MATCH);
                    intent.putExtra("id", String.valueOf(dataBean.getBanner_list().get(position).getBiz_id()));
                    mContext.startActivity(intent);

                }



//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    ActivityOptions options = null;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "shareView");
//                    }
//                    mContext.startActivity(intent, options.toBundle());
//                } else {

//                }

            }

            @Override
            public void onBtnFollowClick(int position) {
                if (checkLogin()) return;
                //点击关注
                //发送关注请求
                //根据请求结果改变ui
                mPresenter.addFollow(position, mNoteList.get(position).getUser_id(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }

            @Override
            public void onBtnRemoveFollowClick(int contentPosition) {
                if (checkLogin()) return;
                //移除关注
                mPresenter.removeFollow(contentPosition, mNoteList.get(contentPosition).getUser_id(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }

            @Override
            public void onBtnLikeClick(int position) {
                if (checkLogin()) return;
                //喜欢
                mPresenter.addLike(position, mNoteList.get(position).getId(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }

            @Override
            public void onBtnUnLikeClick(int position) {
                if (checkLogin()) return;
                //取消喜欢
                mPresenter.removeLike(position, mNoteList.get(position).getId(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }

            @Override
            public void onBtnCommentClick(int position) {
                //去评论列表
                Intent intent = new Intent(mContext, PostCommentDetailActivity.class);
                intent.putExtra("postId", mNoteList.get(position).getId());
                intent.putExtra("position", position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }

            @Override
            public void onBtnShareClick(int position) {
                //分享
                showPopFormBottom(mRvHomeList, position);
            }

            @Override
            public void onBtnHeadAndNameClick(int position) {

                long userId = mNoteList.get(position).getUser_id();
                if (!SharedPreferenceUtil.isMe(userId)) {
                    //进入others个人中心
                    Intent intent = new Intent(mContext, OthersPersonalActivity.class);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
                } else {
                    if (checkLogin()){
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


        //用于接收评论的事件
//改变状态 commentcount+1 更新adapter
        if (subscription==null){
            subscription = RxBusUtils.getDefault().toObservableSticky(EventCommentBean.class)
                    .subscribe(new Action1<EventCommentBean>() {
                        @Override
                        public void call(EventCommentBean eventCommentBean) {
                            //用于接收评论的事件
                            //改变状态 commentcount+1 更新adapter
                            mNewHomeListAdapter.updateCommentState(eventCommentBean.getPosition(), eventCommentBean.getCount(), eventCommentBean.getId());
                        }
                    });

            addSubscrebe(subscription);
        }


        //用于接收喜欢的事件
//0是取消 1是喜欢
//改变状态
        if (likeSubscription==null){

            likeSubscription = RxBusUtils.getDefault().toObservableSticky(EventLikeBean.class)
                    .subscribe(new Action1<EventLikeBean>() {
                        @Override
                        public void call(EventLikeBean eventLikeBean) {
                            //用于接收喜欢的事件
                            //0是取消 1是喜欢
                            //改变状态
                            mNewHomeListAdapter.updateLikeState(eventLikeBean.getPosition(), eventLikeBean.getType(), eventLikeBean.getId());
                        }
                    });

            addSubscrebe(likeSubscription);

        }


        //接收follow消息
        if (mFollowSubscribe == null){
            mFollowSubscribe = RxBusUtils.getDefault().toObservableSticky(EventFollowBean.class)
                    .subscribe(new Action1<EventFollowBean>() {
                        @Override
                        public void call(EventFollowBean eventFollowBean) {
                            //接收follow消息
                            int type = eventFollowBean.getType();
                            if (type == 1) {
                                mNewHomeListAdapter.updateFollowState(eventFollowBean.getUserid(), 1);
                            } else {
                                mNewHomeListAdapter.updateFollowState(eventFollowBean.getUserid(), 0);
                            }
                        }
                    });
            addSubscrebe(mFollowSubscribe);
        }

    }


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


    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
            return true;
        }
        return false;
    }


    private int mPage = 2;
    private long tagsId = 0;

    @Override
    public void onLoadMore() {

        if (mPage <= mPageCount) {
            mPresenter.getMoreSquarePostList(mPage, tagsId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            return;
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }

    }

    @Override
    public void onRefresh() {
        mPresenter.getHomePageList(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
    }

    @Override
    public void showError(String msg) {

        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getHomePageList(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
                }
            });
            return;
        }
    }

    @Override
    public void showContent(NewHomeBean.DataBean dataBean) {
        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (dataBean == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        } else {
            if (loadingLayout != null) {
                loadingLayout.setStatus(LoadingLayout.Success);//加载成功
            }
        }

        this.dataBean = dataBean;
        mPage = 2;
        mPageCount = dataBean.getTotal_page();
        mNoteList = dataBean.getNote_list();
        mNewHomeListAdapter.addData(dataBean);

    }

    private int mPageCount;

    @Override
    public void loadMoreContent(SquareMorePostBean.DataBean dataBean) {

        if (dataBean == null) {
//            mNoNet.setVisibility(View.VISIBLE);
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        } else {
            if (loadingLayout != null) {
                loadingLayout.setStatus(LoadingLayout.Success);//加载成功
            }
        }


        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (dataBean.getList() == null) {
            return;
        }

        mPage = dataBean.getCurrent_page();
        mPage++;
        mPageCount = dataBean.getTotal_page();
        List<SquareMorePostBean.DataBean.ListBean> list = dataBean.getList();
        for (int i = 0; i < list.size(); i++) {
            NewHomeBean.DataBean.NoteListBean orgNoteBean = new NewHomeBean.DataBean.NoteListBean();
            SquareMorePostBean.DataBean.ListBean bean = list.get(i);
            orgNoteBean.setId(bean.getId());
            orgNoteBean.setIcon(bean.getIcon());
            orgNoteBean.setComment_count(bean.getComment_count());
            orgNoteBean.setContent(bean.getContent());
            orgNoteBean.setFriend(bean.getFriend());
            orgNoteBean.setImg_url_android(bean.getImg_url_android());
            orgNoteBean.setLike(bean.getLike());
            orgNoteBean.setLike_count(bean.getLike_count());
            orgNoteBean.setTime(bean.getTime());
            orgNoteBean.setUser_id(bean.getUser_id());
            orgNoteBean.setUser_name(bean.getUser_name());
            orgNoteBean.setType(bean.getType());
            orgNoteBean.setImg_height(bean.getImg_height());
            orgNoteBean.setImg_width(bean.getImg_width());
            mNoteList.add(orgNoteBean);
        }

        mNewHomeListAdapter.addData(mNoteList);

    }


    @Override
    public void showAddLikeResult(int position) {
        //喜欢成功后通知adapter改变item状态
//        mNewHomeListAdapter.updateLikeState(position, 1);
        EventLikeBean eventLikeBean = new EventLikeBean(1, position);
        eventLikeBean.setId(mNoteList.get(position).getId());
        RxBusUtils.getDefault().postSticky(eventLikeBean);


    }

    @Override
    public void showRemoveLikeResult(int position) {
        //喜欢成功后通知adapter改变item状态
//        mNewHomeListAdapter.updateLikeState(position, 0);
        EventLikeBean eventLikeBean = new EventLikeBean(0, position);

        eventLikeBean.setId(mNoteList.get(position).getId());
        RxBusUtils.getDefault().postSticky(eventLikeBean);


    }

    @Override
    public void showFollowResult(int position) {
        //通知adapter改变状态
//        mNewHomeListAdapter.updateFollowState(mNoteList.get(position).getUser_id(), 1);

        RxBusUtils.getDefault().postSticky(new EventFollowBean(1, mNoteList.get(position).getUser_id()));

    }

    @Override
    public void showRemoveFollowResult(int position) {
//        mNewHomeListAdapter.updateFollowState(mNoteList.get(position).getUser_id(), 0);

        RxBusUtils.getDefault().postSticky(new EventFollowBean(0, mNoteList.get(position).getUser_id()));


    }

    @Override
    public void showDeleteResult(int position) {

        //删除列表的条目

        mNewHomeListAdapter.deleteItem(position);
    }

    public void showPopFormBottom(View view, int position) {
        SharePopwindow sharePopwindow = new SharePopwindow(mContext, position);
        //showAtLocation(View parent, int gravity, int x, int y)
        if (mNoteList != null && mNoteList.size() != 0) {
            sharePopwindow.isMe(mNoteList.get(position).getUser_id());
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
                                    .load(mNoteList.get(position).getImg_url_android())
                                    .asBitmap() //必须
                                    .centerCrop()
                                    .into(90, 90)
                                    .get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        WeixinShareUtil weixinShareUtil = WeixinShareUtil.getInstance(mContext.getApplicationContext());
                        weixinShareUtil.shareByWeixin(weixinShareUtil.new ShareContentWebpage("分享" + mNoteList.get(position).getUser_name() + "的帖子", mNoteList.get(position).getContent(),
                                "http://wap.mperfit.com/share/note/" + mNoteList.get(position).getId(), thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_TALK);

                    }
                }).start();


            }

            @Override
            public void onQuanItemClick(final int position) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap thumBitmap = null;
                        WeixinShareUtil weixinShareUtiltoQuan = WeixinShareUtil.getInstance(mContext.getApplicationContext());

                        try {
                            thumBitmap = Glide.with(mContext)
                                    .load(mNoteList.get(position).getImg_url_android())
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
                        weixinShareUtiltoQuan.shareByWeixin(weixinShareUtiltoQuan.new ShareContentWebpage("分享" + mNoteList.get(position).getUser_name() + "的帖子", mNoteList.get(position).getContent(),
                                "http://wap.mperfit.com/note/activity/" + mNoteList.get(position).getId(), thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_FRENDS);
                    }
                }).start();


            }

            @Override
            public void onWeiboItemClick(final int position) {
                Glide.with(mContext)
                        .load(mNoteList.get(position).getImg_url_android())
                        .asBitmap()
                        .placeholder(R.drawable.icon)
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {

                                Drawable drawable = getResources().getDrawable(R.drawable.icon);
                                Bitmap defaultBitmap = SystemUtil.drawableToBitmap(drawable);
                                WeiboShareUtils.getDefault(getActivity()).sendMultiMessage("分享" + mNoteList.get(position).getUser_name() + "的帖子", mNoteList.get(position).getContent(),
                                        "http://wap.mperfit.com/share/note/" + mNoteList.get(position).getId(), defaultBitmap);

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

                                    WeiboShareUtils.getDefault(getActivity()).sendMultiMessage("分享" + mNoteList.get(position).getUser_name() + "的帖子", mNoteList.get(position).getContent(),
                                            "http://wap.mperfit.com/note/activity/" + mNoteList.get(position).getId(), bitmap);
                                }

                            }

                        });

            }

            @Override
            public void onDeleteItemClick(int position) {
                //删除选择的条目
                if (checkLogin()) return;
                if (mNoteList == null) {
                    return;
                }
                mPresenter.deletePost(position, mNoteList.get(position).getId(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);

            }
        });
    }

}

