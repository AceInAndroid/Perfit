package com.mperfit.perfit.presenter.contract;

import com.alibaba.fastjson.asm.Type;
import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ArticleCollectBean;
import com.mperfit.perfit.model.bean.LikeListBean;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.model.bean.UserCenterBean;

/**
 * Created by zhangbing on 16/11/2.
 */

public class PersonalContract {
    
    
public interface View extends BaseView{
    void showContent(int type,UserCenterBean userCenterBean);
//    void initLikeData(LikeListBean likeListBean,int type);
//    void initLikeData(ArticleCollectBean articleCollectBean,int type);
//    void initLikeData(ShopCollectBean shopCollectBean,int type);
}

public interface Presenter extends BasePresenter<View>{
    void getUserCenterInfo();
//    void getLikeListInfo(int page,int type);
}

}