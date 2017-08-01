package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.AddLikeBean;
import com.mperfit.perfit.model.bean.LikeListBean;

import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.MyLikedListContract;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/11/09
*/

public class MyLikedListPresenterImpl extends RxPresenter<MyLikedListContract.View> implements MyLikedListContract.Presenter{
    private RetrofitHelper retrofitHelper;
//    private RealmHelper realmHelper;

    @Inject
    public MyLikedListPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper ;
//        this.realmHelper = realmHelper;
    }

    @Override
    public void getLikeListInfo(int page) {
        Subscription subscription = retrofitHelper.fetchLikeListInfo(page)
                .compose(RxUtil.<LikeListBean>rxSchedulerHelper())
                .subscribe(new Action1<LikeListBean>() {
                    @Override
                    public void call(LikeListBean likeListBean) {
                        mView.showContent(likeListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void deleteItem(final String id) {

        Subscription subscribe = retrofitHelper.fetchDeleteLike(id, Constants.LIKE_TYPE_ACTIVITY)
                .compose(RxUtil.<AddLikeBean>rxSchedulerHelper())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {
                        if (addLikeBean.getCode() == 100) {
//                            realmHelper.deleteLikeBean(String.valueOf(id));

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
    public void getMoreData(int page) {
        Subscription subscription = retrofitHelper.fetchLikeListInfo(page)
                .compose(RxUtil.<LikeListBean>rxSchedulerHelper())
                .subscribe(new Action1<LikeListBean>() {
                    @Override
                    public void call(LikeListBean likeListBean) {
                        mView.showMore(likeListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void insetLike( String id, final int position) {
        Subscription subscription = retrofitHelper.fetchAddLike(id, Constants.LIKE_TYPE_ACTIVITY)
                .compose(RxUtil.<AddLikeBean>rxSchedulerHelper())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {
                        if (addLikeBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 100) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SUCCESS, addLikeBean.getMessage());
                            mView.setLikeState(true,position);
                        } else if (addLikeBean.getCode() == 10006) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 10002){
                            mView.showLikeResult(Constants.LIKE_TYPE_LIKED, addLikeBean.getMessage());
                            mView.setLikeState(true,position);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void deleteLike(final String id, final int position) {
        Subscription subscription = retrofitHelper.fetchDeleteLike(id, Constants.LIKE_TYPE_ACTIVITY)
                .compose(RxUtil.<AddLikeBean>rxSchedulerHelper())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {
                        if (addLikeBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 100) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SUCCESS, addLikeBean.getMessage());
                            mView.setLikeState(false,position);
                        } else if (addLikeBean.getCode() == 10006) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else {
                            mView.showLikeResult(Constants.LIKE_TYPE_FILED, addLikeBean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });

        addSubscrebe(subscription);

    }


}