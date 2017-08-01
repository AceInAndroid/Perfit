package com.mperfit.perfit.ui.me.activity.course;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.model.bean.ReFoundDetailInfoBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.alert.CustomAlertView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2016/12/4.
 */

public class RefoundDetaiActivity extends SimpleActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvtoobarTitle;
    @BindView(R.id.tv_shenqing)
    TextView tvShenqing;
    @BindView(R.id.tv_shenqingtime)
    TextView tvShenqingtime;
    @BindView(R.id.tv_shenhe)
    TextView tvShenhe;
    @BindView(R.id.tv_successed)
    TextView tvSuccessed;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_way)
    TextView tvWay;
    @BindView(R.id.tv_shenhe_time)
    TextView tvShenheTime;
    @BindView(R.id.tv_success_time)
    TextView tvSuccessTime;
    @BindView(R.id.btn_state1)
    Button btnState1;
    @BindView(R.id.btn_state2)
    Button btnState2;
    @BindView(R.id.btn_state3)
    Button btnState3;
    @BindView(R.id.tv_service)
    TextView tvService;
    private CustomAlertView customAlertView;

    @Override
    protected int getLayout() {
        return R.layout.activity_refound_detail;
    }

    @Override
    protected void initEventAndData() {
        tvtoobarTitle.setText("退款详情");
        Intent intent = mContext.getIntent();
        String refoundId = intent.getStringExtra("refoundId");
        if (TextUtils.isEmpty(refoundId))
            return;
        RetrofitHelper retrofitHelper = new RetrofitHelper(mContext);
        retrofitHelper.fetchRefoudDetail(Long.parseLong(refoundId))
                .compose(RxUtil.<ReFoundDetailInfoBean>rxSchedulerHelper())
                .subscribe(new Action1<ReFoundDetailInfoBean>() {
                    @Override
                    public void call(ReFoundDetailInfoBean reFoundDetailInfoBean) {
                        if (reFoundDetailInfoBean.getCode() == 100) {
                            ReFoundDetailInfoBean.DataBean.RefundBean refund = reFoundDetailInfoBean.getData().getRefund();
                            setData(refund);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        initAlertDialog();
        setBackTouch();
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
                overridePendingTransition(0,R.anim.right_out);

            }
        });
    }


    /**
     * 初始化提示框
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


    private void setData(ReFoundDetailInfoBean.DataBean.RefundBean refund) {
        final String kfPhone = refund.getKf_phone();
        tvService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                customAlertView.baseConfiguration("确定拨打该电话号码？", kfPhone);
                customAlertView.showAlert();
                customAlertView.setRightButtonClickedListener(new CustomAlertView.RightButtonClickedListener() {
                    @Override
                    public void onClickedRightButton(View view) {
                        // 取消
                        customAlertView.dismissAlert();

                        //用intent启动拨打电话
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + kfPhone));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mContext.startActivity(intent);


                    }
                });

            }
        });
        double price = refund.getPrice() * refund.getNumber();
        tvPrice.setText(getString(R.string.renminbi) + price);
        tvCount.setText(refund.getNumber() + "");
        if (refund.getPay_type() == 1) {
            tvWay.setText(R.string.alipay);
        } else {
            tvWay.setText(R.string.weixin);
        }

        btnState1.setBackgroundDrawable(getResources().getDrawable(R.drawable.refund_select));
        tvShenqingtime.setText(SystemUtil.getFormatTime(refund.getCreate_time()));

        btnState2.setBackgroundDrawable(getResources().getDrawable(R.drawable.refund_select));
        tvShenheTime.setText(SystemUtil.getFormatTime(refund.getAudit_time()));

        btnState3.setBackgroundDrawable(getResources().getDrawable(R.drawable.refund_select));
        if (refund.getFinish_time() !=0){
            tvSuccessTime.setText(SystemUtil.getFormatTime(refund.getFinish_time()));
        }


//        switch (refund.getStatus()) {
//            case 1:
//                btnState1.setBackgroundDrawable(getResources().getDrawable(R.drawable.refund_select));
//                tvShenheTime.setText(SystemUtil.getFormatTime(refund.getCreate_time()));
//                break;
//            case 2:
//                btnState2.setBackgroundDrawable(getResources().getDrawable(R.drawable.refund_select));
//                tvShenheTime.setText(SystemUtil.getFormatTime(refund.getAudit_time()));
//                break;
//            case 3:
//                btnState3.setBackgroundDrawable(getResources().getDrawable(R.drawable.refund_select));
//                tvSuccessTime.setText(SystemUtil.getFormatTime(refund.getFinish_time()));
//                break;
//        }

    }


    @OnClick(R.id.tv_service)
    void setService() {

    }




}
