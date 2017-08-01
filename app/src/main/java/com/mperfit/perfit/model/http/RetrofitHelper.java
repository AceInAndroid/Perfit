package com.mperfit.perfit.model.http;


import android.content.Context;

import com.mperfit.perfit.BuildConfig;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.ActivityBean;
import com.mperfit.perfit.model.bean.ActivityDetailBean;
import com.mperfit.perfit.model.bean.AddLikeBean;
import com.mperfit.perfit.model.bean.AddPostLikeBean;
import com.mperfit.perfit.model.bean.AddShopCommentBean;
import com.mperfit.perfit.model.bean.AliPayInfoBean;
import com.mperfit.perfit.model.bean.ArticleCollectBean;
import com.mperfit.perfit.model.bean.ArticleDetailBean;
import com.mperfit.perfit.model.bean.ArticleListBean;
import com.mperfit.perfit.model.bean.CategoryCompetitionBean;
import com.mperfit.perfit.model.bean.ClassPageTopData;
import com.mperfit.perfit.model.bean.CompetitionGameBean;
import com.mperfit.perfit.model.bean.ConfirmOrderInfoBean;
import com.mperfit.perfit.model.bean.CourseDetailBean;
import com.mperfit.perfit.model.bean.CourseListBean;
import com.mperfit.perfit.model.bean.CourseOrderCompletedBean;
import com.mperfit.perfit.model.bean.DayRankingListBean;
import com.mperfit.perfit.model.bean.EnrollResultBean;
import com.mperfit.perfit.model.bean.FansListBean;
import com.mperfit.perfit.model.bean.FindPassBean;
import com.mperfit.perfit.model.bean.HomeDataBean;
import com.mperfit.perfit.model.bean.LikeListBean;
import com.mperfit.perfit.model.bean.MatchDetaiBean;
import com.mperfit.perfit.model.bean.MyFollowPostBean;
import com.mperfit.perfit.model.bean.MyJoinedActivityBean;
import com.mperfit.perfit.model.bean.MyPointsListBean;
import com.mperfit.perfit.model.bean.MyselfPostBean;
import com.mperfit.perfit.model.bean.NewHomeBean;
import com.mperfit.perfit.model.bean.NewPersonalBean;
import com.mperfit.perfit.model.bean.NormalLoginBean;
import com.mperfit.perfit.model.bean.OthersUserCenterBean;
import com.mperfit.perfit.model.bean.PersonalMyGameBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;
import com.mperfit.perfit.model.bean.PostCommentBean;
import com.mperfit.perfit.model.bean.PostDetailBean;
import com.mperfit.perfit.model.bean.PublishTopicBean;
import com.mperfit.perfit.model.bean.ScoreBoardBean;
import com.mperfit.perfit.model.bean.SquareListBean;
import com.mperfit.perfit.model.bean.OrderDetailBean;
import com.mperfit.perfit.model.bean.OrderListBean;
import com.mperfit.perfit.model.bean.ProfileBean;
import com.mperfit.perfit.model.bean.ProfileUpdateBean;
import com.mperfit.perfit.model.bean.QiNiuBean;
import com.mperfit.perfit.model.bean.ReFoundDetailInfoBean;
import com.mperfit.perfit.model.bean.ReLoadPersonalDataBean;
import com.mperfit.perfit.model.bean.RefoundComoleteInfoBean;
import com.mperfit.perfit.model.bean.RefoundConfirmBean;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.model.bean.ShopCommentListBean;
import com.mperfit.perfit.model.bean.ShopDtailBean;
import com.mperfit.perfit.model.bean.SquareMorePostBean;
import com.mperfit.perfit.model.bean.SuggestionBean;
import com.mperfit.perfit.model.bean.UserCenterBean;
import com.mperfit.perfit.model.bean.VenueAreaListBean;
import com.mperfit.perfit.model.bean.VenueListInfoBean;
import com.mperfit.perfit.model.bean.VerificationCodeBean;
import com.mperfit.perfit.model.bean.WeixinLoginBean;
import com.mperfit.perfit.model.bean.WeixinPayInfoBean;
import com.mperfit.perfit.utils.ChannelUtil;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.utils.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by zhangbing on 2016/8/3.
 */
