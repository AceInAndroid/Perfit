package com.mperfit.perfit.ui.article.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.ActivityDetailBean;
import com.mperfit.perfit.model.bean.ArticleDetailBean;
import com.mperfit.perfit.model.bean.EnrollSuccessBean;
import com.mperfit.perfit.model.bean.MatchDetaiBean;
import com.mperfit.perfit.presenter.contract.ArticleDetailContract;
import com.mperfit.perfit.presenter.presenter.ArticleDetailPresenterImpl;
import com.mperfit.perfit.ui.activities.activity.ActivityEnroll;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.HtmlUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.SnackbarUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.utils.WeiboShareUtils;
import com.mperfit.perfit.utils.WeixinShareUtil;
import com.mperfit.perfit.widget.SharePopwindow;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.victor.loading.rotate.RotateLoading;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


/**
 * 文章详情页
 * Created by zhangbing on 16/10/14.
 */

public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenterImpl> implements ArticleDetailContract.View {
    @BindView(R.id.iv_detail_bar_image)
    ImageView detailBarImage;
    @BindView(R.id.tv_detail_bar_copyright)
    TextView detailBarCopyright;
    @BindView(R.id.tb_detail_toolbar)
    Toolbar viewToolbar;
    @BindView(R.id.ctl_detail_toobar)
    CollapsingToolbarLayout clpToolbar;
    @BindView(R.id.rl_detail_view_loading)
    RotateLoading viewLoading;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.tv_signup_text)
    TextView tvSignupText;
    @BindView(R.id.tv_signup_total)
    TextView tvSignupTotal;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price_oldprice)
    TextView tvOldPrice;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.btn_joyin)
    Button mJoyinBtn;
    @BindView(R.id.ll_detail_enrollinfo)
    LinearLayout mActivityInfo;
    @BindView(R.id.ib_collect)
    ImageButton ibCollect;
    @BindView(R.id.app_detail_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.fl_webview_content)
    FrameLayout mContent;

    @BindView(R.id.iv_article_detail_price)
    ImageView ivArticleDetail;
    @BindView(R.id.tv_enroll_theme)
    TextView tvEnrollTheme;

    private String imgUrl;
    private boolean isImageShow = false;
    private String id;
    private String mActivityid;
    private String mMatchId;
    private boolean isActivity = false;
    private boolean isMatch = false;
    private AMap aMap;
    private WebView wvDetailContent;
    private ActivityDetailBean.DataBean.ActivityBean activityBean;
    private ArticleDetailBean.DataBean.ArticleBean articleBean;
    private CompositeSubscription mCompositeSubscription;
    private MatchDetaiBean.DataBean mMatchDetaiBeanData;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_article_detail;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化高德
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();
        aMap.setMyLocationEnabled(false);
        aMap.getUiSettings().setAllGesturesEnabled(false);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(false);

    }

    @Override
    protected void initEventAndData() {
        setToolBar(viewToolbar, "");
        //在代码实例化 防止activity持有
        wvDetailContent = new WebView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mContent.addView(wvDetailContent, params);
        Intent intent = getIntent();

        Logger.e("type:" + intent.getIntExtra("type", Constants.TYPE_ARTICLE));

        if (intent.getIntExtra("type", Constants.TYPE_ARTICLE) == Constants.TYPE_ACTIVITY) {
            //如果是活动活动
            isActivity = true;
            mActivityid = intent.getStringExtra("id");
            mPresenter.getActivityDetail(mActivityid);
        } else if (intent.getIntExtra("type", Constants.TYPE_ARTICLE) == Constants.TYPE_MATCH){
            //如果是赛事
            isMatch = true;
            mMatchId = intent.getStringExtra("id");
            mPresenter.getMatchDetail(mMatchId);

        }else {
            isActivity = false;
            id = intent.getExtras().getString("id");
            mActivityInfo.setVisibility(View.GONE);
            mJoyinBtn.setVisibility(View.GONE);
            mapView.setVisibility(View.GONE);
            mPresenter.getArticleDetail(id);
        }
        viewLoading.start();
        WebSettings webSettings = wvDetailContent.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        if (SystemUtil.isNetworkConnected()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }

        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);

        //屏蔽掉长按
        wvDetailContent.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
