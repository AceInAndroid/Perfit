package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.ActivityBean;
import com.mperfit.perfit.model.bean.CompetitionGameBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ActivityContract;
import com.mperfit.perfit.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/10/24
*/

public class ActivityPresenterImpl extends RxPresenter<ActivityContract.View> implements ActivityContract.Presenter{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ActivityPresenterImpl(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;

    }

    @Override
    public void getActivityData() {

        Subscription subscription = mRetrofitHelper.fetchActivityListInfo(1)
                .compose(RxUtil.<ActivityBean>rxSchedulerHelper())
                .subscribe(new Action1<ActivityBean>() {
                    @Override
                    public void call(ActivityBean activityBean) {
                        mView.showContent(activityBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("亲,出错啦");
                    }
                });

            addSubscrebe(subscription);


    }

    @Override
    public void getGameData(int page) {

        Subscription subscription = mRetrofitHelper.fetchCompetitionGameListInfo(1)
                .compose(RxUtil.<CompetitionGameBean.DataBean>handleResult())
                .subscribe(new Action1<CompetitionGameBean.DataBean>() {
                    @Override
                    public void call(CompetitionGameBean.DataBean dataBean) {
                        mView.showGameContent(dataBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscription);


    }

    @Override
    public void getMoreDate(int currentPage) {
        Subscription moreSubscription = mRetrofitHelper.fetchActivityListInfo(currentPage)
                .compose(RxUtil.<ActivityBean>rxSchedulerHelper())
                .subscribe(new Action1<ActivityBean>() {
                    @Override
                    public void call(ActivityBean activityBean) {
                        mView.showMoreContent(activityBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("亲,出错啦");
                    }
                });

        addSubscrebe(moreSubscription);
    }
}