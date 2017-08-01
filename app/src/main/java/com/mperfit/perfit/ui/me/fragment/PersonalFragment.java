package com.mperfit.perfit.ui.me.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.ArticleCollectBean;
import com.mperfit.perfit.model.bean.LikeListBean;
import com.mperfit.perfit.model.bean.ReLoadPersonalDataBean;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.model.bean.UserCenterBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.PersonalContract;
import com.mperfit.perfit.presenter.presenter.PersonalPresenterImpl;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.me.activity.MyJoinActivity;
import com.mperfit.perfit.ui.me.activity.SettingActivity;
import com.mperfit.perfit.ui.me.activity.articlecollect.ArticleCollectActivity;
import com.mperfit.perfit.ui.me.activity.articlecollect.adapter.ArticleCollectAdapter;
import com.mperfit.perfit.ui.me.activity.course.MyCourseActivity;
import com.mperfit.perfit.ui.me.activity.shop.ShopCollectActivity;
import com.mperfit.perfit.ui.photogallery.HeadShowActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.widget.CustomImageView;
import com.mperfit.perfit.widget.RoundImageView;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangbing on 16/10/14.
 */

public class PersonalFragment extends BaseFragment<PersonalPresenterImpl> implements PersonalContract.View {

    @BindView(R.id.ll_course)
    LinearLayout llCourse;
    @BindView(R.id.ll_joyed)
    LinearLayout llJoyed;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_slogan)
    TextView tvSlogan;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    private String imgUrl;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initEventAndData() {
        //登陆后收到消息更新UI和收藏数据库
        Subscription subscription = RxBus.getDefault().toObservable(ReLoadPersonalDataBean.class)
                .subscribe(new Action1<ReLoadPersonalDataBean>() {
                    @Override
                    public void call(ReLoadPersonalDataBean reLoadPersonalDataBean) {
                        mPresenter.getUserCenterInfo();
//                        mPresenter.getLikeListInfo(1,1);
//                        mPresenter.getLikeListInfo(1,2);
//                        mPresenter.getLikeListInfo(1,3);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        initEvent();
        addSubscrebe(subscription);
        mPresenter.getUserCenterInfo();
    }

    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    private void initEvent() {
        llJoyed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyJoinActivity.class);
                mContext.startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);

            }
        });
        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SettingActivity.class);
                mContext.startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });

        llShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopCollectActivity.class);
                mContext.startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);

            }
        });
    }

    @Override
    public void showContent(int result, UserCenterBean userCenterBean) {
//        ToastUtils.showShrotMsg(mContext, userCenterBean.getMessage());
        if (result == Constants.LIKE_RESULT_SINGFILED) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
        } else if (result == Constants.LIKE_TYPE_FILED) {

        } else {
            UserCenterBean.DataBean.UserMapBean userMap = userCenterBean.getData().getUserMap();
            loadHead(userCenterBean);
//            tvId.setText(userMap.getName());
            tvName.setText(userMap.getName());
            tvSlogan.setText(userMap.getSignature());
            if (userMap.getSex().equals("1")) {
                ivSex.setBackgroundResource(R.drawable.boy);
            } else {
                ivSex.setBackgroundResource(R.drawable.girl);
            }
        }


    }


    private void loadHead(UserCenterBean userCenterBean) {
        ViewTarget viewTarget = new ViewTarget<CircleImageView, GlideDrawable>(ivHead) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                Drawable current = resource.getCurrent();
                this.view.setImageDrawable(current);
            }
        };
        imgUrl = userCenterBean.getData().getUserMap().getImg_url();
        ImageLoader.load(mActivity, imgUrl, viewTarget);
    }

    @Override
    public void showError(String msg) {

    }

    @OnClick(R.id.ll_course)
    public void toCourse() {
        Intent intent = new Intent(mContext, MyCourseActivity.class);
        mContext.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }


    @OnClick(R.id.ll_article)
    public void toArticleCollect() {
        Intent intent = new Intent(mContext, ArticleCollectActivity.class);
        mContext.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);

    }


    @OnClick(R.id.iv_head)
    public void toDetail() {
        Intent intent = new Intent(mContext, HeadShowActivity.class);
        intent.putExtra("head", imgUrl);
        mContext.startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions.makeSceneTransitionAnimation(getActivity(), ivHead, "head").toBundle();
        }
    }

    @Override
    public void onDestroy() {
        unSubscribe();
        super.onDestroy();

    }
}
