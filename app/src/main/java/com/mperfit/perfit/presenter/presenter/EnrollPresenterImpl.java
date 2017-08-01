package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.EnrollResultBean;
import com.mperfit.perfit.model.bean.VerificationCodeBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.EnrollContract;
import com.mperfit.perfit.utils.ErroCodeChargeUtil;
import com.mperfit.perfit.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2016/10/27
 */

public class EnrollPresenterImpl extends RxPresenter<EnrollContract.View> implements EnrollContract.Presenter {

    private RetrofitHelper retrofitHelper;

    @Inject
    public EnrollPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;

    }

    @Override
    public void getActivityInfo() {

    }

    @Override
    public void putEnrollFormInfo(String activityId, String name, String phone,
                                  int sex, int age, String remark, String code) {
        Subscription enrollSubscrip = retrofitHelper.fetchEnroll(activityId, name, phone, sex, age, remark, code)
                .compose(RxUtil.<EnrollResultBean>rxSchedulerHelper())
                .subscribe(new Action1<EnrollResultBean>() {
                    @Override
                    public void call(EnrollResultBean enrollResultBean) {
                        int checkCode = ErroCodeChargeUtil.checkCode(enrollResultBean.getCode());
                        if (checkCode == Constants.LIKE_RESULT_SINGFILED) {
                            mView.showEnrollResult(Constants.LIKE_RESULT_SINGFILED, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_TYPE_FILED) {
                            mView.showEnrollResult(Constants.LIKE_TYPE_FILED, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_RESULT_SUCCESS) {
                            mView.showEnrollResult(Constants.LIKE_RESULT_SUCCESS, enrollResultBean.getMessage());
                        } else if (checkCode == Constants.LIKE_TYPE_DISABLE) {
                            mView.showEnrollResult(Constants.LIKE_TYPE_DISABLE, enrollResultBean.getMessage());
                        } else {
                            mView.showEnrollResult(Constants.LIKE_TYPE_FILED, enrollResultBean.getMessage());
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错了");
                    }
                });

        addSubscrebe(enrollSubscrip);
    }

    @Override
    public void putMatchEnrollFormInfo(String matchId) {
        Subscription subscribe = retrofitHelper.fetchMatchEnroll(matchId)
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
    public void getPhoneCode(String phone, int type) {
        Subscription mPhoneCodesubscribe = retrofitHelper.fetchVerificationCode(phone, type)
                .compose(RxUtil.<VerificationCodeBean>rxSchedulerHelper())
                .subscribe(new Action1<VerificationCodeBean>() {
                    @Override
                    public void call(VerificationCodeBean verificationCodeBean) {
                        mView.showIdentifyingCode(verificationCodeBean.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错了");
                    }
                });
        addSubscrebe(mPhoneCodesubscribe);

    }
}