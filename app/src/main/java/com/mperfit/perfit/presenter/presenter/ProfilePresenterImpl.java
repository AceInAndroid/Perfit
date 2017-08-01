package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.ProfileBean;
import com.mperfit.perfit.model.bean.ProfileUpdateBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ProfileContract;
import com.mperfit.perfit.utils.ErroCodeChargeUtil;
import com.mperfit.perfit.utils.RxUtil;

import javax.inject.Inject;

import okhttp3.RequestBody;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2016/11/03
 */

public class ProfilePresenterImpl extends RxPresenter<ProfileContract.View> implements ProfileContract.Presenter {

    private RetrofitHelper retrofitHelper;

    @Inject
    public ProfilePresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void getProfileInfo() {
        Subscription mProfileSubscribe = retrofitHelper.fetchProfileInfo()
                .compose(RxUtil.<ProfileBean>rxSchedulerHelper())
                .subscribe(new Action1<ProfileBean>() {
                    @Override
                    public void call(ProfileBean profileBean) {
                        mView.showProfileInfo(profileBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscrebe(mProfileSubscribe);

    }

    @Override
    public void saveProfileInfo(String name, int sex, String head,
                                String birth, String profession,
                                String enmotionState, String signature) {
        Subscription mSaveSubscribe = retrofitHelper.fetchProfileUpdate(name, sex, head, birth, profession, enmotionState, signature)
                .compose(RxUtil.<ProfileUpdateBean>rxSchedulerHelper())
                .subscribe(new Action1<ProfileUpdateBean>() {
                    @Override
                    public void call(ProfileUpdateBean profileUpdateBean) {

                        int checkCode = ErroCodeChargeUtil.checkCode(profileUpdateBean.getCode());
                        if (checkCode == Constants.LIKE_RESULT_SINGFILED){
                            mView.showSaveReult(Constants.LIKE_RESULT_SINGFILED,profileUpdateBean.getMessage());
                        } else if (checkCode == Constants.LIKE_TYPE_FILED){
                            mView.showSaveReult(Constants.LIKE_TYPE_FILED,profileUpdateBean.getMessage());
                        } else if (checkCode == Constants.LIKE_RESULT_SUCCESS ){
                            mView.showSaveReult(Constants.LIKE_RESULT_SUCCESS,profileUpdateBean.getMessage());
                        } else {
                            mView.showSaveReult(Constants.LIKE_TYPE_FILED,profileUpdateBean.getMessage());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错啦");

                    }
                });
        addSubscrebe(mSaveSubscribe);

    }


    @Override
    public void saveProfileInfo(String name, int sex,
                                String birth, String profession,
                                String enmotionState, String signature) {
        Subscription mSaveSubscribe = retrofitHelper.fetchProfileUpdate(name, sex, birth, profession, enmotionState, signature)
                .compose(RxUtil.<ProfileUpdateBean>rxSchedulerHelper())
                .subscribe(new Action1<ProfileUpdateBean>() {
                    @Override
                    public void call(ProfileUpdateBean profileUpdateBean) {

                        int checkCode = ErroCodeChargeUtil.checkCode(profileUpdateBean.getCode());
                        if (checkCode == Constants.LIKE_RESULT_SINGFILED){
                            mView.showSaveReult(Constants.LIKE_RESULT_SINGFILED,profileUpdateBean.getMessage());
                        } else if (checkCode == Constants.LIKE_TYPE_FILED){
                            mView.showSaveReult(Constants.LIKE_TYPE_FILED,profileUpdateBean.getMessage());
                        } else if (checkCode == Constants.LIKE_RESULT_SUCCESS ){
                            mView.showSaveReult(Constants.LIKE_RESULT_SUCCESS,profileUpdateBean.getMessage());
                        } else {
                            mView.showSaveReult(Constants.LIKE_TYPE_FILED,profileUpdateBean.getMessage());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错啦");

                    }
                });
        addSubscrebe(mSaveSubscribe);

    }


}