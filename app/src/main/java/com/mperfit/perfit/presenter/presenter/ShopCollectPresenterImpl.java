package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.ActivityDetailBean;
import com.mperfit.perfit.model.bean.AddLikeBean;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ShopCollectContract;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
* Created by zhangbing on 2016/11/30
*/

public class ShopCollectPresenterImpl extends RxPresenter<ShopCollectContract.View> implements ShopCollectContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
//    private RealmHelper realmHelper;

    @Inject
    public ShopCollectPresenterImpl(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
//        this.realmHelper = realmHelper;
    }



    @Override
    public void getCollectData(int page) {
        Subscription subscription = mRetrofitHelper.fetchShopCollect(page)
                .compose(RxUtil.<ShopCollectBean>rxSchedulerHelper())

                .subscribe(new Action1<ShopCollectBean>() {
                    @Override
                    public void call(ShopCollectBean shopCollectBean) {
                        mView.showContent(shopCollectBean);
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
    public void insetLike( String id, int type, final int position) {
        Subscription subscription = mRetrofitHelper.fetchAddLike(id, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {
                        if (addLikeBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 100) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SUCCESS, addLikeBean.getMessage());
//                            realmHelper.insertLikeBean(realmLikeBean);
                            mView.setLikeState(true,position);
                            Logger.e("insetLike调用成功" + true + "position="+ position);
                        } else if (addLikeBean.getCode() == 10006) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 10002){
                            mView.showLikeResult(Constants.LIKE_TYPE_LIKED, addLikeBean.getMessage());
                            mView.setLikeState(true,position);
//                            realmHelper.insertLikeBean(realmLikeBean);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                        Logger.e("show insetLike  showerro调用了" + throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void deleteLike(final String id, int type, final int position) {
        Subscription subscription = mRetrofitHelper.fetchDeleteLike(id, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {
                        Logger.e(addLikeBean.getCode() + "" );
                        if (addLikeBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 100) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SUCCESS, addLikeBean.getMessage());
//                            realmHelper.deleteLikeBean(id);
                            mView.setLikeState(false,position);
                            Logger.e("deleteLike调用成功" + true + "position="+ position);
                        } else if (addLikeBean.getCode() == 10006) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else {
                            mView.showLikeResult(Constants.LIKE_TYPE_FILED, addLikeBean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                        Logger.e("deleteLike show error调用成功 " + throwable.getMessage());
                    }
                });

        addSubscrebe(subscription);

    }

    @Override
    public void getMoreData(int page) {
        Subscription subscription = mRetrofitHelper.fetchShopCollect(page)
                .compose(RxUtil.<ShopCollectBean>rxSchedulerHelper())

                .subscribe(new Action1<ShopCollectBean>() {
                    @Override
                    public void call(ShopCollectBean shopCollectBean) {
                        mView.showMoreData(shopCollectBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);

    }
}