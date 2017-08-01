package com.mperfit.perfit.ui.login.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.presenter.contract.ForgotPasswordContract;
import com.mperfit.perfit.presenter.presenter.ForgotPasswordPresenterImpl;
import com.mperfit.perfit.utils.MD5Util;
import com.mperfit.perfit.widget.CountDownButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import butterknife.BindView;

/**
 *
 * Created by zhangbing on 16/10/28.
 */

public class ForgotPasswordActivity extends BaseActivity<ForgotPasswordPresenterImpl> implements ForgotPasswordContract.View {
    @BindView(R.id.met_id)
    MaterialEditText mId;
    @BindView(R.id.met_vailidate_code)
    MaterialEditText mVailidateCode;
    @BindView(R.id.met_password)
    MaterialEditText mPassword;
    @BindView(R.id.cdb_register_timer)
    CountDownButton mTimer;
    @BindView(R.id.bt_regist)
    Button mBtRegist;
    @BindView(R.id.iv_close)
    ImageView mClose;
    private boolean isPhoneNum = false;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forgotpass;
    }

    @Override
    protected void initEventAndData() {
        checkPhoneNum();
        regist();
        initEvent();
    }


    private void initEvent() {
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void regist() {
        mBtRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mId.getText().toString().equals("")) {
                    Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isPhoneNum) {
                    Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else if (mPassword.getText().toString().equals("")) {
                    Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mVailidateCode.getText().toString().equals("")) {
                    Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mPresenter.goFindPass(mId.getText().toString().trim(), MD5Util.encrypt(MD5Util.encrypt(mPassword.getText().toString().trim())),
                            mVailidateCode.getText().toString().trim());

                    Log.e("zhangbing",MD5Util.encrypt(MD5Util.encrypt(mPassword.getText().toString().trim())));
                    Log.e("zhangbing","没加密前"+mPassword.getText().toString().trim());
                }
            }
        });
    }

    /**
     * 验证手机号格式
     * 获取验证码
     */
    private void checkPhoneNum() {
        mId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPhoneNum = mId.validateWith(new RegexpValidator("请输入正确的手机号", "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"));
                if (isPhoneNum) {
                    //设置验证码按钮可以点击 ,默认不可点击
                    mTimer.setEnabled(true);
                    mTimer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.getInvalidateCode(mId.getText().toString().trim(), Constants.FORGOTPASS);
                        }
                    });
                } else {
                    mTimer.setEnabled(false);
                }
            }
        });
    }



    @Override
    public void showError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void showFindResult(int type, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGetCodeResult(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }
}
