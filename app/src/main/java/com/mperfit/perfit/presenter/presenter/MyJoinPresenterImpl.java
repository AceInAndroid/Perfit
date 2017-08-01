package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.MyJoinedActivityBean;
import com.mperfit.perfit.model.bean.PersonalMyGameBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.MyJoinContract;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/11/03
*/

public class MyJoinPresenterImpl extends RxPresenter<MyJoinContract.View> implements MyJoinContract.Presenter{

    private RetrofitHelper retrofitHelper;
    @Inject
    public MyJoinPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper =retrofitHelper;

    }

    @Override
    public void getMyJoinedGame() {
        Subscription subscribe = retrofitHelper.fetchMyGameList(1)
                .compose(RxUtil.<PersonalMyGameBean>rxSchedulerHelper())
                .subscribe(new Action1<PersonalMyGameBean>() {
                    @Override
                    public void call(PersonalMyGameBean myJoinedGameBean) {
                        if (myJoinedGameBean.getCode() == Constants.TYPE_RELOGIN){
                            mView.reLogin();
                            return;
                        } else if (myJoinedGameBean.getCode() ==10006){
                            mView.reLogin();
                            return;
                        }
                        mView.showMyJoinedGame(myJoinedGameBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscrebe(subscribe);


    }

    @Override
    public void getMyJoinedActivity() {

        Subscription subscribe = retrofitHelper.fetchMyJoinedActivity(1)
                .compose(RxUtil.<MyJoinedActivityBean>rxSchedulerHelper())
                .subscribe(new Action1<MyJoinedActivityBean>() {
                    @Override
                    public void call(MyJoinedActivityBean myJoinedActivityBean) {

                        if (myJoinedActivityBean.getCode() == Constants.TYPE_RELOGIN){
                            mView.reLogin();
                            return;
                        }else if (myJoinedActivityBean.getCode() ==10006){
                            mView.reLogin();
                            return;
                        }
                        mView.showMyJoinedActivity(myJoinedActivityBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscrebe(subscribe);


    }

    @Override
    public void getMyJoinedGameMore(int page) {

        Subscription subscribe = retrofitHelper.fetchMyGameList(page)
                .compose(RxUtil.<PersonalMyGameBean>rxSchedulerHelper())
                .subscribe(new Action1<PersonalMyGameBean>() {
                    @Override
                    public void call(PersonalMyGameBean myJoinedGameBean) {
                        if (myJoinedGameBean.getCode() == Constants.TYPE_RELOGIN){
                            mView.reLogin();
                            return;
                        } else if (myJoinedGameBean.getCode() ==10006){
                            mView.reLogin();
                            return;
                        }
                        mView.showMoreMyJoinedGame(myJoinedGameBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscribe);

    }

    @Override
    public void getMyJoinedActivityMore(int page) {

        Subscription subscribe = retrofitHelper.fetchMyJoinedActivity(page)
                .compose(RxUtil.<MyJoinedActivityBean>rxSchedulerHelper())
                .subscribe(new Action1<MyJoinedActivityBean>() {
                    @Override
                    public void call(MyJoinedActivityBean myJoinedActivityBean) {

                        if (myJoinedActivityBean.getCode() == Constants.TYPE_RELOGIN){
                            mView.reLogin();
                            return;
                        } else if (myJoinedActivityBean.getCode() ==10006){
                            mView.reLogin();
                            return;
                        }

                        mView.showMyJoinedActivity(myJoinedActivityBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscrebe(subscribe);


    }


}