package com.mperfit.perfit.presenter.presenter;

import com.mperfit.perfit.app.App;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.NormalLoginBean;
import com.mperfit.perfit.model.bean.ReLoadPersonalDataBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.LoginContract;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/10/28
*/

public class LoginPresenterImpl extends RxPresenter<LoginContract.View> implements LoginContract.Presenter{
    private RetrofitHelper retrofitHelper;

    @Inject
    public LoginPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper =retrofitHelper;
    }

    @Override
    public void usePhoneLogin(String phone,String passWord,String uMengToken) {
        Subscription subscribe = retrofitHelper.fetchNormalLogin(phone, passWord, uMengToken)
                .compose(RxUtil.<NormalLoginBean>rxSchedulerHelper())
                .subscribe(new Action1<NormalLoginBean>() {
                    @Override
                    public void call(NormalLoginBean normalLoginBean) {
                        if (normalLoginBean.getCode() == 100) {
                            String authId = normalLoginBean.getData().getAuth_id();
                            String authCode = normalLoginBean.getData().getAuth_code();
                            long userId = normalLoginBean.getData().getUser_id();
                            SharedPreferenceUtil.setAuthCode(authCode);
                            SharedPreferenceUtil.setAuthId(authId);
                            SharedPreferenceUtil.setUserId(userId);
                            mView.showLoginResult(Constants.REGIST_SUCCESS,userId,normalLoginBean.getMessage());

                            //跳转到首页
                            if (!SharedPreferenceUtil.getAuthid().equals("1")||!SharedPreferenceUtil.getAuthCode().equals("auth_code")){
                                mView.toHome();
                                ReLoadPersonalDataBean reLoadPersonalDataBean = new ReLoadPersonalDataBean();
                                reLoadPersonalDataBean.setAuthId(authId);
                                reLoadPersonalDataBean.setAuthCode(authCode);
                                RxBus.getDefault().post(reLoadPersonalDataBean);
                            } else {
                                SharedPreferenceUtil.setAuthCode(authCode);
                                SharedPreferenceUtil.setAuthId(authId);
                                SharedPreferenceUtil.setUserId(userId);
                                mView.toHome();
                            }
                        } else {
                            mView.showLoginResult(Constants.REGIST_FILURE, 0, normalLoginBean.getMessage());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        mView.showError("亲出错啦~请重试");
                    }
                });

        addSubscrebe(subscribe);

    }

    @Override
    public void useWeixinLogin() {
        IWXAPI api = WXAPIFactory.createWXAPI(App.getInstance(), Constants.WEIXNAPP_ID);
        api.registerApp(Constants.WEIXNAPP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "app_wechat";
        api.sendReq(req);
    }
}