package com.mperfit.perfit.di.module;

import android.app.Activity;

import com.mperfit.perfit.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * 通过基类,提供Activity的实例module,更畅快的使用dagger吧
 * 绝对让你爱不释手,啦啦啦  阿冰留
 * Created by zhangbing on 16/10/12.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
