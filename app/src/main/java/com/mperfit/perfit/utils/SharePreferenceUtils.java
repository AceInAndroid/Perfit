package com.mperfit.perfit.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 专门访问和设置SharePreference的工具类, 保存和配置一些设置信息
 * 
 * @author Zhangbing
 * 
 */
public class SharePreferenceUtils {

	private static final String SHARE_PREFS_NAME = "zhangbing";
	private static SharedPreferences mSharedPreferences;

	public static void putBoolean(Context ctx, String key, boolean value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context ctx, String key,
			boolean defaultValue) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		return mSharedPreferences.getBoolean(key, defaultValue);
	}

	public static void putString(Context ctx, String key, String value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		mSharedPreferences.edit().putString(key, value).apply();
	}

	public static String getString(Context ctx, String key, String defaultValue) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		return mSharedPreferences.getString(key, defaultValue);
	}

	public static void putLong(Context ctx, String key, long value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		mSharedPreferences.edit().putLong(key, value).apply();
	}


	public static long getLong(Context ctx, String key,
							   long defaultValue) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		return mSharedPreferences.getLong(key, defaultValue);
	}


}
