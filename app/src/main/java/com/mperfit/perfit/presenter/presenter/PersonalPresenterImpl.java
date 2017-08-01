package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.ArticleCollectBean;
import com.mperfit.perfit.model.bean.LikeListBean;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.model.bean.UserCenterBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.PersonalContract;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2016/11/02
 */

public class PersonalPresenterImpl extends RxPresenter<PersonalContract.View> implements PersonalContract.Presenter {

    private RetrofitHelper retrofitHelper;
//    private RealmHelper realmHelper;

    @Inject
    public PersonalPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void getUserCenterInfo() {
        Subscription subscribe = retrofitHelper.fetchUserCenterInfo()
                .compose(RxUtil.<UserCenterBean>rxSchedulerHelper())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserCenterBean>() {
                    @Override
                    public void call(UserCenterBean userCenterBean) {
                        if (userCenterBean.getCode() == 100) {
                            mView.showContent(Constants.LIKE_RESULT_SUCCESS, userCenterBean);
                        }
                        if (userCenterBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showContent(Constants.LIKE_RESULT_SINGFILED, userCenterBean);
                        } else if (userCenterBean.getCode() == 10006) {
                            //重新登录
                            mView.showContent(Constants.LIKE_RESULT_SINGFILED, userCenterBean);
                        } else {
                            mView.showContent(Constants.LIKE_TYPE_FILED, userCenterBean);
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错了~请重试");
                    }
                });
        addSubscrebe(subscribe);
    }


//    @Override
//    public void getLikeListInfo(int page) {
//        Subscription subscription = mRetrofitHelper.fetchLikeListInfo(page)
//                .compose(RxUtil.<LikeListBean>rxSchedulerHelper())
//                .subscribe(new Action1<LikeListBean>() {
//                    @Override
//                    public void call(LikeListBean likeListBean) {
//                        if (likeListBean.getData().getList() == null){
//                            return;
//                        }
//                        for (int i = 0 ; i < likeListBean.getData().getList().size();i++){
//                            RealmLikeBean realmLikeBean = new RealmLikeBean();
//                            realmLikeBean.setId(String.valueOf(likeListBean.getData().getList().get(i).getActivity_id()));
//                            realmHelper.insertLikeBean(realmLikeBean);
//                        }
//
//                        mView.initLikeData(likeListBean,ty);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//
//                    }
//                });
//        addSubscrebe(subscription);
//    }

//    @Override
//    public void getLikeListInfo(int page, final int type) {
//
//        switch (type){
//            case 1:
//                Subscription subscription = mRetrofitHelper.fetchLikeListInfo(page)
//                        .compose(RxUtil.<LikeListBean>rxSchedulerHelper())
//                        .subscribe(new Action1<LikeListBean>() {
//                            @Override
//                            public void call(LikeListBean likeListBean) {
//                                if (likeListBean.getData().getList() == null){
//                                    return;
//                                }
////                                for (int i = 0 ; i < likeListBean.getData().getList().size();i++){
//////                                    RealmLikeBean realmLikeBean = new RealmLikeBean();
//////                                    realmLikeBean.setId(String.valueOf(likeListBean.getData().getList().get(i).getActivity_id()));
//////                                    realmHelper.insertLikeBean(realmLikeBean);
////                                }
//                                mView.initLikeData(likeListBean,type);
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//
//                            }
//                        });
//                addSubscrebe(subscription);
//
//                break;
//            case 2:
//
//                Subscription subscription1 = mRetrofitHelper.fetchArticleCollect(page)
//                        .compose(RxUtil.<ArticleCollectBean>rxSchedulerHelper())
//                        .subscribe(new Action1<ArticleCollectBean>() {
//                            @Override
//                            public void call(ArticleCollectBean articleCollectBean) {
//
//                                if (articleCollectBean.getData().getList() == null){
//                                    return;
//                                }
//                                for (int i = 0 ; i < articleCollectBean.getData().getList().size();i++){
//                                    RealmLikeBean realmLikeBean = new RealmLikeBean();
//                                    realmLikeBean.setId(String.valueOf(articleCollectBean.getData().getList().get(i).getArticle_id()));
//                                    realmHelper.insertLikeBean(realmLikeBean);
//                                }
//                                mView.initLikeData(articleCollectBean,type);
//
//
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//
//                            }
//                        });
//                addSubscrebe(subscription1);
//
//
//                break;
//            case 3:
//
//                Subscription subscription2 = mRetrofitHelper.fetchShopCollect(page)
//                        .compose(RxUtil.<ShopCollectBean>rxSchedulerHelper())
//                        .subscribe(new Action1<ShopCollectBean>() {
//                            @Override
//                            public void call(ShopCollectBean shopCollectBean) {
//                                if (shopCollectBean.getData().getList() == null){
//                                    return;
//                                }
//                                for (int i = 0 ; i < shopCollectBean.getData().getList().size();i++){
//                                    RealmLikeBean realmLikeBean = new RealmLikeBean();
//                                    realmLikeBean.setId(String.valueOf(shopCollectBean.getData().getList().get(i).getSeller_id()));
//                                    realmHelper.insertLikeBean(realmLikeBean);
//                                }
//                                mView.initLikeData(shopCollectBean,type);
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//
//                            }
//                        });
//                addSubscrebe(subscription2);
//                break;
//
//          }
//        }

}