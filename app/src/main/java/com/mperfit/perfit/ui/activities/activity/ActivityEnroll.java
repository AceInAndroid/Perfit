package com.mperfit.perfit.ui.activities.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.EnrollSuccessBean;
import com.mperfit.perfit.presenter.contract.EnrollContract;
import com.mperfit.perfit.presenter.presenter.EnrollPresenterImpl;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.ToastUtil;
import com.mperfit.perfit.widget.CountDownButton;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 报名信息
 * Created by zhangbing on 16/10/26.
 */

public class ActivityEnroll extends BaseActivity<EnrollPresenterImpl> implements EnrollContract.View {
    @BindView(R.id.tv_tb_title)
    TextView tvEnrollInfoTitle;
    @BindView(R.id.met_enroll_name)
    MaterialEditText mName;
    @BindView(R.id.met_enroll_sex)
    MaterialEditText mSex;
    @BindView(R.id.met_enroll_age)
    MaterialEditText mAge;
    @BindView(R.id.met_enroll_phone)
    MaterialEditText mPhoneNum;
    @BindView(R.id.met_enroll_more)
    MaterialEditText mMore;
    @BindView(R.id.met_enroll_identify_code)
    MaterialEditText mIdentifyCode;
    @BindView(R.id.cdb_register_timer)
    CountDownButton mCountDownButton;
    @BindView(R.id.ll_enroll_sex)
    LinearLayout mEnrollSex;
    @BindView(R.id.bt_selectsex)
    Button mSelectSex;
    @BindView(R.id.tv_enroll_date)
    TextView mDate;
    @BindView(R.id.tv_enroll_price)
    TextView mPrice;
    @BindView(R.id.tv_enroll_joyed)
    TextView tvNum;
    @BindView(R.id.tv_enroll_total)
    TextView tvTotalNum;
    @BindView(R.id.tv_enroll_place)
    TextView tvEnrollPlace;
    @BindView(R.id.bt_enroll_confirm)
    Button btConfirm;
    @BindView(R.id.ib_back)
    ImageButton ibBack;

    @BindView(R.id.ll_enroll_all)
    LinearLayout mLlEnrollContainer;


