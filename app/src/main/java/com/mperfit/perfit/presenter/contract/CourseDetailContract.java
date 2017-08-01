package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.CourseDetailBean;

/**
 * Created by zhangbing on 2016/11/23.
 */

public class CourseDetailContract {

    public interface View extends BaseView {
        void showContent(CourseDetailBean courseDetailBean);


    }

    public interface Presenter extends BasePresenter<View> {
        void getCourseDetai(String courseId);


    }

}