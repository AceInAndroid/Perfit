package com.mperfit.perfit.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mperfit.perfit.component.ActivityLifeCycleEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangbing on 16/10/11.
 * 无MVP的Fragment基类
 */

public abstract class SimpleFragment extends SupportFragment {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    private boolean isInited = false;


    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();



    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (savedInstanceState == null){
            if (!isHidden()) {
                initEventAndData();
            }
        } else {
            initEventAndData();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
//        if (savedInstanceState == null) {
//            if (!isHidden()) {
//                initEventAndData();
//            }
//        } else {
//                initEventAndData();
//
//        }
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!isInited && !hidden) {
//            initEventAndData();
//        }
//    }
//
//    @Override
//    public void onSupportVisible() {
//        super.onSupportVisible();
//        if (!isHidden()){
//            initEventAndData();
//
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("Fragment");
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        unSubscribe();
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutId();
    protected abstract void initEventAndData();
}