    private boolean isPhoneNumOk = false;
    private long activityId;
    private OptionPicker optionPicker;
    private int mType;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_enroll;
    }

    @Override
    protected void initEventAndData() {
        initDate();
        setInputBackground();
        validatePhoneFormat();  //验证手机格式的方法
        selectSex();
        initEvent();
        tvEnrollInfoTitle.setText("报名参赛");
        setBackTouch(ibBack);

    }



    private void initEvent() {

        if (mType==Constants.ENROLL_TYPE_ACTIVITY){

            btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mName.getText().toString().equals("")) {
                        ToastUtils.showShrotMsg(mContext, "请输入您的名字");
                        mName.requestFocus();
                        return;
                    } else if (mSex.getText().toString().equals("")) {
                        ToastUtils.showShrotMsg(mContext, "请选择性别");
                        optionPicker.show();
                        return;
                    } else if (mPhoneNum.getText().toString().equals("")) {
                        ToastUtils.showShrotMsg(mContext, "请输入手机号让小P联系到你");
                        mPhoneNum.requestFocus();
                        return;
                    }
//                else if (!isPhoneNumOk) {
//                    ToastUtils.showShrotMsg(mContext, "请输入正确的手机号啦~!");
//                    mPhoneNum.requestFocus();
//                    return;
//                }

                    else if (mIdentifyCode.getText().toString().equals("")) {
                        ToastUtils.showShrotMsg(mContext, "请输入验证码");
                        mIdentifyCode.requestFocus();
                        return;
                    } else if (mAge.getText().toString().equals("")) {
                        ToastUtils.showShrotMsg(mContext, "请输入年龄");
                        mAge.requestFocus();
                    } else {
                        int sex = 0;
                        if (optionPicker.getSelectedIndex() == 0) {
                            sex = 1;
                        } else {
                            sex = 2;
                        }
                        mPresenter.putEnrollFormInfo(String.valueOf(activityId), mName.getText().toString(), mPhoneNum.getText().toString(),
                                sex, Integer.valueOf(mAge.getText().toString()), mMore.getText().toString(), mIdentifyCode.getText().toString());
                    }
                }
            });
        } else {
            mLlEnrollContainer.setVisibility(View.GONE);
            btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.putMatchEnrollFormInfo(String.valueOf(activityId));
                }
            });
        }



    }

    private void initDate() {
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(Constants.ENROLL_TITLE);
            double price = intent.getDoubleExtra(Constants.ENROLL_PRICE, 0);
            int count = intent.getIntExtra(Constants.ENROLL_COUNT, 0);
            int totalCount = intent.getIntExtra(Constants.ENROLL_TOTALCOUNT, 0);
            String date = intent.getStringExtra(Constants.ENROLL_DATE);
            String place = intent.getStringExtra(Constants.ENROLL_PLACE);
            activityId = intent.getLongExtra(Constants.ENROLL_ACTIVITYID, 0);
            mType = intent.getIntExtra(Constants.ENROLL_TYPE, Constants.ENROLL_TYPE_ACTIVITY);


            if ((int) price == 0) {
                mPrice.setText(R.string.enroll_free);
            } else {
                mPrice.setText(String.valueOf(price) + "RMB (到店支付)");
            }
            Logger.e("enrollTitle:" + title  + "type" + mType);

            tvEnrollInfoTitle.setText(title);
            mDate.setText(date);
            tvNum.setText(String.valueOf(count));
            tvTotalNum.setText(getString(R.string.xiegang) + String.valueOf(totalCount));
            tvEnrollPlace.setText(place);
        }
    }


    @Override
    public void showActivityInfo() {

    }

    /**
     * 显示验证码发送结果
     */
    @Override
    public void showIdentifyingCode(String msg) {
        ToastUtils.showShrotMsg(mContext, msg);
    }

    /**
     * 显示报名结果
     */
    @Override
    public void showEnrollResult(int type, String msg) {
        switch (type) {
            case Constants.LIKE_RESULT_SINGFILED:
                ToastUtils.showShrotMsg(mContext, msg);
                CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                break;

            case Constants.LIKE_TYPE_FILED:
                ToastUtils.showShrotMsg(mContext, msg);
//                CheckLoginUtil.reLogin(mContext);
                break;

            case Constants.LIKE_RESULT_SUCCESS:
                ToastUtils.showShrotMsg(mContext, msg);
                RxBusUtils.getDefault().postSticky(new EnrollSuccessBean());
                finish();
                break;

            case Constants.LIKE_TYPE_DISABLE:
                //验证码错误
                ToastUtils.showShrotMsg(mContext, msg);
                mIdentifyCode.requestFocus();
                break;
        }

    }

    @Override
    public void showMatchEnrollResult(int type, String msg) {

        switch (type) {
            case Constants.LIKE_RESULT_SINGFILED:
                ToastUtils.showShrotMsg(mContext, msg);
                CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                break;

            case Constants.LIKE_TYPE_FILED:
                ToastUtils.showShrotMsg(mContext, msg);
//                CheckLoginUtil.reLogin(mContext);
                break;

            case Constants.LIKE_RESULT_SUCCESS:
                ToastUtils.showShrotMsg(mContext, msg);
                RxBusUtils.getDefault().postSticky(new EnrollSuccessBean());
                finish();
                break;

            case Constants.LIKE_TYPE_DISABLE:
                //验证码错误
                ToastUtils.showShrotMsg(mContext, msg);
                mIdentifyCode.requestFocus();
                break;
        }


    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShrotMsg(mContext, msg);

    }

    @OnClick(R.id.cdb_register_timer)
    void getCode() {
        if (isPhoneNumOk) {
            //设置验证码按钮可以点击 ,默认不可点击
            mCountDownButton.setEnabled(true);
            mPresenter.getPhoneCode(mPhoneNum.getText().toString().trim(), Constants.JOYINACTIVITY);
        } else {
            ToastUtils.showShrotMsg(mContext, "请输入正确的手机号");
        }
    }

    //选择性别
    private void selectSex() {


        optionPicker = new OptionPicker(mContext, new String[]{"男", "女"});
        optionPicker.setOffset(2);
        optionPicker.setTitleText("请选择性别");
        optionPicker.setTitleTextSize(18);
        optionPicker.setTitleTextColor(getResources().getColor(R.color.activity_text));
        optionPicker.setTopHeight(50);
        optionPicker.setHeight(400);
        optionPicker.setSubmitTextSize(18);
        optionPicker.setCancelVisible(false);
        optionPicker.setTextSize(20);
        optionPicker.setSubmitText("完成");
        optionPicker.setSelectedIndex(1);
        optionPicker.setTextSize(11);
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                mSex.setText(option);
            }

        });


        mSelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                optionPicker.show();

            }
        });
    }


    //设置背景
    private void setInputBackground() {
        mName.setBackgroundDrawable(getResources().getDrawable(R.drawable.enrollinfo_bg));
        mAge.setBackgroundDrawable(getResources().getDrawable(R.drawable.enrollinfo_bg));
        mMore.setBackgroundDrawable(getResources().getDrawable(R.drawable.enrollinfo_bg));
        mPhoneNum.setBackgroundDrawable(getResources().getDrawable(R.drawable.enrollinfo_bg));
        mSex.setBackgroundDrawable(getResources().getDrawable(R.drawable.enrollinfo_bg));
        mIdentifyCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.enrollinfo_bg));
        mCountDownButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.count_down_bg));
    }

    //验证手机格式
    private void validatePhoneFormat() {
        mPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPhoneNumOk = mPhoneNum.validateWith(new RegexpValidator("", "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"));
                if (isPhoneNumOk) {
                    //设置验证码按钮可以点击 ,默认不可点击
                    mCountDownButton.setEnabled(true);
                    mCountDownButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.getPhoneCode(mPhoneNum.getText().toString().trim(), Constants.JOYINACTIVITY);
                        }
                    });
                } else {
//                    ToastUtils.showShrotMsg(mContext, "请输入正确的手机号");
                    mCountDownButton.setEnabled(false);
                }
            }
        });
    }

}