public class RetrofitHelper {
    private static Context context;

    private static OkHttpClient okHttpClient = null;
    private static PerfitApis perfitApisService = null;
    private static NewPerfitApis newPerfitApisService = null;

    private void init() {
        initOkHttp();
        perfitApisService = getPerfitApisService();
        newPerfitApisService = getNewPerfitApisService();
    }

    public RetrofitHelper(Context context) {
        this.context = context;
        init();

        Subscription subscribe = RxBus.getDefault().toObservable(ReLoadPersonalDataBean.class)
                .subscribe(new Action1<ReLoadPersonalDataBean>() {
                    @Override
                    public void call(ReLoadPersonalDataBean reLoadPersonalDataBean) {
                        init();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });


    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //设置log拦截器
        if (BuildConfig.DEBUG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        //设置缓存
        // http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //没有网络 强制读取缓存
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0 ,remove一下header 不然下面会出错
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        //设置公共请求参数头信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("systemtype", android.os.Build.MODEL)
                        .header("systemversion", android.os.Build.VERSION.RELEASE)
                        .header("screenwidth", String.valueOf(SystemUtil.getScreenWidth(context)))
                        .header("screenheight", String.valueOf(SystemUtil.getScreenHight(context)))
                        .header("platform", "1")
                        .header("channel", ChannelUtil.getChannel(context, "xiaomi"))
                        .header("imei", SystemUtil.getImei(context))
                        .header("appversion", SystemUtil.getVersionCode(context))
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        final String authid = SharedPreferenceUtil.getAuthid();
        final String authCode = SharedPreferenceUtil.getAuthCode();
        if (!authid.equals("1") || !authCode.equals("auth_code")) {
            //添加登录头
            Interceptor apikey = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    request = request.newBuilder()
                            .addHeader("authId", authid)
                            .addHeader("authCode", authCode)
                            .build();
                    return chain.proceed(request);
                }
            };
            //添加登录认证头
            builder.addInterceptor(apikey);
        }


        //设置头信息
        builder.addInterceptor(headerInterceptor);
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);

        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }


    private static PerfitApis getPerfitApisService() {
        Retrofit perfitRetrofit = new Retrofit.Builder()
                .baseUrl(PerfitApis.HOST)
                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return perfitRetrofit.create(PerfitApis.class);
    }


    private static NewPerfitApis getNewPerfitApisService() {
        Retrofit perfitRetrofit = new Retrofit.Builder()
                .baseUrl(NewPerfitApis.HOST)
                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return perfitRetrofit.create(NewPerfitApis.class);
    }


    //获取活动列表
    public Observable<ActivityBean> fetchActivityListInfo(int page) {
        return perfitApisService.getActivityInfo(page);
    }


    //获取赛事列表
    public Observable<HttpResult<CompetitionGameBean.DataBean>> fetchCompetitionGameListInfo(int page) {
        return perfitApisService.getCompetitionInfo(page);
    }
    //获取活动详情

    public Observable<ActivityDetailBean> fetchActivityDetail(String activityId) {
        return perfitApisService.getActivityDetail(activityId);
    }

    public Observable<MatchDetaiBean> fetchMatchDetail(String matchId) {
        return perfitApisService.getMatchDetail(matchId);
    }


    //获取登录token

    public Observable<WeixinLoginBean> fetchWeixinLogin(String code) {
        return perfitApisService.getWeixinLoginToken(code);
    }

    //获取手机验证码
    public Observable<VerificationCodeBean> fetchVerificationCode(String phoneNum, int type) {
        return perfitApisService.getVerificationCode(phoneNum, type);
    }

    //注册
    public Observable<VerificationCodeBean> fetchRegister(String phoneNum, String password, String code, String uMengToken) {
        return perfitApisService.getRegister(phoneNum, password, code, uMengToken);
    }

    //使用手机登录
    public Observable<NormalLoginBean> fetchNormalLogin(String phone, String passWord, String uMengToken) {
        return perfitApisService.getPhoneLogin(phone, passWord, uMengToken);
    }

    //找回密码

    public Observable<FindPassBean> fetchFindPassword(String phone, String passWord, String code) {
        return perfitApisService.getFindPassword(phone, passWord, code);
    }


    //活动报名

    public Observable<EnrollResultBean> fetchEnroll(String activityId, String name, String phone,
                                                    int sex, int age, String remark, String code) {
        return perfitApisService.getEnroll(activityId, name, phone, sex, age, remark, code);
    }


    public Observable<EnrollResultBean> fetchEnroll(String activityId) {
        return perfitApisService.getEnroll(activityId);
    }



    public Observable<EnrollResultBean> fetchMatchEnroll(String activityId) {
        return perfitApisService.getMatchEnroll(activityId);
    }

    public Observable<MyJoinedActivityBean> fetchMyJoinedActivity(int page) {
        return perfitApisService.getMyJoinedActivity(page);
    }

    public Observable<PersonalMyGameBean> fetchMyGameList(int page) {
        return perfitApisService.getMyGameInfo(page);
    }

    public Observable<ProfileBean> fetchProfileInfo() {
        return perfitApisService.getProfile();
    }


    //提交个人信息的更改

    public Observable<ProfileUpdateBean> fetchProfileUpdate(String name, int sex, String head,
                                                            String birth, String profession,
                                                            String enmotionState, String signature) {
        return perfitApisService.getProfileUpdate(name, sex, head, birth, profession, enmotionState, signature);
    }


    public Observable<ProfileUpdateBean> fetchProfileUpdate(String name, int sex,
                                                            String birth, String profession,
                                                            String enmotionState, String signature) {
        return perfitApisService.getProfileUpdate(name, sex, birth, profession, enmotionState, signature);
    }



    public Observable<UserCenterBean> fetchUserCenterInfo() {
        return perfitApisService.getUserCenterInfo();
    }

    public Observable<SuggestionBean> fetchSuggestion(String s) {
        return perfitApisService.getSendSuggestion(s);
    }

    //用户添加收藏
    //类型 1 活动 2文章 3场馆
    public Observable<AddLikeBean> fetchAddLike(String id, int type) {
        return perfitApisService.getAddLike(id, type);
    }

    //取消收藏
    //类型 1 活动 2文章 3场馆
    public Observable<AddLikeBean> fetchDeleteLike(String id, int type) {
        return perfitApisService.getDeleteLike(id, type);
    }


    //获取用户收藏列表
    //类型 1 活动 2文章 3场馆
    public Observable<LikeListBean> fetchLikeListInfo(int page) {
        return perfitApisService.getLikeList(page);
    }


    //获取首页 轮播图、分类、文章列表

    public Observable<HomeDataBean> fetchHomeDataInfo() {
        return perfitApisService.getHomeData();
    }

    //文章模块  -  文章列别 page:页码  category : 类别 1：玩 2：美
    public Observable<ArticleListBean> fetchArticleList(int page, int category) {
        return perfitApisService.getArticleList(page, category);
    }

    //获取文章详情

    public Observable<ArticleDetailBean> fetchArticleDetail(String articleId) {
        return perfitApisService.getArticleDetail(articleId);
    }


    public Observable<ClassPageTopData> fetchCourseMenuInfo() {
        return perfitApisService.getCourseMenuData();
    }

    //课程列表
    public Observable<CourseListBean> fetchCourseListInfo() {
        return perfitApisService.getCourseList();
    }

    public Observable<CourseListBean> fetchCourseListInfo(int page, int areaId, int categoryId, int sortId, double longitude, double latitude) {
        return perfitApisService.getCourseList(page, areaId, categoryId, sortId, longitude, latitude);
    }

    //课程详情
    public Observable<CourseDetailBean> fetchCourseDetailInfo(String courseId) {
        return perfitApisService.getCourseDetail(courseId);
    }

    //场馆详情
    public Observable<ShopDtailBean> fetchShopDetail(String sellerId) {
        return perfitApisService.getShopDetail(sellerId);
    }

    //文章收藏
    public Observable<ArticleCollectBean> fetchArticleCollect(int page) {
        return perfitApisService.getArticleCollect(page);
    }


    //场馆收藏
    public Observable<ShopCollectBean> fetchShopCollect(int page) {
        return perfitApisService.getShopCollect(page);
    }

    //提交订单  获取预订单id
    public Observable<ConfirmOrderInfoBean> fetchOrederInfo(String courseId, int count) {
        return perfitApisService.getConfirmOrderInfo(courseId, count);
    }

    //获取微信订单信息qianming
    public Observable<WeixinPayInfoBean> fetchWeixinPay(long preOrderId) {
        return perfitApisService.getWeixinOrderInfo(preOrderId);
    }

    //获取alipay订单信息qianming
    public Observable<AliPayInfoBean> fetchAlipayPay(long preOrderId) {
        return perfitApisService.getAlipayOrderInfo(preOrderId);
    }

    //tell sevice order is completed
    public Observable<CourseOrderCompletedBean> fetchCourseOrderCompleted(long id) {
        return perfitApisService.getTalkToServiceOrderComplete(id);
    }

    //fetch orderlist
    public Observable<OrderListBean> fetchOrederList(int page, int state) {
        return perfitApisService.getOrderList(page, state);
    }

    //fetch orderlist of all
    public Observable<OrderListBean> fetchOrederList(int page) {
        return perfitApisService.getOrderList(page);
    }

    //fetch order detai
    public Observable<OrderDetailBean> fetchOrderDetail(long orderId) {
        return perfitApisService.getOrderDetail(orderId);
    }

    //refound

    public Observable<RefoundConfirmBean> fetchRefoundConfirmInfo(long orderId) {
        return perfitApisService.getRefundConfirm(orderId);
    }

    //refound completed

    public Observable<RefoundComoleteInfoBean> fetchRefoundComplete(long orderId, int count) {
        return perfitApisService.getCompleteRefund(orderId, count);
    }

    //refoudn detail info

    public Observable<ReFoundDetailInfoBean> fetchRefoudDetail(long refoundId) {
        return perfitApisService.geRefoundDetail(refoundId);
    }

    //qiniu token
    public Observable<QiNiuBean> fetchQiniuToken() {
        return perfitApisService.getQiNiuToken();
    }

    //add shop comment
    public Observable<AddShopCommentBean> fetchAddShopComment(long orderId, int score, String content, String imageArray) {
        return perfitApisService.getAddShopComment(orderId, score, content, imageArray);
    }

    //fetch venelist

    public Observable<VenueListInfoBean> fetchVenueList(int page, int areaId, double longitude, double latitude) {
        return perfitApisService.getVenueList(page, areaId, longitude, latitude);
    }

    public Observable<VenueListInfoBean> fetchVenueList() {
        return perfitApisService.getVenueList();
    }

    public Observable<VenueAreaListBean> fetchVenueAreaList() {
        return perfitApisService.getVenueAreaList();
    }

    public Observable<ShopCommentListBean> fetchCommentList(int page, long sellerId) {
        return perfitApisService.getVenuCommentList(page, sellerId);
    }

    //=================2.4版本======================


    //获得精品列表
    public Observable<HttpResult<NewHomeBean.DataBean>> getHomePageList() {
        return newPerfitApisService.getNewHomePage();
    }

    //获得更多精品帖子
    public Observable<HttpResult<SquareMorePostBean.DataBean>> getHomePageList(int page) {
        return newPerfitApisService.getMoreNewHomePage(page);
    }


    //获得广场列表
    public Observable<HttpResult<SquareListBean.DataBean>> getSquareList(int page, long topicId) {
        return newPerfitApisService.getSquareInfo(page, topicId);
    }


    //首页加载更多
    public Observable<HttpResult<MyFollowPostBean.DataBean>> getMyFollowPost(int page) {
        return newPerfitApisService.getFollowPostList(page);
    }


    //首页加载更多
    public Observable<HttpResult<SquareMorePostBean.DataBean>> getSquareMorePostList(int page, long topicId) {
        return newPerfitApisService.getSquarePostMoreInfo(page, topicId);
    }




    public Observable<HttpResult<AddPostLikeBean.DataBean>> getAddPostLike(long postId) {
        return newPerfitApisService.getaddPostLikeInfo(postId);
    }


    public Observable<HttpResult<AddPostLikeBean.DataBean>> getRemovePostLike(long postId) {
        return newPerfitApisService.getRemovePostLikeInfo(postId);
    }

    public Observable<HttpResult<AddPostLikeBean.DataBean>> getFollowInfo(long userId) {
        return newPerfitApisService.getFolloewInfo(userId);
    }


    public Observable<HttpResult<AddPostLikeBean.DataBean>> getRemoveFollowInfo(long userId) {
        return newPerfitApisService.getRemobveFolloewInfo(userId);
    }

    public Observable<AddPostLikeBean> getDeletePostInfo(long noteId) {
        return newPerfitApisService.getDeletePostInfo(noteId);
    }


    //========个人中心相关
    public Observable<HttpResult<OthersUserCenterBean.DataBean>> getOthersPersonalInfo(long userId) {
        return newPerfitApisService.getOthersPersonalInfo(userId);
    }


    public Observable<HttpResult<MyPointsListBean.DataBean>> getMyPointsInfoList(int page) {
        return newPerfitApisService.getMyPointsInoList(page);
    }


    public Observable<HttpResult<CategoryCompetitionBean.DataBean>> getMyCategoryCompetitionInfoList(int page, int categoryId) {
        return newPerfitApisService.getCategoryCompetitionInfoList(page,categoryId);
    }


    public Observable<HttpResult<NewPersonalBean.DataBean>> getSelfPersonalInfo() {
        return newPerfitApisService.getSelfPersonalInfo();
    }

    public Observable<HttpResult<PersonalMyPostBean.DataBean>> getMyselfPostList(int page, long userId) {
        return newPerfitApisService.getMySelfPostList(page,userId);
    }

    public Observable<HttpResult<PersonalMyPostBean.DataBean>> getMyselfLikeList(int page, long userId) {
        return newPerfitApisService.getMySelfLikeList(page,userId);
    }


    public Observable<HttpResult<PostDetailBean.DataBean>> getPostDetailInfo(long postId) {
        return newPerfitApisService.getPostDetailInfo(postId);
    }


    public Observable<HttpResult<PostCommentBean.DataBean>> getPostCommentList(int page, long postId) {
        return newPerfitApisService.getPostCommentList(page, postId);
    }


    public Observable<HttpResult<AddPostLikeBean.DataBean>> getAddComment(long postId, String content) {
        return newPerfitApisService.getAddComment(postId, content);
    }


    public Observable<HttpResult<AddPostLikeBean.DataBean>> getPublishPost(String content,String imageUrl,long topicId,int height,int width) {
        return newPerfitApisService.getPublishAPost(content,imageUrl, topicId,height,width);
    }


    public Observable<HttpResult<PublishTopicBean.DataBean>> getPublishTopics() {
        return newPerfitApisService.getPublishTopics();
    }


    public Observable<HttpResult<PublishTopicBean.DataBean>> getPublishTopics(int count) {
        return newPerfitApisService.getPublishTopics(count);
    }

    public Observable<HttpResult<FansListBean.DataBean>> getFansList(int page,long userId) {
        return newPerfitApisService.getFansList(page,userId);
    }

    public Observable<HttpResult<FansListBean.DataBean>> getFollowList(int page,long userId) {
        return newPerfitApisService.getFollowList(page,userId);
    }

    public Observable<HttpResult<DayRankingListBean.DataBean>> fetchSatrRanking(int time,int page){
        return newPerfitApisService.getStarRankingList(time,page);
    }

    public Observable<HttpResult<DayRankingListBean.DataBean>> fetchSatrRanking(int page){
        return newPerfitApisService.getStarRankingList(page);
    }


    public Observable<HttpResult<ScoreBoardBean.DataBean>> fetchScoreRanking(int page){
        return newPerfitApisService.getScoreRankingList(page);
    }


}
