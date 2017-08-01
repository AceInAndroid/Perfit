package com.mperfit.perfit.ui.me.activity.course;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.TouchDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.RefoundComoleteInfoBean;
import com.mperfit.perfit.model.bean.RefoundConfirmBean;
import com.mperfit.perfit.model.bean.RefoundSuccedBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.widget.AmountView;
import com.mperfit.perfit.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2016/12/4.
 */

public class RefoundActivity extends SimpleActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvtoobarTitle;
    @BindView(R.id.tv_refound_title)
    TextView tvTitle;
    @BindView(R.id.av_count)
    AmountView avCount;
    @BindView(R.id.tv_refound_price)
    TextView tvRefoundPrice;
    @BindView(R.id.fl_back)
    FrameLayout flBack;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.divider_title)
    View dividerTitle;
    @BindView(R.id.ll_count)
    LinearLayout llCount;
    @BindView(R.id.divider_count)
    ImageView dividerCount;
    @BindView(R.id.ll_refound_price)
    LinearLayout llRefoundPrice;
    @BindView(R.id.divider_way)
    ImageView dividerWay;
    @BindView(R.id.ll_refound_title)
    LinearLayout llRefoundTitle;
    @BindView(R.id.divider_wayto)
    ImageView dividerWayto;
    @BindView(R.id.ll_waytoorigin)
    LinearLayout llWaytoorigin;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private RetrofitHelper retrofitHelper;
    private long orderId;
    private int count = 1;
    private LoadingDialog loadingDialog;
    private double price;

    @Override
    protected int getLayout() {
        return R.layout.activity_refound;
    }

    @Override
    protected void initEventAndData() {
        tvtoobarTitle.setText("申请退款");
        dividerWayto.setVisibility(View.VISIBLE);
        Intent intent = mContext.getIntent();
        orderId = intent.getLongExtra("orderId", 0);
        loadingDialog = new LoadingDialog(mContext);
        retrofitHelper = new RetrofitHelper(mContext);
        retrofitHelper.fetchRefoundConfirmInfo(orderId)
                .compose(RxUtil.<RefoundConfirmBean>rxSchedulerHelper())
                .subscribe(new Action1<RefoundConfirmBean>() {
                    @Override
                    public void call(RefoundConfirmBean refoundConfirmBean) {
                        if (refoundConfirmBean.getCode() == 100) {
                            RefoundConfirmBean.DataBean.OrderBean orderBean = refoundConfirmBean.getData().getOrder();
                            setData(orderBean);
                        } else {
//                            ToastUtils.showShrotMsg(mContext, refoundConfirmBean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
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
        if (View.class.isInstance(ibBack.getParent())) {
            // 设置视图扩大后的触摸区域
            ((View) ibBack.getParent()).setTouchDelegate(expandedArea);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setData(final RefoundConfirmBean.DataBean.OrderBean orderBean) {

        price = orderBean.getPrice();
        tvRefoundPrice.setText(String.valueOf(price * count) + "元");
        avCount.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {

            @Override
            public void onAmountChange(View view, int amount) {
                count = amount;

                tvRefoundPrice.setText(String.valueOf(price * count) + "元");
            }
        });

        tvTitle.setText(orderBean.getCourse_name());
        avCount.setGoods_storage(orderBean.getList_count());

    }

    @OnClick(R.id.btn_submit)
    void refound() {
        if (loadingDialog != null)
            loadingDialog.show();

        retrofitHelper.fetchRefoundComplete(orderId, count)
                .compose(RxUtil.<RefoundComoleteInfoBean>rxSchedulerHelper())
                .subscribe(new Action1<RefoundComoleteInfoBean>() {
                    @Override
                    public void call(RefoundComoleteInfoBean refoundComoleteInfoBean) {
                        loadingDialog.close();
                        if (refoundComoleteInfoBean.getCode() == 100) {
//                            ToastUtils.showShrotMsg(mContext,"退款成功");
                            RxBus.getDefault().post(new RefoundSuccedBean());
                            Intent intent = new Intent();
                            intent.putExtra("refoundId", String.valueOf(refoundComoleteInfoBean.getData().getRefund().getRefund_id()));
                            intent.setClass(mContext, RefoundDetaiActivity.class);
                            mContext.startActivity(intent);
                            overridePendingTransition(R.anim.right_in,0);
                        } else {
                            if (loadingDialog != null)
                                loadingDialog.close();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (loadingDialog != null)
                            loadingDialog.close();
                    }
                });

    }


}
