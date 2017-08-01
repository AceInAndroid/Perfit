package com.mperfit.perfit.ui.me.fragment.myjoined;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.base.SimpleFragment;
import com.mperfit.perfit.model.bean.MyJoinedActivityBean;
import com.mperfit.perfit.presenter.contract.MyJoinContract;
import com.mperfit.perfit.presenter.presenter.MyJoinPresenterImpl;
import com.mperfit.perfit.ui.me.adapter.MyJoinListAdapter;
import com.mperfit.perfit.utils.SystemUtil;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangbing on 2016/11/8.
 */

//BaseFragment<MyJoinPresenterImpl> implements MyJoinContract.View
public class MyjoinedFragment extends SimpleFragment{

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }

//    @BindView(R.id.rv_list)
//    RecyclerView rvList;
//
//    @BindView(R.id.loading_layout)
//    LoadingLayout loadingLayout;
//
//
//    private int mCurrenPage = 2;
//    private int mTotalPage;
//
//    private MyJoinListAdapter myJoinListAdapter;
//    private List<MyJoinedActivityBean.DataBean.ListBean> list;
//    private LinearLayoutManager linearLayoutManager;
//
//
//    @Override
//    protected void initInject() {
//        getFragmentComponent().inject(this);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_myjoinedactivity;
//    }
//
//    @Override
//    protected void initEventAndData() {
//
//        if (!SystemUtil.isNetworkConnected()) {
//            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
//            //reload按钮监听
//            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
//                @Override
//                public void onReload(View v) {
//                    mPresenter.getMyJoinedGame();
//                }
//            });
//            return;
//        }
//
//        //reload按钮监听
//        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
//                mPresenter.getMyJoinedGame();
//
//            }
//        });
//        //加载布局
//        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.image);
//        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
//        drawable.start();
//        loadingLayout.setLoadingPage(view);
//        loadingLayout.setStatus(LoadingLayout.Loading);//加载中状态
//        rvList.setArrowImageView(R.drawable.venu_choose);
//        //加载更多
//        rvList.setLoadingMoreProgressStyle(ProgressStyle.LineSpinFadeLoader);
//        rvList.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                mPresenter.getMyJoinedGame();
//            }
//
//            @Override
//            public void onLoadMore() {
//                // load more data here
//                if (mCurrenPage <= mTotalPage) {
//                    mPresenter.getMyJoinedMore(mCurrenPage);
//                } else if (mCurrenPage > mTotalPage) {
//                    //没有更多了
//                    rvList.loadMoreComplete();
//                }
//            }
//        });
//
//        linearLayoutManager = new LinearLayoutManager(mContext);
//        rvList.setLayoutManager(new LinearLayoutManager(mContext));
//        myJoinListAdapter = new MyJoinListAdapter(mContext, list);
//        rvList.setAdapter(myJoinListAdapter);
//
//        mPresenter.getMyJoinedGame();
//
//    }
//
//    @Override
//    public void showMyJoined(final MyJoinedActivityBean myJoinedActivityBean) {
//        if (rvList !=null){
//            rvList.refreshComplete();
//            rvList.loadMoreComplete();
//        }
//
//
//        if (myJoinedActivityBean.getData().getList() == null ) {
//            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
//            return;
//        }
//        if (myJoinedActivityBean.getData().getList().size() == 0) {
////            mNoNet.setVisibility(View.VISIBLE);
//            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
//            return;
//        }
//        //如果刷新 重置初始页
//        mCurrenPage = 2;
//        mTotalPage=myJoinedActivityBean.getData().getTotal_page();
//        if (loadingLayout != null) {
//            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
//        }
//
//
//        myJoinListAdapter.setOnItemClickListener(new MyJoinListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View view) {
//                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
//                intent.putExtra("type", Constants.TYPE_ACTIVITY);
//                intent.putExtra("id", myJoinedActivityBean.getData().getList().get(position).getActivity_id());
//                mContext.startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.right_in,R.anim.right_out);
//            }
//        });
//        myJoinListAdapter.addData(myJoinedActivityBean.getData().getList());
//
//    }
//
//    @Override
//    public void showMoreMyJoined(MyJoinedActivityBean myJoinedActivityBean) {
//        rvList.loadMoreComplete();
//        rvList.refreshComplete();
//        if (myJoinedActivityBean.getCode() == 100) {
//            mCurrenPage = myJoinedActivityBean.getData().getCurrent_page();
//            mCurrenPage++;
//            mTotalPage=myJoinedActivityBean.getData().getTotal_page();
//            myJoinListAdapter.addMoreDate(myJoinedActivityBean.getData().getList());
//        } else {
//            ToastUtils.showShrotMsg(mContext,myJoinedActivityBean.getMessage());
//        }
//    }
//
//    @Override
//    public void reLogin() {
//        CheckLoginUtil.reLogin(mContext,Constants.LOGIN_FROMCOLLECT);
//    }
//
//    @Override
//    public void showError(String msg) {
//        if (loadingLayout != null) {
//            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
//        }
//        if (rvList!=null){
//            rvList.loadMoreComplete();
//            rvList.refreshComplete();
//        }
//        ToastUtils.showShrotMsg(mContext,msg);
//
//    }
}
