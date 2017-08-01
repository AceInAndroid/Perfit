package com.mperfit.perfit.utils;

import android.content.Context;
import android.content.Intent;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.ui.login.activity.LoginActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by zhangbing on 16/11/1.
 */

public class CheckLoginUtil {


    //Constants.LOGIN_FROMCOLLECT
    //Constants.LOGIN_FROMLOGIN
    public static boolean isLogin(Context context,int formType){

        long selfId = SharePreferenceUtils.getLong(context.getApplicationContext(), Constants.USER_ID,0);
        if (selfId == 0){
            //重新登录
            reLogin(context,formType);
            return false;
        } else {
            return true;
        }


    }

    //ture 是没登录
    public static boolean CheckLogin(Context context) {
        long selfId = SharePreferenceUtils.getLong(context.getApplicationContext(), Constants.USER_ID,0);
        return selfId == 0;
    }





    //1、场馆收藏来的 2、登录页面来的
    public static void reLogin(Context context, int type) {
        switch (type) {
            case Constants.LOGIN_FROMCOLLECT:
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra(Constants.LOGIN_TYPE, Constants.LOGIN_FROMCOLLECT);
                context.startActivity(intent);
                break;
            case Constants.LOGIN_FROMLOGIN:
                Intent intentfromlogin = new Intent(context, LoginActivity.class);
                intentfromlogin.putExtra(Constants.LOGIN_TYPE, Constants.LOGIN_FROMLOGIN);
                context.startActivity(intentfromlogin);
                break;

        }

    }


//    public static boolean CheckIdentify(int code) {
//        switch (code) {
//            case 100:
//                break;
//        }
//    }
}
