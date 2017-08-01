package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ArticleCollectBean;

/**
 * Created by zhangbing on 2016/11/28.
 */

public class ArticleCollectContract  {
    
    
public interface View extends BaseView{
    void showContent(ArticleCollectBean articleCollectBean);
    void showDeleteResult(int result,String msg);
    void showMore(ArticleCollectBean articleCollectBean);
}

public interface Presenter extends BasePresenter<View>{
    void getArticleCollectInfo(int page);
    void deleteLike(String id,int type);
    void deleteLikeFromDb(String id);
    void getMoreData(int page);
}



}