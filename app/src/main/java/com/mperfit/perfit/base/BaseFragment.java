package com.mperfit.perfit.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.di.component.DaggerFragmentComponent;
import com.mperfit.perfit.di.component.FragmentComponent;
import com.mperfit.perfit.di.module.FragmentModule;
import com.mperfit.perfit.utils.SystemUtil;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.weavey.loading.lib.LoadingLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangbing on 2016/10/13.
 * MVP Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment implements BaseView {


    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();


    @Inject
    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    private boolean isFirst = true;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    //setUserVisibleHint(boolean isVisibleToUser)方法会多次回调,而且可能会在onCreateView()方法执行完毕之前回调
    // 如果isVisibleToUser==true,然后进行数据加载和控件数据填充,但是onCreateView()方法并未执行完毕,
    // 此时就会出现NullPointerException空指针异常. 所以给onCreateView 添加标记位
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isUIVisible = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    //注入相应的fragment实例
    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }


    private LoadingLayout loadingLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        initInject();
        isViewCreated =true;
        return mView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
            initEventAndData();
            isFirst = false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //presenter 和view 绑定
        mPresenter.attachView(this);
        mUnBinder = ButterKnife.bind(this, view);
//        lazyLoad();
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
//            initEventAndData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

        }
    }




//    public abstract void loadData();







//    @Override
//    public void onSupportVisible() {
//        super.onSupportVisible();
//        Logger.e("onSupportVisible调用了");
//
//        if (!isHidden()) {
//            initEventAndData();
//            Logger.e("!isHidden调用了");
//
//        }
//    }


//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            initEventAndData();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Fragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

//    /** Fragment当前状态是否可见 */
//    protected boolean isVisible =false;
//
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if(getUserVisibleHint()) {
//            isVisible = true;
//            initEventAndData();
//        } else {
//            isVisible = false;
//        }
//    }


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


    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();
}