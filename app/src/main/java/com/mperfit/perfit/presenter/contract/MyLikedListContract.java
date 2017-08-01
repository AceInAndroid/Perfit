package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.LikeListBean;

/**
 * Created by zhangbing on 2016/11/9.
 */

public class MyLikedListContract {
    
    

public interface View extends BaseView{
    void showContent(LikeListBean likeListBean);
    void showMore(LikeListBean likeListBean);

    void setLikeState(Boolean isLiked,int position);
    void showLikeResult(int result,String msg);


}

public interface Presenter extends BasePresenter<View>{
    void getLikeListInfo(int page);

    void deleteItem(String id);

    void getMoreData(int page);

    void insetLike( String id , int position);
    void deleteLike(final String id, final int position);

}

}