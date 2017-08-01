package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ShopDtailBean;

/**
 * Created by zhangbing on 2016/11/24.
 */

public class ShopDetailContract {
    
public interface View extends BaseView{
    void showContent(ShopDtailBean shopDtailBean);
    void setLikeState(Boolean isLiked);
    void showLikeResult(int result,String msg);
}

public interface Presenter extends BasePresenter<View>{
    void getShopDetailInfo(String id);

    void insetLike( String id , int type);
    void deleteLike(String id,int type);

}




}