package com.mperfit.perfit.utils;

import android.app.Activity;
import android.graphics.Bitmap;

import com.mperfit.perfit.app.Constants;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.utils.Utility;

/**
 * 微博分享
 * Created by zhangbing on 2017/1/17.
 */

public class WeiboShareUtils {


    private volatile static WeiboShareUtils singleton;
    private IWeiboShareAPI mWeiboShareAPI;

    private Activity activity;

    private WeiboShareUtils(Activity activity) {

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(activity, Constants.WEIBO_KEY);
        mWeiboShareAPI.registerApp();
        this.activity =activity;
    }

    public static WeiboShareUtils getDefault(Activity activity) {
        if (singleton == null) {
            synchronized (WeiboShareUtils.class) {
                if (singleton == null) {
                    singleton = new WeiboShareUtils(activity);
                }
            }
        }
        return singleton;
    }


    public void sendMultiMessage(final String ShareTitle, final String ShareDescription, final String Url, final Bitmap bitmap) {
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        weiboMessage.mediaObject = getWebpageObj(ShareTitle, ShareDescription, thumbBmp, Url);
//         2. 初始化从第三方到微博的消息请求
        TextObject textObject = new TextObject();
        textObject.text = ShareDescription;
        weiboMessage.textObject = textObject;
        weiboMessage.imageObject = getImageObj(bitmap);
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(activity, request);
        thumbBmp.recycle();
    }


    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();

        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */

    private static WebpageObject getWebpageObj(String title, String description, Bitmap bitmap, String url) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = description;
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;
        return mediaObject;
    }


}
