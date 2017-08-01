package com.mperfit.perfit.model.http;

import com.mperfit.perfit.model.bean.ActivityBean;
import com.mperfit.perfit.model.bean.ActivityDetailBean;
import com.mperfit.perfit.model.bean.AddShopCommentBean;
import com.mperfit.perfit.model.bean.AliPayInfoBean;
import com.mperfit.perfit.model.bean.ArticleCollectBean;
import com.mperfit.perfit.model.bean.ArticleDetailBean;
import com.mperfit.perfit.model.bean.ArticleListBean;
import com.mperfit.perfit.model.bean.ClassPageTopData;
import com.mperfit.perfit.model.bean.CompetitionGameBean;
import com.mperfit.perfit.model.bean.CourseOrderCompletedBean;
import com.mperfit.perfit.model.bean.MatchDetaiBean;
import com.mperfit.perfit.model.bean.OrderDetailBean;
import com.mperfit.perfit.model.bean.OrderListBean;
import com.mperfit.perfit.model.bean.PersonalMyGameBean;
import com.mperfit.perfit.model.bean.QiNiuBean;
import com.mperfit.perfit.model.bean.ReFoundDetailInfoBean;
import com.mperfit.perfit.model.bean.RefoundComoleteInfoBean;
import com.mperfit.perfit.model.bean.RefoundConfirmBean;
import com.mperfit.perfit.model.bean.ShopCommentListBean;
import com.mperfit.perfit.model.bean.VenueAreaListBean;
import com.mperfit.perfit.model.bean.VenueListInfoBean;
import com.mperfit.perfit.model.bean.WeixinPayInfoBean;
import com.mperfit.perfit.model.bean.ConfirmOrderInfoBean;
import com.mperfit.perfit.model.bean.CourseDetailBean;
import com.mperfit.perfit.model.bean.CourseListBean;
import com.mperfit.perfit.model.bean.EnrollResultBean;
import com.mperfit.perfit.model.bean.FindPassBean;
import com.mperfit.perfit.model.bean.HomeDataBean;
import com.mperfit.perfit.model.bean.LikeListBean;
import com.mperfit.perfit.model.bean.MyJoinedActivityBean;
import com.mperfit.perfit.model.bean.NormalLoginBean;
import com.mperfit.perfit.model.bean.ProfileBean;
import com.mperfit.perfit.model.bean.ProfileUpdateBean;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.model.bean.ShopDtailBean;
import com.mperfit.perfit.model.bean.SuggestionBean;
import com.mperfit.perfit.model.bean.UserCenterBean;
import com.mperfit.perfit.model.bean.VerificationCodeBean;
import com.mperfit.perfit.model.bean.WeixinLoginBean;
import com.mperfit.perfit.model.bean.AddLikeBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangbing on 16/10/24.
 */

public interface PerfitApis {
    //    String HOST = "http://test-app.mperfit.com/";
    String HOST = "http://app.mperfit.com/";
//    String HOST = "http://114.215.70.159:8089/";

    @POST("activity/list")
    Observable<ActivityBean> getActivityInfo(@Query("page") int page);

    /**
     * 赛事列表
     */
    @POST("match/category/list")
    Observable<HttpResult<CompetitionGameBean.DataBean>> getCompetitionInfo(@Query("page") int page);


    @POST("activity/detail")
    Observable<ActivityDetailBean> getActivityDetail(@Query("activity_id") String id);

    @POST("match/detail")
    Observable<MatchDetaiBean> getMatchDetail(@Query("match_id") String id);

    @POST("weChat/login")
    Observable<WeixinLoginBean> getWeixinLoginToken(@Query("code") String code);

    @POST("user/smsCode")
    Observable<VerificationCodeBean> getVerificationCode(@Query("phone") String phone, @Query("type") int type);

    @POST("user/register")
    Observable<VerificationCodeBean> getRegister(@Query("phone") String phone, @Query("password") String passWord, @Query("code") String code, @Query("device_token") String uMengToken);

    @POST("user/login")
    Observable<NormalLoginBean> getPhoneLogin(@Query("phone") String phone, @Query("password") String passWorld, @Query("device_token") String uMengToken);

    @POST("user/changePwd")
    Observable<FindPassBean> getFindPassword(@Query("phone") String phone, @Query("password") String passWorld, @Query("code") String code);

    @POST("activity/enroll/add")
    Observable<EnrollResultBean> getEnroll(@Query("activity_id") String id, @Query("name") String name,
                                           @Query("phone") String phone, @Query("sex") int sex,
                                           @Query("age") int age, @Query("remark") String remark, @Query("code") String code);


    @POST("activity/enroll/add")
    Observable<EnrollResultBean> getEnroll(@Query("activity_id") String id);


    @POST("match/enroll/add")
    Observable<EnrollResultBean> getMatchEnroll(@Query("match_id") String id);


    @POST("user/activity/enroll/list")
    Observable<MyJoinedActivityBean> getMyJoinedActivity(@Query("page") int page);

    //获取我参加比赛列表
    @POST("user/match/enroll/list")
    Observable<PersonalMyGameBean> getMyGameInfo(@Query("page") int page);


    @POST("user/info/detail")
    Observable<ProfileBean> getProfile();

