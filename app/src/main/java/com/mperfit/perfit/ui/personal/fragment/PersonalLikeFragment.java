package com.mperfit.perfit.ui.personal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.widget.LoadMoreFooterView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2017/2/14.
 */

public class PersonalLikeFragment extends SimpleFragment implements OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    GridView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_like;
    }

    @Override
    protected void initEventAndData() {
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);



    }


    @Override
    public void onLoadMore() {

    }
}
