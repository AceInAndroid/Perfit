package com.mperfit.perfit.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.TouchDelegate;
import android.view.View;
import android.view.WindowManager;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.di.component.ActivityComponent;
import com.mperfit.perfit.di.component.DaggerActivityComponent;
import com.mperfit.perfit.di.module.ActivityModule;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by zhangbing on 2016/9/28.
 * MVP activity基类 ,
 * 1、将presenter和View进行绑定
 * 2、初试化toolbar
 * 3、初试化dagger
 * 2、注入Activity需要的实例、
 * 4、presenter与activity生命周期绑定防止内存泄露。
 * 5、生命周期管理
 *
 */

public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {

    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;
    private boolean isTransparencyState = true;

    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayout());
        //初试化butterknife,并得到返回的unbinder对象
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        //友盟统计启动
//        PushAgent.getInstance(context).onAppStart();
        //初试化dagger2
        initInject();
        //完成presenter和View的绑定
        if (mPresenter != null)
            mPresenter.attachView(this);
        //把Activity加入管理队列
        App.getInstance().addActivity(this);
        if (isTransparencyState){
//            setStateBar();
        }
        initEventAndData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);

    }


    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressedSupport();
//            }
//        });
    }

    //初试化ActivityComponent ,包括了所有的Activity实例、和activity对应的presenter
    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);//统计时长
//        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //防止Activity退出后 presenter还持有Activity实例,造成内存泄露
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnBinder.unbind();
        unSubscribe();
        //移除Activity
        App.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
//        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }


    public void setBackTouch(View ibBack) {
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
                overridePendingTransition(0, R.anim.slide_right_out);
            }
        });
    }



    private void setStateBar() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
    }

    public boolean getTransparencyState() {
        return isTransparencyState;
    }

    public void setTransparencyState(boolean transparencyState) {
        isTransparencyState = transparencyState;
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


//    //让返回键也执行动画
//    @Override
//    public void onBackPressedSupport() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.right_in,
//                R.anim.right_out);
//    }




    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}