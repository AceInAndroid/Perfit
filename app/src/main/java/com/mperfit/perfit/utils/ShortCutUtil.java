package com.mperfit.perfit.utils;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;


import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 创建桌面快捷方式
 * @author Zhang bing
 *
 */
public class ShortCutUtil {
    public static final String ACTION_MY_GAME="com.mperfit.appmall.intent.action.FROM_MY_GAME_SHORTCUT";
    public static final String TAG = "ShortcutUtil";
    private final static String MY_APP ="Perifit";

    /**perfit icon*/
    private final static int SHORTCUT_TYPE_ICON =4;



    /**
     * 为程序创建程序桌面快捷方式<br/>
     * 之前创建过则不创建<br/>
     */
    public static void addApplicationShortcut(Context context) {
        // 此处分开主要是考虑到替换安装可能就没有后面的快捷方式了
        if (!SharedPreferenceUtil.hasCreateShortcut()) {
            if (!isShortCutExist(context, context.getString(R.string.app_name))) {
                addShortcutByPackageName(context, context.getPackageName());
                // MLog.i("ShortCut", "app shortcut not exist");
            } else {
                // MLog.i("ShortCut", "app shortcut  exists");
            }
            SharedPreferenceUtil.setCreateShortCut(true);
        }
    }



    /**
     * 为程序创建重磅推荐方式<br/>
     * 之前创建过则不创建<br/>
     */
    public static void addPerfitShortcut(Context context,int iconId) {
//        if(!ChannelUtil.isShouldGenerateShortCut(context))//部分渠道，不创建快捷方式
//            return;
        // 添加桌面快捷方式
        if (!SharedPreferenceUtil.hasCreateShortcut()) {
            Intent gameIntent = getShortCutIntent(context,SHORTCUT_TYPE_ICON);
            addShortcut(context, MY_APP, gameIntent,iconId, false);
            SharedPreferenceUtil.setCreateShortCut(true);
        }
    }

