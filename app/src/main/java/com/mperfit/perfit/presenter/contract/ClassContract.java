package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ClassPageTopData;
import com.mperfit.perfit.model.bean.CourseListBean;

/**
 * Created by zhangbing on 2016/11/21.
 */

public class ClassContract {
    
public interface View extends BaseView{
    void showMenuInfo(ClassPageTopData classPageTopData);
    void showCourseListInfo(CourseListBean courseListBean);
    void showMoreCourseListInfo(CourseListBean courseListBean);
}

public interface Presenter extends BasePresenter<View>{
    void getTopMenuData();

    void getCourseList(int page,int areaId,int categoryId,int sortId,double longitude,double latitude);
    void getCourseList();
    void getCourseListMore(int page,int areaId,int categoryId,int sortId,double longitude,double latitude);

}

}