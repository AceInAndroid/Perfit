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
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.EventCommentBean;
import com.mperfit.perfit.model.bean.EventFollowBean;
import com.mperfit.perfit.model.bean.EventLikeBean;
import com.mperfit.perfit.model.bean.SquareListBean;
import com.mperfit.perfit.model.bean.SquareMorePostBean;
import com.mperfit.perfit.presenter.contract.SquareContract;
import com.mperfit.perfit.presenter.presenter.SquarePresenterImpl;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.comment.PostCommentDetailActivity;
import com.mperfit.perfit.ui.community.activity.HomePostPreviewActivity;
import com.mperfit.perfit.ui.community.adapter.SquareFragmentAdapter;
import com.mperfit.perfit.ui.otherspersonal.activity.OthersPersonalActivity;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
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
 * Created by zhangbing on 2017/2/10.
 */

public class SquareFragment extends BaseFragment<SquarePresenterImpl> implements SquareContract.View, OnRefreshListener, OnLoadMoreListener {
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

    private SquareListBean.DataBean dataBean;
    private SquareFragmentAdapter mSquareAdapter;
    private List<SquareListBean.DataBean.NoteListBean> mNoteList;
    private SharePopwindow sharePopwindow;
    private List<SquareListBean.DataBean.TopicListBean> mTagList;
    private Subscription mCommentSubscription;
    private Subscription likeSubscription;
    private Subscription mFollowSubscribe;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initEventAndData() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        mRvHomeList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvHomeList.setHasFixedSize(true);
        mRvHomeList.addItemDecoration(new SquareListDivider(mContext, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.divider_gray)));
        mSquareAdapter = new SquareFragmentAdapter(getActivity(), dataBean);
        mRvHomeList.setAdapter(mSquareAdapter);
        ((SimpleItemAnimator) mRvHomeList.getItemAnimator()).setSupportsChangeAnimations(false);
        mPresenter.getSquareList(1, 0, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
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
                mPresenter.getSquareList(1, 0, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }
        });
        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getSquareList(1, 0, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
                }
            });
            return;
        }

    }


    private void initEvent() {
        mSquareAdapter.setOnClickListener(new SquareFragmentAdapter.OnClickListener() {
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
            public void onTagsItemClick(int position, long tagId) {
                //刷新tags对于的菜单
                if (mTagList != null) {
                    mPresenter.getSquareTagsList(1, mTagList.get(position).getId(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
                    tagsId = mTagList.get(position).getId();
                }
            }

            @Override
            public void onBannerItemClick(int position, View view) {

                if (dataBean == null) {
                    return;
                }
                if (dataBean.getBanner_list().get(position).getIs_click() != 1) {
                    return;
                }
                //广告条
                //根据id打开文章
                Intent intent = new Intent();
                intent.setClass(mContext, ArticleDetailActivity.class);
                intent.putExtra("id", String.valueOf(dataBean.getBanner_list().get(position).getBiz_id()));
                mContext.startActivity(intent);

                Logger.e("ID:" + dataBean.getBanner_list().get(position).getBiz_id());
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


        //用于接收评论的事件
        //改变状态 commentcount+1 更新adapter
        if (mCommentSubscription == null) {
            mCommentSubscription = RxBusUtils.getDefault().toObservableSticky(EventCommentBean.class)
                    .subscribe(new Action1<EventCommentBean>() {
                        @Override
                        public void call(EventCommentBean eventCommentBean) {
                            //用于接收评论的事件
                            //改变状态 commentcount+1 更新adapter
                            mSquareAdapter.updateCommentState(eventCommentBean.getPosition(), eventCommentBean.getCount(), eventCommentBean.getId());

                        }
                    });

            addSubscrebe(mCommentSubscription);
        }


        //用于接收喜欢的事件
        //0是取消 1是喜欢
        //改变状态

        if (likeSubscription == null) {
            likeSubscription = RxBusUtils.getDefault().toObservableSticky(EventLikeBean.class)
                    .subscribe(new Action1<EventLikeBean>() {
                        @Override
                        public void call(EventLikeBean eventLikeBean) {
                            //用于接收喜欢的事件
                            //0是取消 1是喜欢
                            //改变状态
                            mSquareAdapter.updateLikeState(eventLikeBean.getPosition(), eventLikeBean.getType(), eventLikeBean.getId());
                        }
                    });

            addSubscrebe(likeSubscription);

        }


        //接收follow消息
        if (mFollowSubscribe == null) {
            mFollowSubscribe = RxBusUtils.getDefault().toObservableSticky(EventFollowBean.class)
                    .subscribe(new Action1<EventFollowBean>() {
                        @Override
                        public void call(EventFollowBean eventFollowBean) {
                            //接收follow消息
                            int type = eventFollowBean.getType();
                            if (type == 1) {
                                mSquareAdapter.updateFollowState(eventFollowBean.getUserid(), 1);
                            } else {
                                mSquareAdapter.updateFollowState(eventFollowBean.getUserid(), 0);
                            }
                        }
                    });

            addSubscrebe(mFollowSubscribe);

        }


    }


    private Bundle createViewInfoBundle(View view) {
        if (view == null) {
            return null;
        }
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
        mPresenter.getSquareList(1, 0, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
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
                    mPresenter.getSquareList(1, 0, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);

                }
            });
            return;
        }
    }

    @Override
    public void showContent(SquareListBean.DataBean dataBean) {
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
        mTagList = dataBean.getTopic_list();
        mSquareAdapter.addData(dataBean);

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
            SquareListBean.DataBean.NoteListBean orgNoteBean = new SquareListBean.DataBean.NoteListBean();
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

        mSquareAdapter.addData(mNoteList);

    }

    @Override
    public void showTagsContent(SquareMorePostBean.DataBean dataBean) {

        if (dataBean.getList() == null) {
            ToastUtils.showShrotMsg(mContext, "标签暂无帖子哦~换其他标签试试");
            return;
        }
        //点击tags 局部刷新
        mPage = dataBean.getCurrent_page();
        mPage++;
        mPageCount = dataBean.getTotal_page();
        mNoteList.clear();
        List<SquareMorePostBean.DataBean.ListBean> list = dataBean.getList();
        for (int i = 0; i < list.size(); i++) {
            SquareListBean.DataBean.NoteListBean orgNoteBean = new SquareListBean.DataBean.NoteListBean();
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
            orgNoteBean.setImg_width(bean.getImg_width());
            orgNoteBean.setImg_height(bean.getImg_height());
            mNoteList.add(orgNoteBean);
        }
        mSquareAdapter.addData(mNoteList);
    }

    @Override
    public void showAddLikeResult(int position) {
        //喜欢成功后通知adapter改变item状态
//        mSquareAdapter.updateLikeState(position, 1);
        EventLikeBean eventLikeBean = new EventLikeBean(1, position);
        eventLikeBean.setId(mNoteList.get(position).getId());
        RxBusUtils.getDefault().postSticky(eventLikeBean);


    }

    @Override
    public void showRemoveLikeResult(int position) {
        //喜欢成功后通知adapter改变item状态
//        mSquareAdapter.updateLikeState(position, 0);
        EventLikeBean eventLikeBean = new EventLikeBean(0, position);
        eventLikeBean.setId(mNoteList.get(position).getId());
        RxBusUtils.getDefault().postSticky(eventLikeBean);


    }

    @Override
    public void showFollowResult(int position) {
        //通知adapter改变状态
//        mSquareAdapter.updateFollowState(mNoteList.get(position).getUser_id(), 1);

        RxBusUtils.getDefault().postSticky(new EventFollowBean(1, mNoteList.get(position).getUser_id()));

    }

    @Override
    public void showRemoveFollowResult(int position) {
//        mSquareAdapter.updateFollowState(mNoteList.get(position).getUser_id(), 0);

        RxBusUtils.getDefault().postSticky(new EventFollowBean(0, mNoteList.get(position).getUser_id()));


    }

    @Override
    public void showDeleteResult(int position) {

        //删除列表的条目

        mSquareAdapter.deleteItem(position);

        //关闭popwindos

        if (sharePopwindow != null) {
            sharePopwindow.dismiss();
        }

        ToastUtils.showShrotMsg(mContext, "删除成功");

    }

    public void showPopFormBottom(View view, int position) {
        sharePopwindow = new SharePopwindow(mContext, position);
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
                                "http://wap.mperfit.com/share/note/" + mNoteList.get(position).getId(), thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_FRENDS);
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
                                            "http://wap.mperfit.com/share/note/" + mNoteList.get(position).getId(), bitmap);
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
