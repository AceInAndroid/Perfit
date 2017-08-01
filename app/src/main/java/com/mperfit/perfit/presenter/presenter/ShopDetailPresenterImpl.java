package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.AddLikeBean;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.model.bean.ShopDtailBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ShopDetailContract;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.R.id.list;

/**
 * Created by zhangbing on 2016/11/24
 */

public class ShopDetailPresenterImpl extends RxPresenter<ShopDetailContract.View> implements ShopDetailContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
//    private RealmHelper realmHelper;

    @Inject
    public ShopDetailPresenterImpl(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
//        this.realmHelper = realmHelper;
    }

    @Override
    public void getShopDetailInfo(String sellerId) {
        Subscription subscription = mRetrofitHelper.fetchShopDetail(sellerId)
                .compose(RxUtil.<ShopDtailBean>rxSchedulerHelper())
                .map(new Func1<ShopDtailBean, ShopDtailBean>() {
                    @Override
                    public ShopDtailBean call(ShopDtailBean shopDtailBean) {
                        long sellerid = shopDtailBean.getData().getSeller().getSeller_id();
//                        boolean isLiked = realmHelper.queryLikeId(String.valueOf(sellerid));

                        if (shopDtailBean.getData().getSeller().getIs_collect() == 0 ){
                            shopDtailBean.getData().getSeller().setSelect(false);
                            mView.setLikeState(false);
                        } else {
                            shopDtailBean.getData().getSeller().setSelect(true);
                            mView.setLikeState(true);
                        }

                        return shopDtailBean;
                    }
                })
                .subscribe(new Action1<ShopDtailBean>() {
                    @Override
                    public void call(ShopDtailBean shopDtailBean) {
                        mView.showContent(shopDtailBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscription);

    }


    @Override
    public void insetLike( String id, int type) {

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
//                            realmHelper.insertLikeBean(realmLikeBean);
                            mView.setLikeState(true);
                        } else if (addLikeBean.getCode() == 10006) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 10002) {
                            mView.showLikeResult(Constants.LIKE_TYPE_LIKED, addLikeBean.getMessage());
//                            realmHelper.insertLikeBean(realmLikeBean);
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
        mRetrofitHelper.fetchDeleteLike(id, type)
                .compose(RxUtil.<AddLikeBean>rxSchedulerHelper())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {

                        if (addLikeBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showLikeResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 100) {
                            mView.showLikeResult(Constants.LIKE_RESULT_SUCCESS, addLikeBean.getMessage());
//                            realmHelper.deleteLikeBean(id);
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

    }


}