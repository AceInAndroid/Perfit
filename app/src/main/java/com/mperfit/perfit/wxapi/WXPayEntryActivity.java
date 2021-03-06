package com.mperfit.perfit.wxapi;

import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.CourseOrderCompletedBean;
import com.mperfit.perfit.model.bean.WexinPaysucessBean;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import rx.Subscription;
import rx.functions.Action1;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.WEIXNAPP_ID);
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
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();

                    RxBus.getDefault().post(new WexinPaysucessBean());
                    Logger.e("1、支付成功，weixinpay调用完成 发送消息");
                    break;
                case -1:
                    Toast.makeText(this, "支付失败，请检查", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(this, "已取消支付", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "支付失败，请检查", Toast.LENGTH_SHORT).show();
                    break;
            }
            finish();

        }
    }
}