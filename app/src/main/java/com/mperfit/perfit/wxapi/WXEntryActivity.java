package com.mperfit.perfit.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.ReLoadPersonalDataBean;
import com.mperfit.perfit.model.bean.WXloginSuccessBean;
import com.mperfit.perfit.model.bean.WeixinLoginBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.main.activity.MainActivity;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.RxUtil;

import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;

import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import rx.Subscriber;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler  {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            super.onCreate(savedInstanceState);
        }catch (Exception e){
            finish();
        }
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXNAPP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
    }



    @Override
    public void onResp(BaseResp resp) {

        int result = 0;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = 0;

                String code = ((SendAuth.Resp) resp).code;

//                String umengPushToken = SharedPreferenceUtil.getUmengPush();
                Logger.e("登录code 是" + code );

                getAccessToken(code);
//                ToastUtils.showShrotMsg(WXEntryActivity.this, "登录成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = 1;
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = 2;
                finish();
                break;
            default:
                result = 3;
                break;
        }

    }

    /**
     * 获取token
     *  @param code
     *
     */
    private void getAccessToken(String code) {
        RetrofitHelper retrofitHelper = new RetrofitHelper(WXEntryActivity.this);
        retrofitHelper.fetchWeixinLogin(code)
                .compose(RxUtil.<WeixinLoginBean>rxSchedulerHelper())
                .subscribe(new Subscriber<WeixinLoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("zhangbing", "失败" + e.toString());
                    }

                    @Override
                    public void onNext(WeixinLoginBean weixinLoginBean) {
                        savaData(weixinLoginBean);
                    }
                });


    }

    private void savaData(WeixinLoginBean weixinLoginBean) {
        long userId = weixinLoginBean.getData().getUser_id();
        String authId = weixinLoginBean.getData().getAuth_id();
        String authCode = weixinLoginBean.getData().getAuth_code();
//      保存AuthCode
        SharedPreferenceUtil.setAuthCode(authCode);
        SharedPreferenceUtil.setAuthId(authId);
        SharedPreferenceUtil.setUserId(userId);
        SharePreferenceUtils.putLong(getApplicationContext(),Constants.USER_ID,userId);
        ReLoadPersonalDataBean dataBean = new ReLoadPersonalDataBean();
        dataBean.setId(userId);
        RxBusUtils.getDefault().post(dataBean);
        RxBusUtils.getDefault().post(new WXloginSuccessBean());

    }


}