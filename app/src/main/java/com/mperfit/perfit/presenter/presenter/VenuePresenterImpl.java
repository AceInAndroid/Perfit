package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.VenueAreaListBean;
import com.mperfit.perfit.model.bean.VenueListInfoBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.VenueContract;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/12/09
*/

public class VenuePresenterImpl extends RxPresenter<VenueContract.View> implements VenueContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public VenuePresenterImpl(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper ;
    }

    @Override
    public void getVenueList(int page, int areaId, double longitude, double latitude) {
        Subscription subscription = mRetrofitHelper.fetchVenueList(page, areaId, longitude, latitude)
                .compose(RxUtil.<VenueListInfoBean>rxSchedulerHelper())
                .subscribe(new Action1<VenueListInfoBean>() {
                    @Override
                    public void call(VenueListInfoBean venueListInfoBean) {
                            mView.showContent(venueListInfoBean);
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
    public void getVenueList() {

        Subscription subscription = mRetrofitHelper.fetchVenueList()
                .compose(RxUtil.<VenueListInfoBean>rxSchedulerHelper())
                .subscribe(new Action1<VenueListInfoBean>() {
                    @Override
                    public void call(VenueListInfoBean venueListInfoBean) {
                        mView.showContent(venueListInfoBean);
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
    public void getVenueAreaList() {
        Subscription subscription = mRetrofitHelper.fetchVenueAreaList()
                .compose(RxUtil.<VenueAreaListBean>rxSchedulerHelper())
                .subscribe(new Action1<VenueAreaListBean>() {
                    @Override
                    public void call(VenueAreaListBean venueAreaListBean) {
                        mView.showArea(venueAreaListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void getMore(int page, int areaId, double longitude, double latitude) {
        Subscription subscription = mRetrofitHelper.fetchVenueList(page, areaId, longitude, latitude)
                .compose(RxUtil.<VenueListInfoBean>rxSchedulerHelper())
                .subscribe(new Action1<VenueListInfoBean>() {
                    @Override
                    public void call(VenueListInfoBean venueListInfoBean) {
                        mView.showMore(venueListInfoBean);
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