//                imgReset();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        //暂时不用
//        nsvScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY - oldScrollY > 0 ){//下移隐藏
//
//                } else if (scrollY -oldScrollY < 0){//上移出现
//
//                }
//            }
//        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            (getWindow().getSharedElementEnterTransition()).addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    /**
                     * 测试发现部分手机(如红米note2)上加载图片会变形,没有达到centerCrop效果
                     * 查阅资料发现Glide配合SharedElementTransition是有坑的,需要在Transition动画结束后再加载图片
                     * https://github.com/TWiStErRob/glide-support/blob/master/src/glide3/java/com/bumptech/glide/supportapp/github/_847_shared_transition/DetailFragment.java
                     */
                    if (imgUrl != null) {
                        isImageShow = true;
                        ImageLoader.loadBannaerAds(mContext, imgUrl, detailBarImage);
                    }
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }
            });

            initEvent();
        }


    }

    @OnClick(R.id.ib_back)
    void back() {
        finish();
        overridePendingTransition(0, R.anim.slide_right_out);
    }



    public void addSubscription(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }


    @OnClick(R.id.ib_collect)
    void likeSometing() {

        if (ibCollect.isSelected()) {
            if (isActivity) {
                mPresenter.deleteLike(mActivityid, Constants.LIKE_TYPE_ACTIVITY);
            } else {
                mPresenter.deleteLike(String.valueOf(id), Constants.LIKE_TYPE_ARTICLE);
            }
        } else {
            if (isActivity) {
//                RealmLikeBean realmLikeBean = new RealmLikeBean();
//                realmLikeBean.setId(mActivityid);
                mPresenter.insetLike(String.valueOf(mActivityid), Constants.LIKE_TYPE_ACTIVITY);
            } else {
//                RealmLikeBean realmLikeBean = new RealmLikeBean();
//                realmLikeBean.setId(String.valueOf(id));
                mPresenter.insetLike(id, Constants.LIKE_TYPE_ARTICLE);
            }
        }
    }


    @OnClick(R.id.ib_share)
    void share() {
        showPopFormBottom(viewToolbar);
    }

    public void showPopFormBottom(View view) {
        SharePopwindow sharePopwindow = new SharePopwindow(this, onClickListener);
        //showAtLocation(View parent, int gravity, int x, int y)
        sharePopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        sharePopwindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    private RequestListener<String, Bitmap> requestListener = new RequestListener<String, Bitmap>() {
        @Override
        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
            // todo log exception
            if (isActivity) {

                Drawable drawable = getResources().getDrawable(R.drawable.icon);
                Bitmap defaultBitmap = SystemUtil.drawableToBitmap(drawable);
                WeiboShareUtils.getDefault(mContext).sendMultiMessage(activityBean.getName(), activityBean.getContent(),
                        "http://wap.mperfit.com/share/activity/" + activityBean.getActivity_id(), defaultBitmap);
            } else if (isMatch) {
                Drawable drawable = getResources().getDrawable(R.drawable.icon);
                Bitmap defaultBitmap = SystemUtil.drawableToBitmap(drawable);
                WeiboShareUtils.getDefault(mContext).sendMultiMessage(mMatchDetaiBeanData.getMatch().getName(), mMatchDetaiBeanData.getMatch().getContent(),
                        "http://wap.mperfit.com/share/match/" + mMatchId, defaultBitmap);
            } else {

                Drawable drawable = getResources().getDrawable(R.drawable.icon);
                Bitmap defaultBitmap = SystemUtil.drawableToBitmap(drawable);
                WeiboShareUtils.getDefault(mContext).sendMultiMessage(articleBean.getName(), articleBean.getContent(),
                        "http://wap.mperfit.com/share/article/" + articleBean.getArticle_id(), defaultBitmap);
            }

            // important to return false so the error placeholder can be placed
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }


    };


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (isActivity) {
                switch (v.getId()) {
                    case R.id.ib_weixin:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap thumBitmap = null;

                                try {
                                    thumBitmap = Glide.with(mContext)
                                            .load(activityBean.getImg_url())
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
                                weixinShareUtil.shareByWeixin(weixinShareUtil.new ShareContentWebpage(activityBean.getName(), activityBean.getContent(),
                                        "http://wap.mperfit.com/share/activity/" + activityBean.getActivity_id(), thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_TALK);

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
                                            .load(activityBean.getImg_url())
                                            .asBitmap()
                                            .placeholder(R.drawable.place_holderimage)
                                            .error(R.drawable.place_holderimage)
                                            .centerCrop()
                                            .into(90, 90)
                                            .get();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }

                                weixinShareUtiltoQuan.shareByWeixin(weixinShareUtiltoQuan.new ShareContentWebpage(activityBean.getName(), activityBean.getContent(),
                                        "http://wap.mperfit.com/share/activity/" + activityBean.getActivity_id(), thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_FRENDS);


                            }
                        }).start();
                        break;
                    case R.id.ib_weibo:

                        Glide.with(mContext)
                                .load(activityBean.getImg_url())
                                .asBitmap()
                                .placeholder(R.drawable.icon)
                                .listener(requestListener)
                                .error(R.drawable.icon)
                                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

                                        if (bitmap != null) {

                                            WeiboShareUtils.getDefault(mContext).sendMultiMessage(activityBean.getName(), activityBean.getContent(),
                                                    "http://wap.mperfit.com/share/activity/" + activityBean.getActivity_id(), bitmap);
                                        }

                                    }

                                });


                        break;
                }
            } else if (isMatch){


                switch (v.getId()) {
                    case R.id.ib_weixin:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap thumBitmap = null;

                                try {
                                    thumBitmap = Glide.with(mContext)
                                            .load(mMatchDetaiBeanData.getMatch().getImg_url())
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
                                weixinShareUtil.shareByWeixin(weixinShareUtil.new ShareContentWebpage(mMatchDetaiBeanData.getMatch().getName(), mMatchDetaiBeanData.getMatch().getContent(),
                                        "http://wap.mperfit.com/share/match/" + mMatchId, thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_TALK);

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
                                            .load(mMatchDetaiBeanData.getMatch().getImg_url())
                                            .asBitmap()
                                            .placeholder(R.drawable.place_holderimage)
                                            .error(R.drawable.place_holderimage)
                                            .centerCrop()
                                            .into(90, 90)
                                            .get();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }

                                weixinShareUtiltoQuan.shareByWeixin(weixinShareUtiltoQuan.new ShareContentWebpage(mMatchDetaiBeanData.getMatch().getName(), mMatchDetaiBeanData.getMatch().getContent(),
                                        "http://wap.mperfit.com/share/match/" + mMatchId, thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_FRENDS);


                            }
                        }).start();
                        break;
                    case R.id.ib_weibo:

                        Glide.with(mContext)
                                .load(mMatchDetaiBeanData.getMatch().getImg_url())
                                .asBitmap()
                                .placeholder(R.drawable.icon)
                                .listener(requestListener)
                                .error(R.drawable.icon)
                                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

                                        if (bitmap != null) {

                                            WeiboShareUtils.getDefault(mContext).sendMultiMessage(mMatchDetaiBeanData.getMatch().getName(), mMatchDetaiBeanData.getMatch().getContent(),
                                                    "http://wap.mperfit.com/share/match/" + mMatchId, bitmap);
                                        }

                                    }

                                });


                        break;
                }





            } else {
                //如果是文章
                switch (v.getId()) {
                    case R.id.ib_weixin:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                Bitmap thumBitmap = null;

                                try {
                                    thumBitmap = Glide.with(mContext)
                                            .load(articleBean.getImg_url())
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
                                weixinShareUtil.shareByWeixin(weixinShareUtil.new ShareContentWebpage(articleBean.getName(), articleBean.getContent(),
                                        "http://wap.mperfit.com/share/article/" + articleBean.getArticle_id(), thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_TALK);


                            }
                        }).start();
                        break;
                    case R.id.weixin_quan:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap thumBitmap = null;
                                try {
                                    thumBitmap = Glide.with(mContext)
                                            .load(articleBean.getImg_url())
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
                                weixinShareUtil.shareByWeixin(weixinShareUtil.new ShareContentWebpage(articleBean.getName(), articleBean.getContent(),
                                        "http://wap.mperfit.com/share/article/" + articleBean.getArticle_id(), thumBitmap), WeixinShareUtil.WEIXIN_SHARE_TYPE_FRENDS);

                            }
                        }).start();
                        break;
                    case R.id.ib_weibo:
                        Glide.with(mContext)
                                .load(articleBean.getImg_url())
                                .asBitmap()
                                .placeholder(R.drawable.icon)
                                .error(R.drawable.icon)
                                .listener(requestListener)
                                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

                                        if (bitmap != null) {
                                            WeiboShareUtils.getDefault(mContext).sendMultiMessage(articleBean.getName(), articleBean.getContent(),
                                                    "http://wap.mperfit.com/share/activity/" + articleBean.getArticle_id(), bitmap);
                                        }

                                    }

                                });
                        break;
                }

            }

        }
    };


    private void initEvent() {

        //当滑动到顶部隐藏
       /* appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scale.setDuration(500);
                    if (ibCollect != null)
                        ibCollect.setAnimation(scale);
                    ibCollect.setVisibility(View.GONE);
                } else {
                    if (ibCollect != null)
                        ibCollect.setVisibility(View.VISIBLE);

                }
            }
        });*/

    }


    //    //展示文章详情
