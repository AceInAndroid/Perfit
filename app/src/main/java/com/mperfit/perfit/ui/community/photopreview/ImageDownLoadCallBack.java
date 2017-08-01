package com.mperfit.perfit.ui.community.photopreview;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by zhangbing on 2017/3/7.
 */

public interface ImageDownLoadCallBack {

    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();

}
