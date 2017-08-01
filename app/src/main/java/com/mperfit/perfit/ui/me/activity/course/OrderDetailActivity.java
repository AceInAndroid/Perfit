package com.mperfit.perfit.ui.me.activity.course;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.CommentSuccessBean;
import com.mperfit.perfit.model.bean.OrderDetailBean;
import com.mperfit.perfit.model.bean.RefoundSuccedBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.classes.activity.ClassDetailActivity;
import com.mperfit.perfit.ui.classes.activity.ConfirmOrderActivity;
import com.mperfit.perfit.ui.classes.activity.MapViewActivity;
import com.mperfit.perfit.ui.classes.activity.ShopDetailActivity;
import com.mperfit.perfit.ui.comment.activity.CommentActivity;
import com.mperfit.perfit.ui.me.activity.course.adapter.OrderDetaiCodeListAdapter;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadingDialog;
import com.mperfit.perfit.widget.MyRatingBar;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.ToastUtils;
import com.mperfit.perfit.widget.alert.CustomAlertView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangbing on 2016/12/3.
 */

public class OrderDetailActivity extends SimpleActivity {

    @BindView(R.id.fl_back)
    FrameLayout flBack;
    @BindView(R.id.tv_title)
    TextView tvToolBarTitle;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.tv_times)
    TextView tvtimes;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_code_count)
    TextView tvCodeCount;
    @BindView(R.id.bt_refund)
    Button btRefund;
    @BindView(R.id.ntrl_list)
    RecyclerView ntrlList;
    @BindView(R.id.tv_class_shoptitle)
    TextView tvClassShoptitle;
    @BindView(R.id.mrb_class_detail_ratingbar)
    MyRatingBar mrbClassDetailRatingbar;
    @BindView(R.id.tv_totalscore)
    TextView tvTotalscore;
    @BindView(R.id.iv_classdetail_location)
    ImageView ivClassdetailLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.ll_address_container)
    LinearLayout llAddressContainer;
    @BindView(R.id.iv_classdetail_phone)
    ImageView ivClassdetailPhone;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_ordernumber)
    TextView tvOrdernumber;
    @BindView(R.id.tv_time)
    TextView tvConfirmOrderTime;
    @BindView(R.id.tv_detai_count)
    TextView tvDetaiCount;
    @BindView(R.id.tv_totalprice)
    TextView tvTotalprice;
    @BindView(R.id.ll_allrefoud)
    LinearLayout allRefound;
    @BindView(R.id.ll_to_course)
    LinearLayout llToCourseDetail;
    @BindView(R.id.ll_tuangou_container)
    LinearLayout lltuangou;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlShopTitleContainer;

    private OrderDetailBean.DataBean.OrderBean orderBean;
    private RetrofitHelper retrofitHelper;
    private int status;
    private CustomAlertView customAlertView;
    private CompositeSubscription mCompositeSubscription;
    private LoadingDialog loadingDialog;


    @Override
    protected int getLayout() {
        return R.layout.activity_order_detai;
    }

    @Override
    protected void initEventAndData() {
        tvToolBarTitle.setText("订单详情");
        Intent intent = mContext.getIntent();
        final long orderId = intent.getLongExtra("order_id", 0);
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        Subscription subscription = RxBus.getDefault().toObservable(RefoundSuccedBean.class)
                .subscribe(new Action1<RefoundSuccedBean>() {
                    @Override
                    public void call(RefoundSuccedBean refoundSuccedBean) {
                        getData(orderId);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscrebe(subscription);


//        Subscription subscription1 = RxBusUtils.getDefault().toObservableSticky(CommentSuccessBean.class)
//                .subscribe(new Action1<CommentSuccessBean>() {
//                    @Override
//                    public void call(CommentSuccessBean commentSuccessBean) {
//                        btRefund.setVisibility(View.GONE);
//                        allRefound.setVisibility(View.GONE);
//                        btnSubmit.setVisibility(View.GONE);
//                    }
//                });
//
//        addSubscrebe(subscription1);

        retrofitHelper = new RetrofitHelper(mContext);
        getData(orderId);
        initAlertDialog();
        setBackTouch();
        loadingDialog = new LoadingDialog(mContext);
        loadingDialog.show();
    }


    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
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


    private void getData(long orderId) {
        Subscription subscribe = retrofitHelper.fetchOrderDetail(orderId)
                .compose(RxUtil.<OrderDetailBean>rxSchedulerHelper())
                .subscribe(new Action1<OrderDetailBean>() {
                    @Override
                    public void call(OrderDetailBean orderDetailBean) {
                        loadingDialog.close();
                        if (orderDetailBean.getCode() == 100) {
                            setData(orderDetailBean);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.close();
                        ToastUtils.showShrotMsg(mContext,"亲抱歉~出了点小错误");
                    }
                });

        mCompositeSubscription.add(subscribe);
    }

    private void setData(final OrderDetailBean orderDetailBean) {


        orderBean = orderDetailBean.getData().getOrder();

        // status 订单状态（1待付款  2已付款  3待评价(已消费) 4已评价 5 已退款（已过期））
        status = orderBean.getStatus();
        if (orderBean.getStatus() ==5 ){
            allRefound.setVisibility(View.VISIBLE);
            btRefund.setVisibility(View.GONE);
            allRefound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //去退款的监听
                    Intent intent = new Intent();
                    intent.putExtra("refoundId",orderDetailBean.getData().getOrder().getRefund_id());
                    Logger.e("orderDerailBean:=" + orderDetailBean.getData().getOrder().getRefund_id());
                    intent.setClass(mContext,RefoundDetaiActivity.class);
                    mContext.startActivity(intent);
                    overridePendingTransition(R.anim.right_in,R.anim.slide_right_out);

                }
            });
        } else if (orderBean.getStatus() ==1){
            btRefund.setVisibility(View.GONE);
            allRefound.setVisibility(View.GONE);
            lltuangou.setVisibility(View.GONE);
            btnSubmit.setText("去付款");
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentToPay = new Intent();
                    intentToPay.setClass(mContext, ConfirmOrderActivity.class);
                    intentToPay.putExtra("name", orderBean.getCourse_name());
                    intentToPay.putExtra("price", orderBean.getPrice());
                    intentToPay.putExtra("payprice", orderBean.getPay_price());
                    intentToPay.putExtra("num", orderBean.getNumber());
                    intentToPay.putExtra("orderid", orderBean.getOrder_id());
                    mContext.startActivity(intentToPay);
                    overridePendingTransition(R.anim.right_in,R.anim.slide_right_out);


                }
            });
            btnSubmit.setVisibility(View.VISIBLE);


        } else if (orderBean.getStatus() ==3){
            btRefund.setVisibility(View.GONE);
            allRefound.setVisibility(View.GONE);
            btnSubmit.setText("去评价");
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentToComment = new Intent();
                    intentToComment.setClass(mContext, CommentActivity.class);
                    intentToComment.putExtra("order_id",orderBean.getOrder_id());
                    mContext.startActivity(intentToComment);
                    overridePendingTransition(R.anim.right_in,R.anim.slide_right_out);


                }
            });
            btnSubmit.setVisibility(View.VISIBLE);
        } else if (orderBean.getStatus() ==4){
            //4已评价
            btRefund.setVisibility(View.GONE);
            allRefound.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);

        } else {
            btnSubmit.setVisibility(View.GONE);
            allRefound.setVisibility(View.GONE);
            btRefund.setVisibility(View.VISIBLE);

        }
        ImageLoader.load(mContext,orderBean.getCourse_img_url(),ivImage);
        ntrlList.setLayoutManager(new LinearLayoutManager(mContext));
        ntrlList.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.divider_gray)));
        OrderDetaiCodeListAdapter listAdapter = new OrderDetaiCodeListAdapter(mContext,
                orderDetailBean.getData().getQd_list(),status);
        ntrlList.setAdapter(listAdapter);
        tvTitles.setText(orderDetailBean.getData().getOrder().getCourse_name());
        tvtimes.setText(orderBean.getBegin_time() + "-" + orderBean.getEnd_time());
        tvPrice.setText(  orderBean.getPrice()+ "元/位");
        tvCodeCount.setText("(" + orderBean.getNumber() + ")");
        tvClassShoptitle.setText(orderBean.getSeller_name());
        mrbClassDetailRatingbar.setStarRating(Float.valueOf(orderBean.getScore()));
        tvTotalscore.setText("("+orderBean.getScore()+"分)");
        tvLocation.setText(orderBean.getSpecific_address());
        tvPhone.setText(orderBean.getPhone());
        tvOrdernumber.setText(orderBean.getCode());
        tvConfirmOrderTime.setText(SystemUtil.getFormatTime(orderBean.getOrder_create_time()));
        tvDetaiCount.setText(orderBean.getNumber() + "");
        tvTotalprice.setText("¥" + orderBean.getPay_price());
        listAdapter.setOnItemClickListener(new OrderDetaiCodeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //去退款的监听
                Intent intent = new Intent();
                intent.putExtra("refoundId",String.valueOf(orderDetailBean.getData().getQd_list().get(position).getOrder_refund_id()));
                intent.setClass(mContext,RefoundDetaiActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.slide_right_out);


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

    @OnClick(R.id.bt_refund)
    void reFund() {
        if (orderBean ==null){
            ToastUtils.showShrotMsg(mContext,"出了点小错误~请稍后重试");
            return;
        }
        Intent intent = new Intent(mContext, RefoundActivity.class);
        intent.putExtra("orderId", orderBean.getOrder_id());
        startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.slide_right_out);
    }

    @OnClick(R.id.ll_to_course)
    void toCourseDetai(){
        if (orderBean==null){
            return;
        }
        Intent intent = new Intent(mContext, ClassDetailActivity.class);
        intent.putExtra(Constants.COURSE_ID,String.valueOf(orderBean.getCourse_id()));
        mContext.startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.slide_right_out);

    }


    @OnClick(R.id.ll_address_container)
    void toMapView(){
        Intent intentToMap = new Intent(mContext, MapViewActivity.class);
        intentToMap.putExtra("name", orderBean.getSeller_name());
        intentToMap.putExtra("Latitude", orderBean.getLatitude());
        intentToMap.putExtra("Longitude", orderBean.getLongitude());
        intentToMap.putExtra("address", orderBean.getSpecific_address());
        intentToMap.putExtra("time", orderBean.getBusiness_hours());
        mContext.startActivity(intentToMap);
        overridePendingTransition(R.anim.right_in,R.anim.slide_right_out);

    }

    @OnClick(R.id.rl_title)
    void goShopDetail(){
        Intent intent = new Intent();
        intent.setClass(mContext, ShopDetailActivity.class);
        intent.putExtra(Constants.COURSE_SELLERID, String.valueOf(orderBean.getSeller_id()));
        mContext.startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @OnClick(R.id.ll_phone)
    void dailPhone() {
        final String number = tvPhone.getText().toString();
        customAlertView.baseConfiguration("确定拨打该电话号码？", number);
        customAlertView.showAlert();
        customAlertView.setRightButtonClickedListener(new CustomAlertView.RightButtonClickedListener() {
            @Override
            public void onClickedRightButton(View view) {
                // 取消
                customAlertView.dismissAlert();


                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(mContext,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    callPhone(number);
                }
            }
        });


    }

    private void callPhone(String number) {

        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
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

    @Override
    protected void onDestroy() {
        if (mCompositeSubscription!=null){
            unSubscribe();
        }
        super.onDestroy();

    }
}
