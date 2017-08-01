package com.mperfit.perfit.ui.otherspersonal.fragment;

import android.widget.GridView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.widget.LoadMoreFooterView;

import butterknife.BindView;

/**
 * Created by zhangbing on 2017/2/14.
 */

public class OthersPersonalLikeFragment extends SimpleFragment implements OnLoadMoreListener {
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
