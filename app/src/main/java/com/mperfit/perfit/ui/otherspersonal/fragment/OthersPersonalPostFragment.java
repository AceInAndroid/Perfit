package com.mperfit.perfit.ui.otherspersonal.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.GridView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.model.bean.OthersUserCenterBean;
import com.mperfit.perfit.widget.LoadMoreFooterView;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangbing on 2017/2/14.
 */

public class OthersPersonalPostFragment extends SimpleFragment implements OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    GridView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private  List<OthersUserCenterBean.DataBean.NoteListBean> mNoteList;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_post;
    }

    @Override
    protected void initEventAndData() {
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        Bundle bundle = getArguments();//从activity传过来的Bundle

            String text = bundle.getString("text");
//            ToastUtils.showShrotMsg(mContext,"BundleOk"+text);
//            List<OthersUserCenterBean.DataBean.NoteListBean> noteList = bundle.getParcelable("notelist");
            Logger.e("BundleOk" +text);

    }


    @Override
    public void onLoadMore() {

    }
}
