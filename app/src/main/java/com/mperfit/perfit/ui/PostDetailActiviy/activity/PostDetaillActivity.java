package com.mperfit.perfit.ui.postdetailactiviy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.EventLikeBean;
import com.mperfit.perfit.model.bean.PostDetailBean;
import com.mperfit.perfit.presenter.contract.PostDetailContract;
import com.mperfit.perfit.presenter.presenter.PostDetailPresenterImpl;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.comment.PostCommentDetailActivity;
import com.mperfit.perfit.ui.comment.adapter.PostCommentListAdapter;
import com.mperfit.perfit.ui.community.activity.HomePostPreviewActivity;
import com.mperfit.perfit.ui.community.adapter.SquareFragmentAdapter;
import com.mperfit.perfit.ui.otherspersonal.activity.OthersPersonalActivity;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
import com.mperfit.perfit.ui.postdetailactiviy.adapter.PostCommentAdapter;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.ClickFilter;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.utils.WeiboShareUtils;
import com.mperfit.perfit.utils.WeixinShareUtil;
import com.mperfit.perfit.widget.LoadingView;
import com.mperfit.perfit.widget.SharePopwindow;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangbing on 2017/2/15.
 */

public class PostDetaillActivity extends BaseActivity<PostDetailPresenterImpl> implements PostDetailContract.View {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.app_detail_bar)
    AppBarLayout appDetailBar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ivFeedCenter)
    ImageView ivFeedCenter;
    @BindView(R.id.btnLike)
    ImageButton btnLike;
    @BindView(R.id.tv_heart_count)
    TextView tvHeartCount;
    @BindView(R.id.btnComments)
    ImageButton btnComments;
    @BindView(R.id.tv_conmment_count)
    TextView tvConmmentCount;
    @BindView(R.id.btnMore)
    ImageButton btnMore;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.view_divider)
    View mDivider;
    @BindView(R.id.view_divider2)
    View mDivider2;
    @BindView(R.id.lv_loading)
    LoadingView mLoadingView;
    private List<PostDetailBean.DataBean.CommentBean> mCommentList;
    private PostCommentAdapter mPostCommentAdapter;
    private int mlike;
    private PostDetailBean.DataBean.NoteBean mPostBean;
    private int mLikeCount;
    private int mCommentCount;
    private int mPosition;
    private int mType;
    private long mPostId;
    private String mImgUrl;
    private String mUserName;
    private String mContent;
    private long mUserId;
    private SharePopwindow sharePopwindow;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_postdetail;
    }

    @Override
    protected void initEventAndData() {
        tvTbTitle.setText("帖子详情");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPostCommentAdapter = new PostCommentAdapter(this, mCommentList);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        rvList.setLayoutManager(layoutManager);
        //去掉滑动交给nestedscrolling处理 这样才有fling效果
        rvList.setNestedScrollingEnabled(false);
        rvList.setAdapter(mPostCommentAdapter);

        Intent intent = getIntent();
        mPostId = intent.getLongExtra(Constants.POST_ID, 0);
        mPosition = intent.getIntExtra("position", -1);
        mType = intent.getIntExtra(Constants.TYPE, -1);
        Logger.e("type" + mType);
        if (mPostId != 0) {
            mPresenter.getPostDetailInfo(mPostId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        }
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
    public void showContent(final PostDetailBean.DataBean dataBean) {

        mPostBean = dataBean.getNote();
        mCommentList = dataBean.getComment();

        mUserId = dataBean.getNote().getUser_id();
        mPostCommentAdapter.addData(mCommentList);
        //设置个人信息
        mUserName = mPostBean.getUser_name();
        tvName.setText(mUserName);
        ImageLoader.load(mContext, mPostBean.getIcon(), ivHead);
        tvTime.setText(mPostBean.getTime());
        mImgUrl = mPostBean.getImg_url();
        int imgHeight = dataBean.getNote().getImg_height();
        int imgWidth = dataBean.getNote().getImg_width();

        int vw = App.SCREEN_WIDTH;
        float scale = (float) vw / (float) imgWidth;
        int vh = Math.round(imgHeight * scale);

        Logger.e("vh:" + vh +"scale" + scale + "vw" + vw   +"App.SCREEN_HEIGHT" + App.SCREEN_HEIGHT * 0.60);

        ViewGroup.LayoutParams params = ivFeedCenter.getLayoutParams();

        //横屏图片(长大于宽的 不变)
        if (imgWidth > imgHeight){
            if (ivFeedCenter.getScaleType() != ImageView.ScaleType.FIT_XY) {
                ivFeedCenter.setScaleType(ImageView.ScaleType.FIT_XY);
            }

            params.height = vh;


        }  else {
            if (App.SCREEN_HEIGHT != 0 && vh > App.SCREEN_HEIGHT * 0.70) {
                params.height = (int) (App.SCREEN_HEIGHT * 0.70);

                if (ivFeedCenter.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
                    ivFeedCenter.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }


            } else {
                params.height = vh;
                if (ivFeedCenter.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    ivFeedCenter.setScaleType(ImageView.ScaleType.FIT_XY);
                }


            }
        }
        ivFeedCenter.setLayoutParams(params);

        ImageLoader.loadPostDetail(mContext, mImgUrl, ivFeedCenter,mLoadingView,imgWidth,imgHeight);

        mLikeCount = mPostBean.getLike_count();
        tvHeartCount.setText(mLikeCount + "");
        mCommentCount = mPostBean.getComment_count();
        if (mCommentCount == 0) {
            //隐藏评论
            llComment.setVisibility(View.GONE);
            mDivider.setVisibility(View.GONE);
            mDivider2.setVisibility(View.GONE);
        }

        tvConmmentCount.setText(mCommentCount + "");
        mContent = mPostBean.getContent();

        if (mContent.equals("")) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setText(mContent);
        }

        mlike = mPostBean.getLike();

        if (mlike == 1) {
            //则喜欢
            btnLike.setBackgroundDrawable(null);
            btnLike.setBackgroundResource(R.drawable.home_heart_selected);
        } else {
            //不喜欢
            btnLike.setBackgroundDrawable(null);
            btnLike.setBackgroundResource(R.drawable.home_list_heart);
        }


        mPostCommentAdapter.setOnItemClickListener(new PostCommentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (!SharedPreferenceUtil.isMe(dataBean.getComment().get(position).getUser_id())) {
                    //进入others个人中心
                    if (checkLogin()) return;

                    Intent intent = new Intent(mContext, OthersPersonalActivity.class);
                    intent.putExtra("user_id", dataBean.getComment().get(position).getUser_id());
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                } else {
                    //进入自己的个人中心
                    if (checkLogin()) return;
                    long selfId = SharedPreferenceUtil.getUserid();
                    Intent intent = new Intent(mContext, MySelfPersonalCenterActivity.class);
                    intent.putExtra("user_id", selfId);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }
            }
        });
    }


    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMLOGIN);
            return true;
        }
        return false;
    }


    @Override
    public void showAddLikeResult() {
        mlike = 1;
        btnLike.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.home_heart_selected));
        mLikeCount++;
        EventLikeBean eventLikeBean = new EventLikeBean(1, mPosition);
        eventLikeBean.setId(mPostId);
        //喜欢成功发送消息给主页
        RxBusUtils.getDefault().postSticky(eventLikeBean);

        if (mType == Constants.TYPE_POSTDEFORMLIKE) {
            Intent intent = new Intent();
            intent.putExtra(Constants.INTENT_ISLIKE, mlike);
            intent.putExtra(Constants.INTENT_ISLIKE_POSITION, mPosition);
            intent.putExtra(Constants.INTENT_ISLIKE_POST_TYPE, mType);
            setResult(Activity.RESULT_OK, intent);
        }

        tvHeartCount.setText(mLikeCount + "");

    }


    @Override
    public void showRemoveLikeResult() {
        mlike = 0;
        btnLike.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_btn_heart));
        if (mLikeCount > 1) {
            mLikeCount--;
        } else {
            mLikeCount = 0;

        }
        EventLikeBean eventLikeBean = new EventLikeBean(0, mPosition);
        eventLikeBean.setId(mPostId);
        RxBusUtils.getDefault().postSticky(eventLikeBean);

        if (mType == Constants.TYPE_POSTDEFORMLIKE) {
            Intent intent = new Intent();
            intent.putExtra(Constants.INTENT_ISLIKE, mlike);
            intent.putExtra(Constants.INTENT_ISLIKE_POSITION, mPosition);
            intent.putExtra(Constants.INTENT_ISLIKE_POST_TYPE, mType);
            setResult(Activity.RESULT_OK, intent);
        }


        tvHeartCount.setText(mLikeCount + "");
    }

    @Override
    public void showDeleteResult() {
        //删除成功 关闭


        //初始化intent

        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_ISLIKE_POSITION, mPosition);
        intent.putExtra(Constants.INTENT_ISLIKE_POST_TYPE, mType);
        //给个特殊参数辨别删帖
        intent.putExtra(Constants.INTENT_ISDELETE, 1);
        setResult(Activity.RESULT_OK, intent);

        ToastUtils.showShrotMsg(mContext,"删除成功");

        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }


    @OnClick(R.id.btnLike)
    void like() {
        if (mlike == 1) {
            //取消喜欢
            if (checkLogin()) return;
            if (mPostBean != null)
                mPresenter.removeLike(mPostBean.getId(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        } else {
            //喜欢
            if (checkLogin()) return;
            if (mPostBean != null)
                mPresenter.addLike(mPostBean.getId(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        }
    }

    private static final int REQUST_CODE = 0x10;

    @OnClick(R.id.btnComments)
    void toComment() {
        //去评论列表
        Intent intent = new Intent(mContext, PostCommentDetailActivity.class);
        intent.putExtra(Constants.POST_ID, mPostId);
        intent.putExtra("position", mPosition);
        startActivityForResult(intent, REQUST_CODE);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @OnClick(R.id.btnMore)
    void share() {
        showPopFormBottom(ivFeedCenter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUST_CODE) {
                //添加了评论
                //更改评论数量
                if (llComment.getVisibility() == View.GONE) {
                    llComment.setVisibility(View.VISIBLE);
                }
                if (mDivider.getVisibility() == View.GONE)
                    mDivider.setVisibility(View.VISIBLE);
                if (mDivider.getVisibility() == View.GONE)
                    mDivider2.setVisibility(View.VISIBLE);
                //更新数据评论列表
                mPresenter.getPostDetailInfo(mPostId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }
        }
    }


    public void showPopFormBottom(View view) {
        sharePopwindow = new SharePopwindow(mContext, onClickListener);
        //showAtLocation(View parent, int gravity, int x, int y)
        sharePopwindow.isMe(mUserId);
        Logger.e("muserId" + mUserId);
        sharePopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        sharePopwindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    @OnClick(R.id.iv_head)
    void toPersonal() {
        if (mUserId == 0) {
            return;
        }
        if (!SharedPreferenceUtil.isMe(mUserId)) {
            //进入others个人中心
            Intent intent = new Intent(mContext, OthersPersonalActivity.class);
            intent.putExtra("user_id", mUserId);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        } else {
            //进入自己的个人中心
            long selfId = SharedPreferenceUtil.getUserid();
            Intent intent = new Intent(mContext, MySelfPersonalCenterActivity.class);
            intent.putExtra("user_id", selfId);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }

    }


    @OnClick(R.id.tv_name)
    void toPersonalDetail() {
        if (mUserId == 0) {
            return;
        }

        if (!SharedPreferenceUtil.isMe(mUserId)) {
            //进入others个人中心
            Intent intent = new Intent(mContext, OthersPersonalActivity.class);
            intent.putExtra("user_id", mUserId);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        } else {
            //进入自己的个人中心
            long selfId = SharedPreferenceUtil.getUserid();
            Intent intent = new Intent(mContext, MySelfPersonalCenterActivity.class);
            intent.putExtra("user_id", selfId);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ib_weixin:


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap thumBitmap = null;

                            try {
                                thumBitmap = Glide.with(mContext)
                                        .load(mImgUrl)
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

                            WeixinShareUtil weixinShareUtil = WeixinShareUtil.getInstance(mContext);
                            weixinShareUtil.shareByWeixin(weixinShareUtil.new ShareContentWebpage("分享" + mUserName + "的帖子", mContent,
                                    "http://wap.mperfit.com/share/note/" + mPostId, thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_TALK);

                        }
                    }).start();

                    break;
                case R.id.weixin_quan:


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap thumBitmap = null;
                            WeixinShareUtil weixinShareUtiltoQuan = WeixinShareUtil.getInstance(mContext);

                            try {
                                thumBitmap = Glide.with(mContext)
                                        .load(mImgUrl)
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
                            weixinShareUtiltoQuan.shareByWeixin(weixinShareUtiltoQuan.new ShareContentWebpage("分享" + mUserName + "的帖子", mContent,
                                    "http://wap.mperfit.com/share/note/" + mPostId, thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_FRENDS);
                        }
                    }).start();
                    break;
                case R.id.ib_weibo:
                    Glide.with(mContext)
                            .load(mImgUrl)
                            .asBitmap()
                            .placeholder(R.drawable.icon)
                            .listener(new RequestListener<String, Bitmap>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {

                                    Drawable drawable = getResources().getDrawable(R.drawable.icon);
                                    Bitmap defaultBitmap = SystemUtil.drawableToBitmap(drawable);
                                    WeiboShareUtils.getDefault(mContext).sendMultiMessage("分享" + mUserName + "的帖子", mContent,
                                            "http://wap.mperfit.com/share/note/" + mPostId, defaultBitmap);

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

                                        WeiboShareUtils.getDefault(mContext).sendMultiMessage("分享" + mUserName + "的帖子", mContent,
                                                "http://wap.mperfit.com/share/note/" + mPostId, bitmap);
                                    }
                                }
                            });
                    break;

                case R.id.iv_delete:
                    if (ClickFilter.filter()) {
                        return;
                    }
                    mPresenter.deletePost(mPostId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
                    break;
            }
        }
    };


    @OnClick(R.id.ivFeedCenter)
    void toPreview() {
        //进入帖子详情
        Intent intent = new Intent(mContext, HomePostPreviewActivity.class);
        intent.putExtra(Constants.POST_ID, mPostId);
        intent.putExtra(Constants.IMAGE_URL_EXTRA, mPostBean.getImg_url());
        intent.putExtra(Constants.VIEW_INFO_EXTRA, createViewInfoBundle(ivFeedCenter));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mContext, ivFeedCenter, getString(R.string.post_imagview));
        mContext.startActivity(intent, options.toBundle());
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


    @Override
    public void showError(String msg) {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
