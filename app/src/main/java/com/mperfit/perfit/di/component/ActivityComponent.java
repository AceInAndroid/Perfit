package com.mperfit.perfit.di.component;

import android.app.Activity;

import com.mperfit.perfit.di.ActivityScope;
import com.mperfit.perfit.di.module.ActivityModule;
import com.mperfit.perfit.ui.activities.activity.ActivityEnroll;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.classes.activity.ClassDetailActivity;
import com.mperfit.perfit.ui.classes.activity.ShopDetailActivity;
import com.mperfit.perfit.ui.login.activity.ForgotPasswordActivity;
import com.mperfit.perfit.ui.login.activity.LoginActivity;
import com.mperfit.perfit.ui.login.activity.RegisterActivity;
import com.mperfit.perfit.ui.me.activity.MyJoinActivity;
import com.mperfit.perfit.ui.me.activity.ProfileActivity;
import com.mperfit.perfit.ui.me.activity.articlecollect.ArticleCollectActivity;
import com.mperfit.perfit.ui.me.activity.shop.ShopCollectActivity;
import com.mperfit.perfit.ui.otherspersonal.activity.OthersPersonalActivity;
import com.mperfit.perfit.ui.personal.activity.MyActivityActivity;
import com.mperfit.perfit.ui.personal.activity.MyContentActivity;
import com.mperfit.perfit.ui.personal.activity.MyGameActivity;
import com.mperfit.perfit.ui.personal.activity.MySelfPersonalCenterActivity;
import com.mperfit.perfit.ui.postdetailactiviy.activity.PostDetaillActivity;

import dagger.Component;

/**
 * Created by zhangbing on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
    void inject(ArticleDetailActivity articleDetailActivity);
    void inject(ActivityEnroll activityEnroll);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(ForgotPasswordActivity forgotPassword);
    void inject(ProfileActivity profileActivity);
    void inject(MyJoinActivity myJoinActivity);
    void inject(ClassDetailActivity classDetailActivity);
    void inject(ShopDetailActivity shopDetailActivity);
    void inject(ArticleCollectActivity articleCollectActivity);
    void inject(ShopCollectActivity shopCollectActivity);
    void inject(OthersPersonalActivity othersPersonalActivity);
    void inject(PostDetaillActivity postDetaillActivity);
    void inject(MySelfPersonalCenterActivity postDetaillActivity);
    void inject(MyContentActivity myContentActivity);
    void inject(MyGameActivity myContentActivity);
    void inject(MyActivityActivity myActivityActivity);


}
