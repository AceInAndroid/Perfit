package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ActivityDetailBean;
import com.mperfit.perfit.model.bean.ArticleDetailBean;
import com.mperfit.perfit.model.bean.MatchDetaiBean;

/**
 * Created by zhangbing on 16/10/18.
 */

public class ArticleDetailContract  {
    
public interface View extends BaseView{
//    void showContent(ZhihuDetailBean zhihuDetailBean);
    void showContent(ActivityDetailBean activityDetailBean);
    void showMatchContent(MatchDetaiBean activityDetailBean);
    void showArticleContent(ArticleDetailBean articleDetailBean);
    void showActivityEnrollResult(int type,String msg);
    void showMatchEnrollResult(int type,String msg);
    void setLikeState(Boolean isLiked);
    void showLikeResult(int result,String msg);
}

public interface Presenter extends BasePresenter<ArticleDetailContract.View>{
    void getActivityDetail(String id);
    void getArticleDetail(String id);
    void getMatchDetail(String id);
    void putActivityEnrollFormInfo(String activityId);
    void putMatchEnrollFormInfo(String activityId);
    void insetLike( String id , int type);
    void deleteLike(String id,int type);


}




}