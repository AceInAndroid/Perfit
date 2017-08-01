package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ShopCollectBean;

/**
 * Created by zhangbing on 2016/11/30.
 */

public class ShopCollectContract {
    
public interface View extends BaseView{
    void showContent(ShopCollectBean shopCollectBean);
    void setLikeState(Boolean isLiked,int position);
    void showLikeResult(int result,String msg);
    void showMoreData(ShopCollectBean shopCollectBean);
}

public interface Presenter extends BasePresenter<View>{
    void getCollectData(int page);
    void insetLike( String id , int type,int position);
    void deleteLike(String id,int type,int position);
    void getMoreData(int page);
}



}