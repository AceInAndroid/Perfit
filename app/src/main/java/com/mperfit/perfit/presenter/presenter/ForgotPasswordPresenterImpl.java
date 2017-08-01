package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.FindPassBean;
import com.mperfit.perfit.model.bean.VerificationCodeBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ForgotPasswordContract;
import com.mperfit.perfit.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2016/10/31
 */

public class ForgotPasswordPresenterImpl extends RxPresenter<ForgotPasswordContract.View> implements ForgotPasswordContract.Presenter {
    private RetrofitHelper retrofitHelper;

    @Inject
    public ForgotPasswordPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void getInvalidateCode(String phone, int type) {
        Subscription mInvailidateSubscription = retrofitHelper.fetchVerificationCode(phone, type)
                .compose(RxUtil.<VerificationCodeBean>rxSchedulerHelper())
                .subscribe(new Action1<VerificationCodeBean>() {
                    @Override
                    public void call(VerificationCodeBean verificationCodeBean) {
                        mView.showGetCodeResult(verificationCodeBean.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错了" + throwable.getMessage());
                    }
                });
        addSubscrebe(mInvailidateSubscription);
    }

    @Override
    public void goFindPass(String phoneNum, String password, String code) {
        Subscription mFindPasssubscribe = retrofitHelper.fetchFindPassword(phoneNum, password, code)
                .compose(RxUtil.<FindPassBean>rxSchedulerHelper())
                .subscribe(new Action1<FindPassBean>() {
                    @Override
                    public void call(FindPassBean findPassBean) {
                        if (findPassBean.getCode() == 100){
                            mView.showFindResult(Constants.REGIST_SUCCESS,findPassBean.getMessage());
                        } else {
                            mView.showFindResult(Constants.REGIST_FILURE,findPassBean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错了" + throwable.getMessage());

                    }
                });

        addSubscrebe(mFindPasssubscribe);
    }


}