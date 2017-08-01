package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;

/**
 * 文章首页的契约类
 * Created by zhangbing on 16/10/18.
 */

public class ArticleContract  {
    
public interface View extends BaseView{
//    void showContent(DailyListBean info);
    void showMoreContent();
    void showPrograss();

}

public interface Presenter extends BasePresenter<View>{
    void getData();


}




}