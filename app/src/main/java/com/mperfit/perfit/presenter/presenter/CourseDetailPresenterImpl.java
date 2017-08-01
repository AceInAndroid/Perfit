package com.mperfit.perfit.presenter.presenter;
import android.content.Context;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.AddLikeBean;
import com.mperfit.perfit.model.bean.CourseDetailBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.CourseDetailContract;
import com.mperfit.perfit.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/11/23
*/

public class CourseDetailPresenterImpl extends RxPresenter<CourseDetailContract.View> implements CourseDetailContract.Presenter{
    private RetrofitHelper mRetrofitHelper;
    @Inject
    public CourseDetailPresenterImpl(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getCourseDetai(String courseId) {
        Subscription subscription = mRetrofitHelper.fetchCourseDetailInfo(courseId)
                .compose(RxUtil.<CourseDetailBean>rxSchedulerHelper())
                .subscribe(new Action1<CourseDetailBean>() {
                    @Override
                    public void call(CourseDetailBean courseDetailBean) {
                        mView.showContent(courseDetailBean);
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