package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.ClassPageTopData;
import com.mperfit.perfit.model.bean.CourseListBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ClassContract;
import com.mperfit.perfit.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/11/21
*/

public class ClassPresenterImpl extends RxPresenter<ClassContract.View> implements ClassContract.Presenter{

    private RetrofitHelper retrofitHelper;
    @Inject
    public ClassPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper ;
    }

    @Override
    public void getTopMenuData() {
        Subscription subscription = retrofitHelper.fetchCourseMenuInfo()
                .compose(RxUtil.<ClassPageTopData>rxSchedulerHelper())
                .subscribe(new Action1<ClassPageTopData>() {
                    @Override
                    public void call(ClassPageTopData classPageTopData) {
                            mView.showMenuInfo(classPageTopData);
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
    public void getCourseList(int page,int areaId,int categoryId,int sortId,double longitude,double latitude) {
        Subscription subscription = retrofitHelper.fetchCourseListInfo(page,areaId,categoryId,sortId,longitude,latitude)
                .compose(RxUtil.<CourseListBean>rxSchedulerHelper())
                .subscribe(new Action1<CourseListBean>() {
                    @Override
                    public void call(CourseListBean courseListBean) {
                        mView.showCourseListInfo(courseListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscription);
    }


    @Override
    public void getCourseList() {
        Subscription subscription = retrofitHelper.fetchCourseListInfo()
                .compose(RxUtil.<CourseListBean>rxSchedulerHelper())
                .subscribe(new Action1<CourseListBean>() {
                    @Override
                    public void call(CourseListBean courseListBean) {
                        mView.showCourseListInfo(courseListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscription);
    }

    @Override
    public void getCourseListMore(int page, int areaId, int categoryId, int sortId, double longitude, double latitude) {
        Subscription subscription = retrofitHelper.fetchCourseListInfo(page,areaId,categoryId,sortId,longitude,latitude)
                .compose(RxUtil.<CourseListBean>rxSchedulerHelper())
                .subscribe(new Action1<CourseListBean>() {
                    @Override
                    public void call(CourseListBean courseListBean) {
                        mView.showMoreCourseListInfo(courseListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscription);
    }


}