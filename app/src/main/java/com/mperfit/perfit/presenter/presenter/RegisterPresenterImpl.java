package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.VerificationCodeBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.RegisterContract;
import com.mperfit.perfit.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2016/10/28
 */

public class RegisterPresenterImpl extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RetrofitHelper retrofitHelper;

    @Inject
    public RegisterPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void getInvalidateCode(String phone, int type) {
        Subscription getInvailidateCodeSubscribe = retrofitHelper.fetchVerificationCode(phone, type)
                .compose(RxUtil.<VerificationCodeBean>rxSchedulerHelper())
                .subscribe(new Action1<VerificationCodeBean>() {
                    @Override
                    public void call(VerificationCodeBean verificationCodeBean) {
                        mView.showRegistResult(verificationCodeBean.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错了" + throwable.getMessage());
                    }
                });
        addSubscrebe(getInvailidateCodeSubscribe);

    }

    @Override
    public void goRegist(String phoneNum, String password, String code, String uMengToken) {

        Subscription rigistSubscribe = retrofitHelper.fetchRegister(phoneNum, password, code, uMengToken)
                .compose(RxUtil.<VerificationCodeBean>rxSchedulerHelper())
                .subscribe(new Action1<VerificationCodeBean>() {
                    @Override
                    public void call(VerificationCodeBean verificationCodeBean) {
                        if (verificationCodeBean.getCode() == 100){
                            mView.showRegistResult(Constants.REGIST_SUCCESS,verificationCodeBean.getMessage());
                        } else {
                            mView.showRegistResult(Constants.REGIST_FILURE,verificationCodeBean.getMessage());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });

        addSubscrebe(rigistSubscribe);

    }
}