package com.mperfit.perfit.ui.login.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.ReLoadPersonalDataBean;
import com.mperfit.perfit.model.bean.WXloginSuccessBean;
import com.mperfit.perfit.presenter.contract.LoginContract;
import com.mperfit.perfit.presenter.presenter.LoginPresenterImpl;
import com.mperfit.perfit.ui.main.activity.MainActivity;
import com.mperfit.perfit.utils.MD5Util;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangbing on 16/10/28.
 */

public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginContract.View {
    @BindView(R.id.tv_forget_pass)
    TextView tvForgetPass;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_weixin_login)
    Button btWeixinLogin;
    @BindView(R.id.go_register)
    TextView tvGoRegister;
    @BindView(R.id.met_id)
    MaterialEditText metId;
    @BindView(R.id.met_password)
    MaterialEditText metPassword;
    @BindView(R.id.fl_close)
    FrameLayout btClose;
    private int type;
    private Intent intent;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {

        initEvent();


    }

    private CompositeSubscription mCompositeSubscription;


    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }


    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }


    private boolean isWXAppInstall() {
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, Constants.WEIXNAPP_ID);
        api.registerApp(Constants.WEIXNAPP_ID);
        boolean isWXAppInstalled = api.isWXAppInstalled() && api.isWXAppSupportAPI();
        return isWXAppInstalled;
    }

    private void initEvent() {
        tvGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, RegisterActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(0, R.anim.slide_left_in);

            }
        });

        btWeixinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isWXAppInstall()) {
                    mPresenter.useWeixinLogin();
                } else {
                    ToastUtils.showLongMsg(mContext, "亲~请安装微信");
                }
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (metId.getText().toString().equals("")) {
                    ToastUtils.showShrotMsg(mContext, "请输入手机号");
                    return;
                } else if (metPassword.getText().toString().equals("")) {
                    ToastUtils.showShrotMsg(mContext, "请输入您的密码");

                    return;
                }
                mPresenter.usePhoneLogin(metId.getText().toString().trim(), MD5Util.encrypt(MD5Util.encrypt(metPassword.getText().toString().trim())), SharedPreferenceUtil.getUmengPush());
            }
        });
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, ForgotPasswordActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);

            }
        });

        intent = mContext.getIntent();
        type = intent.getIntExtra(Constants.LOGIN_TYPE, Constants.LOGIN_FROMCOLLECT);


        Subscription subscribe = RxBusUtils.getDefault().toObservable(WXloginSuccessBean.class)
                .subscribe(new Action1<WXloginSuccessBean>() {
                    @Override
                    public void call(WXloginSuccessBean wXloginSuccessBean) {
                        if (type == Constants.LOGIN_FROMCOLLECT) {
                            finish();
                            overridePendingTransition(0, R.anim.bottom_out);
                        } else {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                            overridePendingTransition(0, R.anim.bottom_out);
                        }

                    }
                });


        addSubscrebe(subscribe);


        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == Constants.LOGIN_FROMCOLLECT) {
                    finish();
                    overridePendingTransition(0, R.anim.bottom_out);
                } else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    overridePendingTransition(0, R.anim.bottom_out);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = mContext.getIntent();
        type = intent.getIntExtra(Constants.LOGIN_TYPE, Constants.LOGIN_FROMCOLLECT);
    }

    @Override
    public void showError(String msg) {
//        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginResult(int type, long userId, String message) {
//        ToastUtils.showShrotMsg(mContext, message);
        ReLoadPersonalDataBean dataBean = new ReLoadPersonalDataBean();
        dataBean.setId(userId);
        SharedPreferenceUtil.setUserId(userId);
        SharePreferenceUtils.putLong(getApplicationContext(),Constants.USER_ID,userId);
        Logger.e("showLoginResult" + userId);
        switch (type) {
            case Constants.REGIST_SUCCESS:
                RxBusUtils.getDefault().post(dataBean);
                break;
            case Constants.REGIST_FILURE:
//                RxBus.getDefault().post(dataBean);
                break;
        }
    }

    /**
     * 登陆成功后调用
     */
    @Override
    public void toHome() {

        if (type == Constants.LOGIN_FROMCOLLECT) {
            finish();
            overridePendingTransition(0, R.anim.slide_right_out);
        } else {
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);
            overridePendingTransition(0, R.anim.slide_left_in);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (type == Constants.LOGIN_FROMCOLLECT) {
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.right_out);

            } else {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        unSubscribe();
        super.onDestroy();
    }
}
