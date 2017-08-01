package com.mperfit.perfit.ui.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.model.bean.HomeDataBean;
import com.mperfit.perfit.presenter.contract.HomeContract;
import com.mperfit.perfit.presenter.presenter.HomePresenter;
import com.mperfit.perfit.ui.article.activity.ArticleActivity;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.classes.activity.ShopDetailActivity;
import com.mperfit.perfit.ui.home.adapter.HomeListAdapter;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.RefreshHeaderView;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, OnRefreshListener,EasyPermissions.PermissionCallbacks {


    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView rvHomeList;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION_PHONE = 1;
    private HomeDataBean.DataBean data = new HomeDataBean.DataBean();
    private HomeListAdapter homeListAdapter;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initEventAndData() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        homeListAdapter = new HomeListAdapter(mContext, data);
        rvHomeList.setLayoutManager(new LinearLayoutManager(mContext));
        rvHomeList.setAdapter(homeListAdapter);
        initEvent(homeListAdapter);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getHomeData();
            }
        });
        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getHomeData();
                }
            });
            return;
        }

//        if (ContextCompat.checkSelfPermission(mContext,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_LOCATION_PHONE);
//        } else {
//            initLocation();
//        }

        methodRequiresTwoPermission();

        //为ReloadButton设置监听
        mPresenter.getHomeData();
    }

    private static final int PERMISSION_LOCATION =0x01;

    @AfterPermissionGranted(PERMISSION_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            // Already have permission, do the thing
            // ...
            initLocation();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Perfit需要定位权限来为您选取距离您最近的商铺位置",
                    PERMISSION_LOCATION, perms);
        }
    }



    private void initEvent(HomeListAdapter homeListAdapter) {
        //跳转文章列表
        homeListAdapter.setGoArticleClickListener(new HomeListAdapter.OnGoArticleClickListener() {
            @Override
            public void onItemClick() {
                Intent intent = new Intent();
                intent.setClass(mContext, ArticleActivity.class);
                mContext.startActivity(intent);
            }
        });
        //课程类别跳转
        homeListAdapter.setOnSortItemClickListener(new HomeListAdapter.OnSortItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                Intent intentToShopDetail = new Intent(mContext, ShopDetailActivity.class);
                intentToShopDetail.putExtra(Constants.COURSE_SELLERID,String.valueOf(data.getSeller_list().get(position).getSeller_id()));
                mContext.startActivity(intentToShopDetail);
                getActivity().overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.getHomeData();
    }


    @Override
    public void showError(String msg) {
        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setRefreshing(false);
        }
        ToastUtils.showShrotMsg(mContext,msg);

        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getHomeData();
                }
            });
            return;
        }
        loadingLayout.setStatus(LoadingLayout.Error);//加载失败
//        SnackbarUtil.showShort(rvHomeList, msg);
    }

    @Override
    public void showContent(final HomeDataBean.DataBean data) {
        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setRefreshing(false);
        }
        if (data == null) {
//            mNoNet.setVisibility(View.VISIBLE);
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        } else {
            if (loadingLayout!=null) {
                loadingLayout.setStatus(LoadingLayout.Success);//加载成功
            }
        }
        this.data = data;
        homeListAdapter.addDailyDate(data);
        initEvent(data);
    }

    private void initEvent(final HomeDataBean.DataBean data) {
        //文章的跳转
        homeListAdapter.setOnItemClickListener(new HomeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, ArticleDetailActivity.class);
                intent.putExtra("id", String.valueOf(data.getArticele_list().get(position).getBiz_id()));
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    ActivityOptions options = null;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "shareView");
//                    }
//                    mContext.startActivity(intent, options.toBundle());
//                } else {
//                }
                mContext.startActivity(intent);


            }
        });
    }



    //=============获取地理位置===================

    private static double latitude;
    private static double longitude;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器 //异步获取定位结果
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //解析定位结果
                    latitude = amapLocation.getLatitude();
                    longitude = amapLocation.getLongitude();
                    App.getInstance().setLatitude(latitude);
                    App.getInstance().setLongitude(longitude);
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Logger.e("AmapError","location Error, ErrCode:" +amapLocation.getErrorCode() + ", errInfo:"+ amapLocation.getErrorInfo());

            }
        }
    };

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置只获取一次
        mLocationOption.setOnceLocation(true);

        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "定位权限已被拒绝，开启定位权限可以帮您找到最近的商铺，请您手动在设置里开启")
                    .setTitle("提示")
                    .setPositiveButton("确定")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(PERMISSION_LOCATION)
                    .build()
                    .show();
        }
    }
}
