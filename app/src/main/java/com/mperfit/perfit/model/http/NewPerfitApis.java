package com.mperfit.perfit.model.http;

import com.mperfit.perfit.model.bean.AddPostLikeBean;
import com.mperfit.perfit.model.bean.CategoryCompetitionBean;
import com.mperfit.perfit.model.bean.DayRankingListBean;
import com.mperfit.perfit.model.bean.FansListBean;
import com.mperfit.perfit.model.bean.MyFollowPostBean;
import com.mperfit.perfit.model.bean.MyPointsListBean;
import com.mperfit.perfit.model.bean.MyselfPostBean;
import com.mperfit.perfit.model.bean.NewHomeBean;
import com.mperfit.perfit.model.bean.NewPersonalBean;
import com.mperfit.perfit.model.bean.OthersUserCenterBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;
import com.mperfit.perfit.model.bean.PostCommentBean;
import com.mperfit.perfit.model.bean.PostDetailBean;
import com.mperfit.perfit.model.bean.PublishTopicBean;
import com.mperfit.perfit.model.bean.ScoreBoardBean;
import com.mperfit.perfit.model.bean.SquareListBean;
import com.mperfit.perfit.model.bean.SquareMorePostBean;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangbing on 2017/2/15.
 */

public interface NewPerfitApis {

//    String HOST = "http://test-app.mperfit.com/";
    String HOST = "http://app.mperfit.com/";
//    String HOST = "http://114.215.70.159:8089/";


    //1图片 2图文 3短视频

    @POST("/index_v2_1_0")
    Observable<HttpResult<SquareListBean.DataBean>> getSquareInfo(@Query("page") int page,
                                                                  @Query("topic_id")long toppicId);

    @POST("/index_v2_2_0")
    Observable<HttpResult<NewHomeBean.DataBean>> getNewHomePage();


    @POST("note/good/list")
    Observable<HttpResult<SquareMorePostBean.DataBean>> getMoreNewHomePage(@Query("page") int page);


    @POST("/note/friendList")
    Observable<HttpResult<MyFollowPostBean.DataBean>> getFollowPostList(@Query("page") int page);



    @POST("note/list")
    Observable<HttpResult<SquareMorePostBean.DataBean>> getSquarePostMoreInfo(@Query("page") int page,
                                                                              @Query("topic_id")long toppicId);


    @POST("note/createLike")
    Observable<HttpResult<AddPostLikeBean.DataBean>> getaddPostLikeInfo(@Query("note_id") long postId);


    @POST("note/removeLike")
    Observable<HttpResult<AddPostLikeBean.DataBean>> getRemovePostLikeInfo(@Query("note_id") long postId);


    @POST("friend/add")
    Observable<HttpResult<AddPostLikeBean.DataBean>> getFolloewInfo(@Query("des_user_id") long userId);


    @POST("friend/remove")
    Observable<HttpResult<AddPostLikeBean.DataBean>> getRemobveFolloewInfo(@Query("des_user_id") long userId);


    @POST("note/remove")
    Observable<AddPostLikeBean> getDeletePostInfo(@Query("note_id") long noteId);



    @POST("user/another")
    Observable<HttpResult<OthersUserCenterBean.DataBean>> getOthersPersonalInfo(@Query("des_user_id") long userId);


    @POST("user/score/list")
    Observable<HttpResult<MyPointsListBean.DataBean>> getMyPointsInoList(@Query("page")int page);


    @POST("match/list")
    Observable<HttpResult<CategoryCompetitionBean.DataBean>> getCategoryCompetitionInfoList(@Query("page")int page, @Query("category_id")int categoryId);



    @POST("user/center_v2_2_0")
    Observable<HttpResult<NewPersonalBean.DataBean>> getSelfPersonalInfo();


    @POST("user/note/list")
    Observable<HttpResult<PersonalMyPostBean.DataBean>> getMySelfPostList(@Query("page")int page, @Query("des_user_id")long userId);


    @POST("user/note/like/list")
    Observable<HttpResult<PersonalMyPostBean.DataBean>> getMySelfLikeList(@Query("page")int page, @Query("des_user_id")long userId);

    @POST("note/detail")
    Observable<HttpResult<PostDetailBean.DataBean>> getPostDetailInfo(@Query("note_id") long postId);


    @POST("/note/comment/list")
    Observable<HttpResult<PostCommentBean.DataBean>> getPostCommentList(@Query("page")int page,@Query("note_id")long postId);


    @POST("/note/comment/publish")
    Observable<HttpResult<AddPostLikeBean.DataBean>> getAddComment(@Query("note_id")long postId,@Query("content")String content);


    @POST("note/publish")
    Observable<HttpResult<AddPostLikeBean.DataBean>> getPublishAPost(@Query("content")String content,
                                                                     @Query("img_url")String imageUrl,@Query("topic_id")long topicId,
                                                                     @Query("img_height") int height,@Query("img_width")int width);


    @POST("topic/list")
    Observable<HttpResult<PublishTopicBean.DataBean>> getPublishTopics(@Query("con")int count);

    @POST("topic/list")
    Observable<HttpResult<PublishTopicBean.DataBean>> getPublishTopics();

    @POST("friend/follow/list")
    Observable<HttpResult<FansListBean.DataBean>> getFollowList(@Query("page")int page,@Query("des_user_id")long userId);

    @POST("friend/fans/list")
    Observable<HttpResult<FansListBean.DataBean>> getFansList(@Query("page")int page,@Query("des_user_id")long userId);


    @POST("rank/userHot")
    Observable<HttpResult<DayRankingListBean.DataBean>> getStarRankingList(@Query("time")int time,@Query("page")int page);

    @POST("rank/userHot")
    Observable<HttpResult<DayRankingListBean.DataBean>> getStarRankingList(@Query("page")int page);

    @POST("rank/userScore")
    Observable<HttpResult<ScoreBoardBean.DataBean>> getScoreRankingList(@Query("page")int page);

}
