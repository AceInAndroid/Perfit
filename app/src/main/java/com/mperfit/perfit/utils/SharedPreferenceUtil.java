package com.mperfit.perfit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mperfit.perfit.app.App;
import com.mperfit.perfit.app.Constants;


/**
 * Created by zhangbing on 16/8/31.
 */

public class SharedPreferenceUtil {

    private static final boolean DEFAULT_NIGHT_MODE = false;
    private static final boolean DEFAULT_NO_IMAGE = false;
    private static final boolean DEFAULT_AUTO_SAVE = true;
    private static final boolean DEFAULT_LIKE_POINT = false;
    private static final int DEFAULT_CURRENT_ITEM = Constants.TYPE_ZHIHU;

    private static final String SHAREDPREFERENCES_NAME = "perfit_sp";

    public static SharedPreferences getAppSp() {
        return App.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static boolean getNightModeState() {
        return getAppSp().getBoolean(Constants.SP_NIGHT_MODE, DEFAULT_NIGHT_MODE);
    }

    public static void setNightModeState(boolean state) {
        getAppSp().edit().putBoolean(Constants.SP_NIGHT_MODE, state).apply();
    }

    public static boolean getNoImageState() {
        return getAppSp().getBoolean(Constants.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
    }

    public static void setNoImageState(boolean state) {
        getAppSp().edit().putBoolean(Constants.SP_NO_IMAGE, state).apply();
    }

    public static boolean getAutoCacheState() {
        return getAppSp().getBoolean(Constants.SP_AUTO_CACHE, DEFAULT_AUTO_SAVE);
    }

    public static void setAutoCacheState(boolean state) {
        getAppSp().edit().putBoolean(Constants.SP_AUTO_CACHE, state).apply();
    }

    public static int getCurrentItem() {
        return getAppSp().getInt(Constants.SP_CURRENT_ITEM, DEFAULT_CURRENT_ITEM);
    }

    public static void setCurrentItem(int item) {
        getAppSp().edit().putInt(Constants.SP_CURRENT_ITEM, item).apply();
    }

    public static boolean getLikePoint() {
        return getAppSp().getBoolean(Constants.SP_LIKE_POINT, DEFAULT_LIKE_POINT);
    }

    public static void setLikePoint(boolean isFirst) {
        getAppSp().edit().putBoolean(Constants.SP_LIKE_POINT, isFirst).apply();
    }

    public static void setUmengPush(String token){
        getAppSp().edit().putString(Constants.UMENG_PUSH_TOKEN,token).apply();
    }

    public static String getUmengPush(){
        String token = getAppSp().getString(Constants.UMENG_PUSH_TOKEN, Constants.UMENG_PUSH_TOKEN);
        return token;
    }


    public static void setAuthCode(String code){
        getAppSp().edit().putString(Constants.AUTH_CODE,code).apply();
    }


    public static String getAuthCode(){
        return getAppSp().getString(Constants.AUTH_CODE,Constants.AUTH_CODE);
    }


    public static void setAuthId(String id){
         getAppSp().edit().putString(Constants.AUTH_ID,id).apply();
    }


    public static String getAuthid(){
        return getAppSp().getString(Constants.AUTH_ID,Constants.AUTH_ID);
    }


    public static void setUserId(long id){
        getAppSp().edit().putLong(Constants.USER_ID,id).apply();
    }


    public static long getUserid(){
        return getAppSp().getLong(Constants.USER_ID,0);
    }

    /**
     *
     * @param id
     * @return
     */
    public static boolean isMe(long id){
        if (getUserid() ==0){
            return false;
        } else if (getUserid() != id){
            return false;
        } else {
            return true;
        }

    }



    public static boolean hasCreateShortcut() {
        return getAppSp().getBoolean(Constants.SP_SHORTCUT, DEFAULT_LIKE_POINT);
    }

    public static void setCreateShortCut(boolean isFirst) {
        getAppSp().edit().putBoolean(Constants.SP_SHORTCUT, isFirst).apply();
    }




}
