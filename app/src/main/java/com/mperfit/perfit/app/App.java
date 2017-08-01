package com.mperfit.perfit.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.bumptech.glide.request.target.ViewTarget;
import com.mperfit.perfit.R;
import com.mperfit.perfit.di.component.AppComponent;
import com.mperfit.perfit.di.component.DaggerAppComponent;
import com.mperfit.perfit.di.module.AppModule;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.weavey.loading.lib.LoadingLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhangbing on 2016/10/12.
 */
public class App extends Application {

    private static App instance;
    private Set<Activity> allActivities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;
    public static IWXAPI api;

    private static double latitude;
    private static double longitude;
    private IWeiboShareAPI mWeiboShareAPI;


    public static synchronized App getInstance() {
        return instance;
    }

//    static {
//        AppCompatDelegate.setDefaultNightMode(
//                AppCompatDelegate.MODE_NIGHT_NO);
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        String currentProcessName = getCurrentProcessName(instance);
        if (currentProcessName.equals("com.mperfit.perfit:webview")) {
//            iniUmengShare();
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.WEIBO_KEY);
            mWeiboShareAPI.registerApp(); // 将应用注册到微博客户端

            //初始化tbs x5 webview
            QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {

                }

                @Override
                public void onViewInitFinished(boolean b) {
                }
            });
            return;
        } else if (currentProcessName.equals("com.mperfit.perfit:mapview")) {
            return;
        } else if (currentProcessName.equals("com.mperfit.perfit:releasepreviewactivity")){
            return;
        }

//        //初始化友盟分享
//        iniUmengShare();

        //初始化屏幕宽高
        getScreenSize();

        //初始化日志
        Logger.init("zhangbing").hideThreadInfo().logLevel(LogLevel.FULL);

//        LeakCanary.install(this);


//        //初始化错误收集
//        CrashHandler.init(new CrashHandler(getApplicationContext()));

/*        //初始化内存泄漏检测
        LeakCanary.install(this);

        //初始化过度绘制检测
        BlockCanary.install(this, new AppBlockCanaryContext()).start();*/

//        初试化友盟推送
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token,保存
//                SharedPreferenceUtil.setUmengPush(deviceToken);
//                Logger.e(deviceToken);
//            }
//            @Override
//            public void onFailure(String s, String s1) {
//                Logger.e("erro:" + s + "erro + "  + s1);
//            }
//        });
//
//
//        //注册成功会返回device token,保存
//        String device_token = mPushAgent.getRegistrationId();
//        Logger.e("友盟device token："+device_token);


        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("网络异常，请检查网络设置...")
                .setErrorImage(R.drawable.logo)
                .setEmptyImage(R.drawable.logo)
                .setNoNetworkImage(R.drawable.logo)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150, 40);


        //初试化微信登录
        api = WXAPIFactory.createWXAPI(instance, Constants.WEIXNAPP_ID, true);
        api.registerApp(Constants.WEIXNAPP_ID);

        //初始化tbs x5 webview
//        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
//            @Override
//            public void onCoreInitFinished() {
//
//            }
//
//            @Override
//            public void onViewInitFinished(boolean b) {
//            }
//        });


        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Log.e("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("app","onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("app","onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("app","onDownloadProgress:"+i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(),  cb);
        //注册微博分享

        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.WEIBO_KEY);
        mWeiboShareAPI.registerApp(); // 将应用注册到微博客户端

        //设置这个 防止在gridview 和listview 报错 具体原因跟gilde加载缓存机制有关系 不要去掉哦
        ViewTarget.setTagId(R.id.glide_tag);


    }


    public static void setLatitude(double latitude) {
        App.latitude = latitude;
    }

    public static void setLongitude(double longitude) {
        App.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }


    private String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    //添加activity到队列
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        if (act == null){
            return;
        }
        allActivities.add(act);
    }

    //移除activity
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    //退出时清除所有activity
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 获取屏幕宽度DPI
     */
    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
