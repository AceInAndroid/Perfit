package com.mperfit.perfit.ui.me.activity.course.fragments;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.model.bean.OrderListBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.classes.activity.ClassDetailActivity;
import com.mperfit.perfit.ui.classes.activity.ConfirmOrderActivity;
import com.mperfit.perfit.ui.comment.activity.CommentActivity;
import com.mperfit.perfit.ui.me.activity.course.OrderDetailActivity;
import com.mperfit.perfit.ui.me.activity.course.adapter.AllCourseFragmentAdapter;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.LoadingDialog;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangbing on 2016/12/2.
 */

public class AllCourseFragment extends SimpleFragment implements OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    RecyclerView rvList;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    private RetrofitHelper retrofitHelper;
    private AllCourseFragmentAdapter adapter;
    private int currentPage;
    private OrderListBean.DataBean orderListBeanData;
    private int totalPage;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_course;
    }

    @Override
    protected void initEventAndData() {
        if (!SystemUtil.isNetworkConnected()) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //reload按钮监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    getData();
                }
            });
            return;
        }

        //reload按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                getData();
            }
        });


        //加载布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        retrofitHelper = new RetrofitHelper(mContext);
        getData();
    }

    private void getData() {
        Subscription subscription = retrofitHelper.fetchOrederList(1)
                .compose(RxUtil.<OrderListBean>rxSchedulerHelper())
                .subscribe(new Action1<OrderListBean>() {


                    @Override
                    public void call(OrderListBean orderListBean) {
                        if (orderListBean.getData().getList() == null) {
                            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
                            return;
                        }

                        if (orderListBean.getData().getList().size() == 0) {
                            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
                            return;
                        }

                        if (loadingLayout != null) {
                            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
                        }

                        final List<OrderListBean.DataBean.ListBean> listBeen = orderListBean.getData().getList();
                        orderListBeanData = orderListBean.getData();
                        setData(orderListBean, listBeen);
                        totalPage = orderListBeanData.getTotal_page();
                        currentPage = 2;

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingLayout.setStatus(LoadingLayout.Success);//加载成功
                        ToastUtils.showShrotMsg(mContext, throwable.getMessage());
                    }
                });

        addSubscrebe(subscription);
    }


    protected CompositeSubscription mCompositeSubscription;

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



    private void setData(final OrderListBean orderListBean, final List<OrderListBean.DataBean.ListBean> listBeen) {
        if (orderListBean.getCode() == 100 && rvList !=null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            rvList.setLayoutManager(linearLayoutManager);
            rvList.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 1,
                    getResources().getColor(R.color.divider_gray)));
            adapter = new AllCourseFragmentAdapter(mContext, listBeen);
            rvList.setAdapter(adapter);
            adapter.setOnItemClickListener(new AllCourseFragmentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("order_id", listBeen.get(position).getOrder_id());
                    mContext.startActivity(intent);
                }

                @Override
                public void onGoPay(int position) {
                    Intent intent = new Intent();
                    OrderListBean.DataBean.ListBean dataBean = listBeen.get(position);
                    intent.setClass(mContext, ConfirmOrderActivity.class);
                    intent.putExtra("name", listBeen.get(position).getCourse_name());
                    intent.putExtra("price", dataBean.getPrice());
                    intent.putExtra("payprice", dataBean.getPay_price());
                    intent.putExtra("num", dataBean.getNumber());
                    intent.putExtra("orderid", dataBean.getOrder_id());
                    mContext.startActivity(intent);
                }

                @Override
                public void onGoComment(int position) {
                    Intent intent = new Intent(mContext, CommentActivity.class);
                    intent.putExtra("order_id", orderListBean.getData().getList().get(position).getOrder_id());
                    mContext.startActivity(intent);


                }

                @Override
                public void onToCourseDetail(int position) {
                    Intent intent = new Intent(mContext, ClassDetailActivity.class);
                    intent.putExtra(Constants.COURSE_ID, String.valueOf(orderListBean.getData().getList().get(position).getCourse_id()));
                    mContext.startActivity(intent);

                }
            });
        } else {
            ToastUtils.showShrotMsg(mContext, orderListBean.getMessage());
        }
    }

    @Override
    public void onLoadMore() {
        if (currentPage <= totalPage) {
            retrofitHelper.fetchOrederList(currentPage)
                    .compose(RxUtil.<OrderListBean>rxSchedulerHelper())
                    .subscribe(new Action1<OrderListBean>() {
                        @Override
                        public void call(OrderListBean orderListBean) {
                            if (orderListBean.getCode() == 100) {
                                swipeToLoadLayout.setLoadingMore(false);
                                adapter.addMoreData(orderListBean.getData().getList());
                                currentPage = orderListBean.getData().getCurrent_page();

                                currentPage++;
                            } else {
                                swipeToLoadLayout.setLoadingMore(false);
                                ToastUtils.showShrotMsg(mContext, orderListBean.getMessage());

                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            swipeToLoadLayout.setLoadingMore(false);

                        }
                    });
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }
}
