package com.mperfit.perfit.di.component;


import com.mperfit.perfit.app.App;
import com.mperfit.perfit.di.ContextLife;
import com.mperfit.perfit.di.module.AppModule;
import com.mperfit.perfit.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 * Created by zhangbing on 16/10/12.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

//    RealmHelper realmHelper();    //提供数据库帮助类

}
