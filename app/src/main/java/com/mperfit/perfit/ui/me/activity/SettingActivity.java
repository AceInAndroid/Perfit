package com.mperfit.perfit.ui.me.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.login.activity.LoginActivity;
import com.mperfit.perfit.ui.main.activity.MainActivity;
import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.widget.ToastUtils;
import com.mperfit.perfit.widget.alert.CustomAlertView;

import butterknife.BindView;

/**
 * Created by zhangbing on 16/11/3.
 */

public class SettingActivity extends SimpleActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.rl_profile)
    LinearLayout rlProfile;
    @BindView(R.id.rl_aboutas)
    LinearLayout rlAboutas;
    @BindView(R.id.rl_sggestion)
    LinearLayout rlSggestion;
    @BindView(R.id.bt_exit)
    Button btExit;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;

    private CustomAlertView customAlertView;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initEventAndData() {
        tvTbTitle.setText("设置");

        initEvent();

        setBackTouch();

        initAlertDialog();
}

    private void setBackTouch() {
        Rect delegateArea = new Rect();
        // Hit rectangle in parent's coordinates
        ibBack.getHitRect(delegateArea);


        // 扩大触摸区域矩阵值
        delegateArea.left -= 200;
        delegateArea.top -= 200;
        delegateArea.right += 200;
        delegateArea.bottom += 200;

        /**
         * 构造扩大后的触摸区域对象
         * 第一个构造参数表示  矩形面积
         * 第二个构造参数表示 被扩大的触摸视图对象
         * <也是本demo最核心用到的类之一>
         */
        TouchDelegate expandedArea = new TouchDelegate(delegateArea, ibBack);
        if(View.class.isInstance(ibBack.getParent())){
            // 设置视图扩大后的触摸区域
            ((View)ibBack.getParent()).setTouchDelegate(expandedArea);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,R.anim.slide_right_out);
            }
        });
    }

    private void initEvent() {


        rlProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext, ProfileActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);

            }
        });

        rlAboutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext, AboutAsAcitivty.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });

        rlSggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext, SuggestionActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertView.baseConfiguration("您确定要退出么？","");
                customAlertView.showAlert();
                customAlertView.setRightButtonClickedListener(new CustomAlertView.RightButtonClickedListener() {
                    @Override
                    public void onClickedRightButton(View view) {
                        // 取消
                        customAlertView.dismissAlert();
                        SharedPreferenceUtil.setAuthId(Constants.AUTH_ID);
                        SharedPreferenceUtil.setAuthCode(Constants.AUTH_CODE);
                        SharedPreferenceUtil.setUserId(0);
                        SharePreferenceUtils.putLong(mContext,Constants.USER_ID,0);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        overridePendingTransition(R.anim.right_in,0);
                    }
                });




            }
        });
    }



    /**
     * 初始化打电话提示框
     */
    private void initAlertDialog() {
        // 初始化
        customAlertView = new CustomAlertView(mContext);
        customAlertView.setLeftButtonClickedListener(new CustomAlertView.LeftButtonClickedListener() {
            @Override
            public void onClickedLeftButton(View view) {
                // 取消
                customAlertView.dismissAlert();
            }
        });
    }




}
