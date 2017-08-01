package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.HomeDataBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.HomeContract;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2016/11/15.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {
    private RetrofitHelper retrofitHelper;
    private Subscription intervalSubscription;

    private static final int INTERVAL_INSTANCE = 2;

    private int topCount = 0;
    private int currentTopCount = 0;


    @Inject
    public HomePresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper =retrofitHelper;
    }

    @Override
    public void getHomeData() {
        Subscription subscription = retrofitHelper.fetchHomeDataInfo()
                .compose(RxUtil.<HomeDataBean>rxSchedulerHelper())
                .subscribe(new Action1<HomeDataBean>() {
                    @Override
                    public void call(HomeDataBean homeDataBean) {
                        topCount = homeDataBean.getData().getBanner_list().size();
                        mView.showContent(homeDataBean.getData());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("亲~出了点小问题~请刷新试试~");
                    }
                });
        addSubscrebe(subscription);
    }



}