    /**更新快捷方式<br/>
     * 异步,如果没有快捷方式不会创建新的，需要注意<br/>*/
    public  static void updateMyGameShortcut(final Context context,final Bitmap bitmap){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent gameIntent=getShortCutIntent(context, SHORTCUT_TYPE_ICON);
                updateShortcutIcon(context, MY_APP, gameIntent, bitmap);
            }
        }).start();
    }

    private static Intent getShortCutIntent(Context context, int type) {
        Intent intent;
        intent = new Intent(Intent.ACTION_MAIN);//使用MAIN，可以避免部分手机(比如华为、HTC部分机型)删除应用时无法删除快捷方式的问题
        switch (type) {
            case SHORTCUT_TYPE_ICON:
                //Note that for an IntentFilter to match an Intent, three conditions must hold: the action and category must match
			/*需要在相关的activity添加以下声明
			    <intent-filter>
	            	<action android:name="com.mperfit.appmall.intent.action.FROM_MY_GAME_SHORTCUT" />
	                <action android:name="android.intent.action.VIEW" />
	                <action android:name="android.intent.action.MAIN" />
	    			<category android:name="android.intent.category.LAUNCHER" />
                    <data android:scheme="perfit_shortcut" />
            	</intent-filter>
			 */
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setClass(context, context.getClass());
                intent.setAction(ACTION_MY_GAME);
                intent.setPackage(context.getPackageName());
                intent.putExtra(Constants.EXTRA_FROM, Constants.EXTRA_FROM_SHORTCUT_ICON);
                intent.setData(Uri.parse("perfit_shortcut://perfit"));
                break;
            default:
                break;
        }
        return intent;
    }



    /**
     * @param context
     *            执行者。
     * @params pkg 待添加快捷方式的应用包名，其值不可为null。
     * @return 返回true为正常执行完毕，<br/>
     *         返回false为pkg值为null或者找不到该应用或者该应用无用于Launch的MainActivity 。
     * @author sodino
     * */
    public static boolean addShortcutByPackageName(Context context, String pkg) {
        // 快捷方式名
        String title = "unknown";
        // MainActivity完整名
        String mainAct = null;
        // 应用图标标识
        int iconIdentifier = 0;
        // 根据包名寻找MainActivity
        PackageManager pkgMag = context.getPackageManager();
        Intent queryIntent = new Intent(Intent.ACTION_MAIN, null);
        queryIntent.addCategory(Intent.CATEGORY_LAUNCHER);// 重要，添加后可以进入直接已经打开的页面
        queryIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        queryIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);

        List<ResolveInfo> list = pkgMag.queryIntentActivities(queryIntent,
                PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(pkg)) {
                title = info.loadLabel(pkgMag).toString();
                mainAct = info.activityInfo.name;
                iconIdentifier = info.activityInfo.applicationInfo.icon;
                break;
            }
        }
        if (mainAct == null) {
            // 没有启动类
            return false;
        }
        Intent shortcut = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 不允许重复创建
        shortcut.putExtra("duplicate", false);
        ComponentName comp = new ComponentName(pkg, mainAct);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
                queryIntent.setComponent(comp));
        // 快捷方式的图标
        Context pkgContext = null;
        if (context.getPackageName().equals(pkg)) {
            pkgContext = context;
        } else {
            // 创建第三方应用的上下文环境，为的是能够根据该应用的图标标识符寻找到图标文件。
            try {
                pkgContext = context.createPackageContext(pkg,
                        Context.CONTEXT_IGNORE_SECURITY
                                | Context.CONTEXT_INCLUDE_CODE);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (pkgContext != null) {
            ShortcutIconResource iconRes = Intent.ShortcutIconResource
                    .fromContext(pkgContext, iconIdentifier);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        }
        // 发送广播，让接收者创建快捷方式
        // 需权限<uses-permission
        // android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
        context.sendBroadcast(shortcut);
        return true;
    }

    /**
     * 新建快捷方式到桌面<br/>
     * 请在对应Intent的Activity中添加<br/>
     * <font color="red"> intent-filter action
     * android:name="android.intent.action.MAIN"</font> <br/>
     *
     * @param shortcutName
     *            快捷方式名
     * @param actionIntent
     *            快捷方式操作
     * @param icon
     *            快捷方式图标
     * @param allowRepeat
     *            是否允许重复生成
     *
     *
     */
    public static void addShortcut(Context context, String shortcutName,
                                   Intent actionIntent, ShortcutIconResource icon, boolean allowRepeat) {
        Intent shortcutIntent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", allowRepeat);// 是否允许重复创建
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);// 快捷方式的标题
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);// 快捷方式的图标
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);// 快捷方式的动作
        context.sendBroadcast(shortcutIntent);// 发送广播
    }

    /**
     * 新建快捷方式到桌面<br/>
     * 请在对应Intent的Activity中添加<br/>
     * <font color="red"> intent-filter action
     * android:name="android.intent.action.MAIN"</font> <br/>
     *
     * @param shortcutName
     *            快捷方式名
     * @param actionIntent
     *            快捷方式操作
     * @param iconId
     *            icon
     * @param allowRepeat
     *            是否允许重复生成
     *
     *
     */
    public static void addShortcut(Context context, String shortcutName,
                                   Intent actionIntent, int  iconId, boolean allowRepeat) {
        Intent shortcutIntent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", allowRepeat);// 是否允许重复创建
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);// 快捷方式的标题
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, Intent.ShortcutIconResource.fromContext(context,
                iconId));// 快捷方式的图标
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);// 快捷方式的动作
        context.sendBroadcast(shortcutIntent);// 发送广播
    }

    /**
     * 删除桌面快捷方式 <br/>
     *
     * @param shortcutName
     *            快捷方式名
     * @param actionIntent
     *            快捷方式操作
     * @param isDuplicate
     *            为true时循环删除快捷方式（即存在很多相同的快捷方式）
     *
     *
     */
    public static void deleteShortcut(Context context, String shortcutName,
                                      Intent actionIntent, boolean isDuplicate) {
        Intent shortcutIntent = new Intent(
                "com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);// 快捷方式的标题
        shortcutIntent.putExtra("duplicate", isDuplicate);// 是否循环删除，比如有很多一样的快捷方式
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);// 快捷方式的动作
        context.sendBroadcast(shortcutIntent);// 发送广播
    }

    /**
     * 检查快捷方式是否存在 <br/>
     * <font color=red>注意：</font> 有些手机无法判断是否已经创建过快捷方式<br/>
     * 因此，在创建快捷方式时，请添加<br/>
     * shortcutIntent.putExtra("duplicate", false);// 不允许重复创建<br/>
     * 最好使用{@link #isShortCutExist(Context, String, Intent)}
     * 进行判断，因为可能有些应用生成的快捷方式名称是一样的的<br/>
     * 	此处需要在AndroidManifest.xml中配置相关的桌面权限信息<br/>
     * 错误信息已捕获<br/>
     */
    public static boolean isShortCutExist(Context context, String title) {
        boolean result = false;
        try {
            final ContentResolver cr = context.getContentResolver();
            StringBuilder uriStr = new StringBuilder();
            String authority = LauncherUtil.getAuthorityFromPermissionDefault(context);
            if(authority==null||authority.trim().equals("")){
                authority = LauncherUtil.getAuthorityFromPermission(context,LauncherUtil.getCurrentLauncherPackageName(context)+".permission.READ_SETTINGS");
            }
            uriStr.append("content://");
            if (TextUtils.isEmpty(authority)) {
                int sdkInt = android.os.Build.VERSION.SDK_INT;
                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                    uriStr.append("com.android.launcher.settings");
                } else if (sdkInt < 19) {// Android 4.4以下
                    uriStr.append("com.android.launcher2.settings");
                } else {// 4.4以及以上
                    uriStr.append("com.android.launcher3.settings");
                }
            } else {
                uriStr.append(authority);
            }
            uriStr.append("/favorites?notify=true");
            Uri uri = Uri.parse(uriStr.toString());
            Cursor c = cr.query(uri, new String[] { "title" },
                    "title=? ",
                    new String[] { title }, null);
            if (c != null && c.getCount() > 0) {
                result = true;
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result=false;
        }
        return result;
    }

    /**
     * 不一定所有的手机都有效，因为国内大部分手机的桌面不是系统原生的<br/>
     * 更多请参考{@link #isShortCutExist(Context, String)}<br/>
     * 桌面有两种，系统桌面(ROM自带)与第三方桌面，一般只考虑系统自带<br/>
     * 第三方桌面如果没有实现系统响应的方法是无法判断的，比如GO桌面<br/>
     * 	此处需要在AndroidManifest.xml中配置相关的桌面权限信息<br/>
     * 错误信息已捕获<br/>
     */
    public static boolean isShortCutExist(Context context, String title, Intent intent) {
        boolean result = false;
        try{
            final ContentResolver cr = context.getContentResolver();
            StringBuilder uriStr = new StringBuilder();
            String authority = LauncherUtil.getAuthorityFromPermissionDefault(context);
            if(authority==null||authority.trim().equals("")){
                authority = LauncherUtil.getAuthorityFromPermission(context,LauncherUtil.getCurrentLauncherPackageName(context)+".permission.READ_SETTINGS");
            }
            uriStr.append("content://");
            if (TextUtils.isEmpty(authority)) {
                int sdkInt = android.os.Build.VERSION.SDK_INT;
                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                    uriStr.append("com.android.launcher.settings");
                } else if (sdkInt < 19) {// Android 4.4以下
                    uriStr.append("com.android.launcher2.settings");
                } else {// 4.4以及以上
                    uriStr.append("com.android.launcher3.settings");
                }
            } else {
                uriStr.append(authority);
            }
            uriStr.append("/favorites?notify=true");
            Uri uri = Uri.parse(uriStr.toString());
            Cursor c = cr.query(uri, new String[] { "title", "intent" },
                    "title=?  and intent=?",
                    new String[] { title, intent.toUri(0) }, null);
            if (c != null && c.getCount() > 0) {
                result = true;
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }catch(Exception ex){
            result=false;
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 更新桌面快捷方式图标，不一定所有图标都有效<br/>
     * 如果快捷方式不存在，则不更新<br/>.
     */
    public static void updateShortcutIcon(Context context, String title, Intent intent,Bitmap bitmap) {
        if(bitmap==null){
            Logger.e(TAG, "update shortcut icon,bitmap empty");
            return;
        }
        try{
            final ContentResolver cr = context.getContentResolver();
            StringBuilder uriStr = new StringBuilder();
            String urlTemp="";
            String authority = LauncherUtil.getAuthorityFromPermissionDefault(context);
            if(authority==null||authority.trim().equals("")){
                authority = LauncherUtil.getAuthorityFromPermission(context,LauncherUtil.getCurrentLauncherPackageName(context)+".permission.READ_SETTINGS");
            }
            uriStr.append("content://");
            if (TextUtils.isEmpty(authority)) {
                int sdkInt = android.os.Build.VERSION.SDK_INT;
                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                    uriStr.append("com.android.launcher.settings");
                } else if (sdkInt < 19) {// Android 4.4以下
                    uriStr.append("com.android.launcher2.settings");
                } else {// 4.4以及以上
                    uriStr.append("com.android.launcher3.settings");
                }
            } else {
                uriStr.append(authority);
            }
            urlTemp=uriStr.toString();
            uriStr.append("/favorites?notify=true");
            Uri uri = Uri.parse(uriStr.toString());
            Cursor c = cr.query(uri, new String[] {"_id", "title", "intent" },
                    "title=?  and intent=? ",
                    new String[] { title, intent.toUri(0) }, null);
            int index=-1;
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                index=c.getInt(0);//获得图标索引
                ContentValues cv=new ContentValues();
                cv.put("icon", flattenBitmap(bitmap));
                Uri uri2=Uri.parse(urlTemp+"/favorites/"+index+"?notify=true");
                int i=context.getContentResolver().update(uri2, cv, null,null);
                context.getContentResolver().notifyChange(uri,null);//此处不能用uri2，是个坑
                Logger.i(TAG, "update ok: affected "+i+" rows,index is"+index);
            }else{
                Logger.i(TAG, "update result failed");
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
            Logger.e(TAG, "update shortcut icon,get errors:"+ex.getMessage());
        }
    }


    private static byte[] flattenBitmap(Bitmap bitmap) {
        // Try go guesstimate how much space the icon will take when serialized
        // to avoid unnecessary allocations/copies during the write.
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        ByteArrayOutputStream out = new ByteArrayOutputStream(size);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            Logger.w(TAG, "Could not write icon");
            return null;
        }
    }

}