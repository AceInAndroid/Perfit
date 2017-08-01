package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.ActivityDetailBean;
import com.mperfit.perfit.model.bean.AddLikeBean;
import com.mperfit.perfit.model.bean.ArticleDetailBean;
import com.mperfit.perfit.model.bean.EnrollResultBean;
import com.mperfit.perfit.model.bean.MatchDetaiBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ArticleDetailContract;
import com.mperfit.perfit.utils.ErroCodeChargeUtil;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by MVPHelper on 2016/10/18
 */

public class ArticleDetailPresenterImpl extends RxPresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
//    private RealmHelper realmHelper;

    @Inject
    public ArticleDetailPresenterImpl(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
//        this.realmHelper = realmHelper;
    }

    @Override
    public void getActivityDetail(String id) {
        Subscription subscribe = mRetrofitHelper.fetchActivityDetail(id)
                .compose(RxUtil.<ActivityDetailBean>rxSchedulerHelper())
                .subscribe(new Action1<ActivityDetailBean>() {
                    @Override
                    public void call(ActivityDetailBean activityDetailBean) {
                        mView.showContent(activityDetailBean);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        Logger.e("出错" + throwable.getMessage());
                        mView.showError("抱歉亲~出错了");
                    }
                });
        addSubscrebe(subscribe);
    }

    @Override
    public void getArticleDetail(String id) {
        Subscription subscription = mRetrofitHelper.fetchArticleDetail(id)
                .compose(RxUtil.<ArticleDetailBean>rxSchedulerHelper())
                .map(new Func1<ArticleDetailBean, ArticleDetailBean>() {
                    @Override
                    public ArticleDetailBean call(ArticleDetailBean articleDetailBean) {
                        ArticleDetailBean.DataBean.ArticleBean articleBean = articleDetailBean.getData().getArticle();

//                        if (articleDetailBean.getData().getArticle().getIs_collect() == 0){
//                            mView.setLikeState(false);
//
//                        } else {
//                            mView.setLikeState(true);
//
//                        }
                        return articleDetailBean;
                    }
                })
                .subscribe(new Action1<ArticleDetailBean>() {
                    @Override
                    public void call(ArticleDetailBean articleDetailBean) {
                        mView.showArticleContent(articleDetailBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscription);
    }

    @Override
    public void getMatchDetail(String id) {

        Subscription subscribe = mRetrofitHelper.fetchMatchDetail(id)
                .compose(RxUtil.<MatchDetaiBean>rxSchedulerHelper())
                .subscribe(new Action1<MatchDetaiBean>() {
                    @Override
                    public void call(MatchDetaiBean matchDetaiBean) {
                        mView.showMatchContent(matchDetaiBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mView.showError("抱歉亲~出错了");
                    }
                });

        addSubscrebe(subscribe);


    }

    @Override
    public void putActivityEnrollFormInfo(String activityId) {
        Subscription subscription = mRetrofitHelper.fetchEnroll(activityId)
                .compose(RxUtil.<EnrollResultBean>rxSchedulerHelper())
                .subscribe(new Action1<EnrollResultBean>() {
                    @Override
                    public void call(EnrollResultBean enrollResultBean) {
                        int checkCode = ErroCodeChargeUtil.checkCode(enrollResultBean.getCode());
                        if (checkCode == Constants.LIKE_RESULT_SINGFILED) {
                            mView.showActivityEnrollResult(Constants.LIKE_RESULT_SINGFILED, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_TYPE_FILED) {
                            mView.showActivityEnrollResult(Constants.LIKE_TYPE_FILED, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_RESULT_SUCCESS) {
                            mView.showActivityEnrollResult(Constants.LIKE_RESULT_SUCCESS, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_TYPE_DISABLE) {
                            mView.showActivityEnrollResult(Constants.LIKE_TYPE_DISABLE, enrollResultBean.getMessage());
                        } else {
                            mView.showActivityEnrollResult(Constants.LIKE_TYPE_FILED, enrollResultBean.getMessage());
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错了");
                    }
                });

        addSubscrebe(subscription);
    }

    @Override
    public void putMatchEnrollFormInfo(String matchId) {
        Subscription subscribe = mRetrofitHelper.fetchMatchEnroll(matchId)
                .compose(RxUtil.<EnrollResultBean>rxSchedulerHelper())
                .subscribe(new Action1<EnrollResultBean>() {
                    @Override
                    public void call(EnrollResultBean enrollResultBean) {
                        int checkCode = ErroCodeChargeUtil.checkCode(enrollResultBean.getCode());
                        if (checkCode == Constants.LIKE_RESULT_SINGFILED) {
                            mView.showMatchEnrollResult(Constants.LIKE_RESULT_SINGFILED, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_TYPE_FILED) {
                            mView.showMatchEnrollResult(Constants.LIKE_TYPE_FILED, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_RESULT_SUCCESS) {
                            mView.showMatchEnrollResult(Constants.LIKE_RESULT_SUCCESS, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_TYPE_DISABLE) {
                            mView.showMatchEnrollResult(Constants.LIKE_TYPE_DISABLE, enrollResultBean.getMessage());
                        } else {
                            mView.showMatchEnrollResult(Constants.LIKE_TYPE_FILED, enrollResultBean.getMessage());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscribe);
    }

    @Override
    public void insetLike(String id, int type) {

        Subscription subscription = mRetrofitHelper.fetchAddLike(id, type)
                .compose(RxUtil.<AddLikeBean>rxSchedulerHelper())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {
                        if (addLikeBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 100) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SUCCESS, addLikeBean.getMessage());
                            mView.setLikeState(true);
                        } else if (addLikeBean.getCode() == 10006) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 10002) {
                            mView.showLikeResult(Constants.LIKE_TYPE_LIKED, addLikeBean.getMessage());
                            mView.setLikeState(true);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLikeResult(Constants.LIKE_TYPE_FILED, "出错啦~请重试~");
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void deleteLike(final String id, int type) {
        Subscription subscription = mRetrofitHelper.fetchDeleteLike(id, type)
                .compose(RxUtil.<AddLikeBean>rxSchedulerHelper())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {

                        if (addLikeBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 100) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SUCCESS, addLikeBean.getMessage());
                            mView.setLikeState(false);
                        } else if (addLikeBean.getCode() == 10006) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else {
                            mView.showLikeResult(Constants.LIKE_TYPE_FILED, addLikeBean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLikeResult(Constants.LIKE_TYPE_FILED, "出错啦~请重试~");
                    }
                });

        addSubscrebe(subscription);

    }


}