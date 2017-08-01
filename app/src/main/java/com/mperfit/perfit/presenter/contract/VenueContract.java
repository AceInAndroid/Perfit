package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.VenueAreaListBean;
import com.mperfit.perfit.model.bean.VenueListInfoBean;

/**
 * Created by zhangbing on 2016/12/9.
 */

public class VenueContract {

    public interface View extends BaseView {
        void showContent(VenueListInfoBean venueListInfoBean);
        void showMore(VenueListInfoBean venueListInfoBean);
        void showArea(VenueAreaListBean venueAreaListBean);
    }

    public interface Presenter extends BasePresenter<View> {
        void getVenueList(int page, int areaId, double longitude, double latitude);
        void getVenueList();
        void getVenueAreaList();
        void getMore(int page, int areaId, double longitude, double latitude);
    }

}