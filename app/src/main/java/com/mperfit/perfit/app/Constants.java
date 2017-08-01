package com.mperfit.perfit.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by zhangbing on 2016/8/3.
 */
public class Constants {

    //================= TYPE ====================

    public static final int TYPE_ZHIHU = 101;

    public static final int TYPE_ANDROID = 102;

    public static final int TYPE_IOS = 103;

    public static final int TYPE_WEB = 104;

    public static final int TYPE_GIRL = 105;

    public static final int TYPE_WECHAT = 106;

    public static final int TYPE_GANK = 107;

    public static final int TYPE_SETTING = 108;

    public static final int TYPE_LIKE = 109;

    public static final int TYPE_ABOUT = 110;

    public static final int TYPE_ACTIVITY = 111;
    public static final int TYPE_MATCH = 113;
    public static final int TYPE_ARTICLE = 112;
    public static final int TYPE_FROM_CLASSDETAIL = 115;
    public static final int TYPE_FROM_MYOURDER = 116;


    //================= KEY ====================

//    public static final String KEY_API = "f95283476506aa756559dd28a56f0c0b"; //需要APIKEY请去 http://apistore.baidu.com/ 申请,复用会减少访问可用次数
    public static final String KEY_API = "52b7ec3471ac3bec6846577e79f20e4c"; //需要APIKEY请去 http://www.tianapi.com/#wxnew 申请,复用会减少访问可用次数
    public static final String WEIXNAPP_ID = "wx11ef503bfa72cb61";

    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "zhangbing" + File.separator + "perifit";

    //================= UMENG ====================

    public static final String EVENT_TAB_HOME = "tab_home";

    //================= PREFERENCE ====================

    public static final String SP_NIGHT_MODE = "night_mode";

    public static final String SP_NO_IMAGE = "no_image";

    public static final String SP_AUTO_CACHE = "auto_cache";

    public static final String SP_CURRENT_ITEM = "current_item";

    public static final String SP_LIKE_POINT = "like_point";

    public static final String UMENG_PUSH_TOKEN = "umeng_push_token";

    public static final String AUTH_CODE = "auth_code";

    public static final String AUTH_ID = "1";

    //发送验证码 : 1 注册短信 2密码找回短信 3活动报名短信
    public static final int REGIST_CODE = 1;

    public static final int FORGOTPASS = 2;

    public static final int JOYINACTIVITY = 3;

    //================= ENROLL ====================
    public static final String ENROLL_TYPE = "ENROlL_TYPE";
    public static final int ENROLL_TYPE_MATCH = 0x302;
    public static final int ENROLL_TYPE_ACTIVITY = 0x303;

    public static final String ENROLL_TITLE = "0";

    public static final String ENROLL_DATE = "1";

    public static final String ENROLL_PRICE = "2";

    public static final String ENROLL_COUNT = "3";

    public static final String ENROLL_TOTALCOUNT = "4";

    public static final String ENROLL_PLACE = "5";

    public static final String ENROLL_ACTIVITYID = "6";
    public static final String SP_SHORTCUT = "short_cut";
    public static final String EXTRA_FROM = "extra_icon";
    public static final  boolean EXTRA_FROM_SHORTCUT_ICON = true ;
    public static final String REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    public static final String SCOPE = "";
    public static final String PREVIEWIMAG_SELECT = "photo_preview_select";
    public static final String INTENT_ISLIKE = "islike";
    public static final String INTENT_ISLIKE_POSITION ="islike_position" ;
    public static final String INTENT_ISLIKE_POST_TYPE = "islike_type";
    public static final int TYPE_POSTDEFORMPOST = 0x107;
    public static final int TYPE_POSTDEFORMLIKE = 0x108;
    public static final int QATYPE_STAR = 0x118;
    public static final int QATYPE_SCORE= 0x119;
    public static final int QATYPE_GAME= 0x120;
    public static final String TYPE = "type";
    public static final String POST_ID = "postId";
    public static final String INTENT_GAMECATEGORY = "GAME_CATEGORY_ID";
    public static final String INTENT_GAME_NAME = "game_name";


    public static int TYPE_RELOGIN = 10003;

    //================= LIKE ====================
    //1 活动 2文章 3场馆

    public static final int LIKE_TYPE_ACTIVITY = 1;

    public static final int LIKE_TYPE_ARTICLE = 2;

    public static final int LIKE_TYPE_SHOP = 3;

    //================= LIKERESULT ====================

    public static final int LIKE_RESULT_SINGFILED = 2;
    public static final int LIKE_TYPE_FILED = 3;
    public static final int LIKE_TYPE_DISABLE = 5;
    public static final int LIKE_TYPE_LIKED = 4;
    public static final int LIKE_RESULT_SUCCESS = 1;

    //================= Banner ====================
    //biz_type业务类型  （1文章 2活动 3课程 4商家 5站外链接）
    public static final String BANNER_TYPE = "banner_type" ;
    public static final int BANNER_TYPE_ARTICLE = 1 ;
    public static final int BANNER_TYPE_ACTIVITY = 2 ;
    public static final int BANNER_TYPE_CLASS = 3 ;
    public static final int BANNER_TYPE_BUSINESS = 4 ;
    public static final int BANNER_TYPE_OUTURL = 5 ;

    //================= Course ====================
    public static final String COURSE_ID = "course_id" ;
    public static final String COURSE_SELLERID = "seller_id" ;

    //================= Regist ====================

    public static final int REGIST_SUCCESS = 1 ;
    public static final int REGIST_FILURE = 2 ;


    //=================登录鉴权==================
    public static final String LOGIN_TYPE = "login_type";
    public static final String LOGIN_TYPE2 = "login_type2";
    public static final int LOGIN_FROMCOLLECT = 1;
    public static final int LOGIN_FROMLOGIN = 2;
    public static final int LOGIN_FRRO = 3;

    //=================相册相关==================
    public static final String PREVIEWIMAG = "list";


    public static final String WEIBO_KEY = "1649698522";
    public static String PREVIEW_POSITION= "position";
    public static String TYPE_PREVIEW = "type_preview";
    public static String ACTION_STARTMAIN = "android.intent.action.perfit.main";

    public static String COMMENT_COUNT = "conment_count";
    public static String FOLLOW_COUNT = "follow_count";
    public static String FANS_COUNT = "fans_count";
    public static String REFRESH_HOME = "refrshhome";
    public static String USER_ID = "userId";
    public static String EXTRA_IMAGE="extra_imageview";
    public static String IMAGE_URL_EXTRA="image_url";
    public static String PROPNAME_SCREENLOCATION_LEFT="PROPNAME_SCREENLOCATION_LEFT";
    public static final String PROPNAME_SCREENLOCATION_TOP = "PROPNAME_SCREENLOCATION_TOP";

    public static String PROPNAME_WIDTH="PROPNAME_WIDTH";

    public static String PROPNAME_HEIGHT="PROPNAME_HEIGHT";
    public static String VIEW_INFO_EXTRA ="VIEW_INFO_EXTRA";
    public static String INTENT_ISDELETE="intent_isdelete";


    //====================  埋点事件==================
    public static String EVENT_PUBLISHI_NOTE = "event_publish_note";
    public static String EVENT_START = "event_start";
}
