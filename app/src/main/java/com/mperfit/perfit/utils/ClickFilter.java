package com.mperfit.perfit.utils;

/**
 * Created by zhangbing on 2016/12/4.
 */

public class ClickFilter {
    public static final long INTERVAL = 2000; //防止连续点击的时间间隔
    private static long lastClickTime = 0L; //上一次点击的时间

    public static boolean filter() {
        long time = System.currentTimeMillis();
        if ( ( time - lastClickTime ) > INTERVAL ) {
            lastClickTime = time;
            return false;
        }
        return true;
    }
}