    @POST("user/info/update")
    Observable<ProfileUpdateBean> getProfileUpdate(@Query("name") String name, @Query("sex") int sex, @Query("img_url") String head,
                                                   @Query("birth") String birth, @Query("profession") String profession,
                                                   @Query("feeling") String enmotionState, @Query("signature") String signature);


    @POST("user/info/update")
    Observable<ProfileUpdateBean> getProfileUpdate(@Query("name") String name, @Query("sex") int sex,
                                                   @Query("birth") String birth, @Query("profession") String profession,
                                                   @Query("feeling") String enmotionState, @Query("signature") String signature);


    @POST("user/center")
    Observable<UserCenterBean> getUserCenterInfo();

    @POST("user/suggest")
    Observable<SuggestionBean> getSendSuggestion(@Query("content") String s);

    //用户添加收藏 类型 1 活动 2文章 3场馆
    @POST("collect/add")
    Observable<AddLikeBean> getAddLike(@Query("biz_id") String id, @Query("biz_type") int type);

    //用户取消收藏 类型 1 活动 2文章 3场馆
    @POST("collect/delete")
    Observable<AddLikeBean> getDeleteLike(@Query("biz_ids") String id, @Query("biz_type") int type);

    //获取用户收藏列表 类型 1 活动 2文章 3场馆
    @POST("collect/activity/list")
    Observable<LikeListBean> getLikeList(@Query("page") int page);

    //获取首页 类型 1 轮播条 2文章 3分类
    @POST("index_v2_0")
    Observable<HomeDataBean> getHomeData();

    //获取文章列表的接口
    @POST("article/list_v2_0")
    Observable<ArticleListBean> getArticleList(@Query("page") int page, @Query("category_id") int categoryId);

    @POST("article/detail")
    Observable<ArticleDetailBean> getArticleDetail(@Query("article_id") String id);

    @POST("course/sort/list")
    Observable<ClassPageTopData> getCourseMenuData();

    @POST("course/list")
    Observable<CourseListBean> getCourseList(@Query("page") int page, @Query("area_id") int areaId,
                                             @Query("category_id") int categoryId, @Query("sort_id") int sortId,
                                             @Query("longitude") double longitude, @Query("latitude") double latitude);

    @POST("course/list")
    Observable<CourseListBean> getCourseList();

    //获取课程详情
    @POST("course/detail")
    Observable<CourseDetailBean> getCourseDetail(@Query("course_id") String id);

    //场馆详情
    @POST("venue/detail")
    Observable<ShopDtailBean> getShopDetail(@Query("seller_id") String id);

    //我收藏的文章
    @POST("collect/article/list")
    Observable<ArticleCollectBean> getArticleCollect(@Query("page") int page);

    //我收藏的场馆
    @POST("collect/venue/list")
    Observable<ShopCollectBean> getShopCollect(@Query("page") int page);

    //================pay==============

    //获取preperid
    @POST("order/confirm")
    Observable<ConfirmOrderInfoBean> getConfirmOrderInfo(@Query("course_id") String courseId, @Query("number") int count);

    //wxpay
    @POST("wxpay/course/sign")
    Observable<WeixinPayInfoBean> getWeixinOrderInfo(@Query("order_id") long preOrderId);

    //阿里pay
    @POST("alipay/course/sign")
    Observable<AliPayInfoBean> getAlipayOrderInfo(@Query("order_id") long preOrderId);

    //支付结果告知服务器
    @POST("order/pay/complete")
    Observable<CourseOrderCompletedBean> getTalkToServiceOrderComplete(@Query("order_id") long id);


    //订单列表
    //status : 1待付款  2已付款  3待评价(已消费) 4已评价 5 已退款（已过期） 不传默认全部
    @POST("order/list")
    Observable<OrderListBean> getOrderList(@Query("page") int page, @Query("status") int state);

    @POST("order/list")
    Observable<OrderListBean> getOrderList(@Query("page") int page);

    @POST("order/detail")
    Observable<OrderDetailBean> getOrderDetail(@Query("order_id") long orderId);

    @POST("order/course/refund/confirm")
    Observable<RefoundConfirmBean> getRefundConfirm(@Query("order_id") long id);

    @POST("order/course/refund/complete")
    Observable<RefoundComoleteInfoBean> getCompleteRefund(@Query("order_id") long id, @Query("number") int count);

    @POST("order/course/refund/detail")
    Observable<ReFoundDetailInfoBean> geRefoundDetail(@Query("refund_id") long refoudId);

    //七牛图片上传token
    @POST("common/qiniu/getToken")
    Observable<QiNiuBean> getQiNiuToken();

    //添加商家评论
    @POST("venue/comment/add")
    Observable<AddShopCommentBean> getAddShopComment(@Query("order_id") long orderId, @Query("score") int score, @Query("content") String content, @Query("img_array") String imgArray);

    //获取商家列表
    @POST("venue/list")
    Observable<VenueListInfoBean> getVenueList(@Query("page") int page, @Query("area_id") int areaId,
                                               @Query("longitude") double longitude, @Query("latitude") double latitude);

    //获取商家列表
    @POST("venue/list")
    Observable<VenueListInfoBean> getVenueList();

    //获取场馆商圈
    @POST("venue/area/list")
    Observable<VenueAreaListBean> getVenueAreaList();

    //商家评论列表
    @POST("venue/comment/list")
    Observable<ShopCommentListBean> getVenuCommentList(@Query("page") int page, @Query("seller_id") long sellerId);


}