//    @Override
//    public void showContent(ZhihuDetailBean zhihuDetailBean) {
//        viewLoading.stop();
//        imgUrl = zhihuDetailBean.getImage();
//        if (!isImageShow) {
//            ImageLoader.load(mContext, zhihuDetailBean.getImage(), detailBarImage);
//        }
//        clpToolbar.setTitle(zhihuDetailBean.getTitle());
//        detailBarCopyright.setText(zhihuDetailBean.getImage_source());
//        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(), zhihuDetailBean.getCss(), zhihuDetailBean.getJs());
//        wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
//
//    }
    //展示活动详情
    @Override
    public void showContent(ActivityDetailBean activityDetailBean) {
        activityBean = activityDetailBean.getData().getActivity();
        if (activityDetailBean.getData() != null) {
            viewLoading.stop();
            imgUrl = activityDetailBean.getData().getActivity().getImg_url();
            if (!isImageShow) {
                ImageLoader.load(mContext, imgUrl, detailBarImage);
            }
            clpToolbar.setTitle(activityDetailBean.getData().getActivity().getName());
            detailBarCopyright.setText("");
            tvEnrollTheme.setText(activityDetailBean.getData().getActivity().getName());
            wvDetailContent.loadData(getHtmlData(activityDetailBean.getData().getActivity().getImg_text()), HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
            initJoyedInfo(activityDetailBean);
            addMarker(new LatLng(activityDetailBean.getData().getActivity().getLatitude(), activityDetailBean.getData().getActivity().getLongitude()));
        }
    }

    @Override
    public void showMatchContent(MatchDetaiBean matchDetaiBean) {
        if (matchDetaiBean.getData() != null) {
            viewLoading.stop();
            mMatchDetaiBeanData = matchDetaiBean.getData();
            imgUrl = matchDetaiBean.getData().getMatch().getImg_url();
            if (!isImageShow) {
                ImageLoader.load(mContext, imgUrl, detailBarImage);
            }
            mMatchId = String.valueOf(matchDetaiBean.getData().getMatch().getMatch_id());
            clpToolbar.setTitle(matchDetaiBean.getData().getMatch().getName());
            detailBarCopyright.setText("");
            tvEnrollTheme.setText(matchDetaiBean.getData().getMatch().getName());
            wvDetailContent.loadData(getHtmlData(matchDetaiBean.getData().getMatch().getImg_text()), HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
            //地图添加定位
            addMarker(new LatLng(matchDetaiBean.getData().getMatch().getLatitude(), matchDetaiBean.getData().getMatch().getLongitude()));
            Logger.e("纬度:" + matchDetaiBean.getData().getMatch().getLatitude(), "经度" + matchDetaiBean.getData().getMatch().getLongitude());
            setMatchData(matchDetaiBean);
        } else {
            SnackbarUtil.showShort(getWindow().getDecorView(),"出了点小错误~重新打开试试~");
        }
    }

    private boolean setMatchData(MatchDetaiBean matchDetaiBean) {
        final MatchDetaiBean.DataBean.MatchBean bean = matchDetaiBean.getData().getMatch();
        final int nowEnrollNum = bean.getNow_enroll_num();
        final int enrollNum = bean.getEnroll_num();
        tvSignupText.setText(String.valueOf(nowEnrollNum));
        tvSignupTotal.setText("/" + String.valueOf(enrollNum));

        double marketPrice = bean.getMarket_price();
        final double price = bean.getPrice();
        if (price == 0) {
            tvPrice.setText(R.string.freeof);
            tvOldPrice.setVisibility(View.GONE);
            ivArticleDetail.setVisibility(View.GONE);

        } else {
            tvPrice.setText(String.valueOf(price) + getString(R.string.yuan_one));
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvOldPrice.setText(getString(R.string.rmb) + String.valueOf(marketPrice));
        }

        tvTime.setText(bean.getTime());
        tvLocation.setText(bean.getAddress());

        if (bean.getEnroll_status() == 1) {
            //可以报名
            mJoyinBtn.setText(R.string.joyinus);

        } else if (bean.getEnroll_status() == 2) {
            //已报名
            mJoyinBtn.setText(R.string.already_joyed);
            mJoyinBtn.setBackgroundColor(getResources().getColor(R.color.text_color_gray));
            mJoyinBtn.setEnabled(false);
            return true;
        } else {
            //报名已结束
            mJoyinBtn.setEnabled(false);
            mJoyinBtn.setBackgroundColor(getResources().getColor(R.color.text_color_gray));
            mJoyinBtn.setText(R.string.joyin_end);
            return true;
        }

        mJoyinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CheckLoginUtil.CheckLogin(mContext)) {
                //直接报名
                    mPresenter.putMatchEnrollFormInfo(String.valueOf(bean.getMatch_id()));
                } else {
                    CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                }
            }
        });
        return false;
    }


    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }


    @Override
    public void showArticleContent(ArticleDetailBean articleDetailBean) {
        articleBean = articleDetailBean.getData().getArticle();
        if (articleDetailBean.getData() != null) {
            viewLoading.stop();
            imgUrl = articleDetailBean.getData().getArticle().getImg_url();
            if (!isImageShow) {
                ImageLoader.load(mContext, imgUrl, detailBarImage);
            }
            clpToolbar.setTitle(articleDetailBean.getData().getArticle().getName());
            detailBarCopyright.setText("");
            wvDetailContent.loadData(getHtmlData(articleDetailBean.getData().getArticle().getImg_text()), HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
        }
    }

    /**
     * 显示报名结果
     */
    @Override
    public void showActivityEnrollResult(int type, String msg) {
        switch (type) {
            case Constants.LIKE_RESULT_SINGFILED:
                ToastUtils.showShrotMsg(mContext, msg);
                CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                break;

            case Constants.LIKE_TYPE_FILED:
                ToastUtils.showShrotMsg(mContext, msg);
//                CheckLoginUtil.reLogin(mContext);
                break;
            case Constants.LIKE_RESULT_SUCCESS:
                ToastUtils.showShrotMsg(mContext, msg);
                //改变按钮状态
                //已报名
                mJoyinBtn.setText(R.string.already_joyed);
                mJoyinBtn.setBackgroundColor(getResources().getColor(R.color.text_color_gray));
                mJoyinBtn.setEnabled(false);

                break;
            case Constants.LIKE_TYPE_DISABLE:
                //验证码错误
                ToastUtils.showShrotMsg(mContext, msg);
                break;
        }

    }

    @Override
    public void showMatchEnrollResult(int type, String msg) {
        switch (type) {
            case Constants.LIKE_RESULT_SINGFILED:
                ToastUtils.showShrotMsg(mContext, msg);
                CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                break;

            case Constants.LIKE_TYPE_FILED:
                ToastUtils.showShrotMsg(mContext, msg);
//                CheckLoginUtil.reLogin(mContext);
                break;

            case Constants.LIKE_RESULT_SUCCESS:
                ToastUtils.showShrotMsg(mContext, msg);
                //改变按钮状态

                //已报名
                mJoyinBtn.setText(R.string.already_joyed);
                mJoyinBtn.setBackgroundColor(getResources().getColor(R.color.text_color_gray));
                mJoyinBtn.setEnabled(false);

                break;

            case Constants.LIKE_TYPE_DISABLE:
                //验证码错误
                ToastUtils.showShrotMsg(mContext, msg);
                break;
        }
    }

    @Override
    public void setLikeState(Boolean isLiked) {
        ibCollect.setSelected(isLiked);
    }

    @Override
    public void showLikeResult(int result, String msg) {
        if (result == Constants.LIKE_RESULT_SINGFILED) {
            ToastUtils.showLongMsg(mContext, msg);
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
        } else {
            ToastUtils.showShrotMsg(mContext, msg);
        }
    }

    private void initJoyedInfo(ActivityDetailBean activityDetailBean) {
        final ActivityDetailBean.DataBean.ActivityBean bean = activityDetailBean.getData().getActivity();
        final int nowEnrollNum = bean.getNow_enroll_num();
        final int enrollNum = bean.getEnroll_num();
        tvSignupText.setText(String.valueOf(nowEnrollNum));
        tvSignupTotal.setText("/" + String.valueOf(enrollNum));

        double marketPrice = bean.getMarket_price();
        final double price = bean.getPrice();
        if (price == 0) {
            tvPrice.setText(R.string.freeof);
            tvOldPrice.setVisibility(View.GONE);
            ivArticleDetail.setVisibility(View.GONE);

        } else {
            tvPrice.setText(String.valueOf(price) + getString(R.string.yuan_one));
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvOldPrice.setText(getString(R.string.rmb) + String.valueOf(marketPrice));
        }

        tvTime.setText(bean.getTime());
        tvLocation.setText(bean.getAddress());


        if (bean.getEnroll_status() == 1) {
            //可以报名
            mJoyinBtn.setText(R.string.joyinus);

        } else if (bean.getEnroll_status() == 2) {
            //已报名
            mJoyinBtn.setText(R.string.already_joyed);
            mJoyinBtn.setBackgroundColor(getResources().getColor(R.color.text_color_gray));
            mJoyinBtn.setEnabled(false);
            return;
        } else {
            //报名已结束
            mJoyinBtn.setEnabled(false);
            mJoyinBtn.setBackgroundColor(getResources().getColor(R.color.text_color_gray));
            mJoyinBtn.setText(R.string.joyin_end);
            return;
        }
        //报名按钮
        mJoyinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CheckLoginUtil.CheckLogin(mContext)) {
                    mPresenter.putActivityEnrollFormInfo(String.valueOf(bean.getActivity_id()));
                } else {
                    CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                }
            }
        });
    }

    /**
     * 设置webview图片自适应屏幕
     */
    private void imgReset() {
        wvDetailContent.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%';   " +
                "}" +
                "})()");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && wvDetailContent.canGoBack())) {
            wvDetailContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addMarker(LatLng latlng) {
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, 18, 30, 30)));
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(latlng)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
    }

    @Override
    public void showError(String msg) {
        if (viewLoading!=null)
        viewLoading.stop();

        Logger.e(msg);
        SnackbarUtil.show(getWindow().getDecorView(), msg);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            }
        }
    }

    @Override
    protected void onDestroy() {
//        destroyWebView();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
        System.exit(0);
        super.onDestroy();
    }

    private void destroyWebView() {
        if (mapView != null) {
            mapView.removeAllViews();
            mapView = null;

        }
        if (aMap != null) {
            aMap.clear();
            aMap = null;

        }
        if (wvDetailContent != null) {
            wvDetailContent.pauseTimers();
            wvDetailContent.removeAllViews();
            wvDetailContent.destroy();
            wvDetailContent = null;
        }
        if (mContent != null) {
            mContent.removeAllViews();
        }
    }
}
