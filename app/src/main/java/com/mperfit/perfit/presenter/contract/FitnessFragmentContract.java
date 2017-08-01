package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ArticleListBean;

/**
 * Created by zhangbing on 2016/11/18.
 */

public class FitnessFragmentContract {
    
public interface View extends BaseView{
    void showContent(ArticleListBean articleListBean);
    void showMore(ArticleListBean articleListBean);
}

public interface Presenter extends BasePresenter<View>{
    void getArticleList(int page,int category);
}




}