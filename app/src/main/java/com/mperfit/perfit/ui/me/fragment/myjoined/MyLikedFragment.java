package com.mperfit.perfit.ui.me.fragment.myjoined;

import com.mperfit.perfit.base.SimpleFragment;

/**
 * Created by zhangbing on 2016/11/8.
 */

//BaseFragment<MyLikedListPresenterImpl> implements MyLikedListContract.View

public class MyLikedFragment extends SimpleFragment {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }


//    @BindView(R.id.loading_layout)
//    LoadingLayout loadingLayout;
//
//    private int mCurrenPage = 2;
//    private int mTotalPage;
//
//    private MyLikeListAdapter myLikeListAdapter;
//
//    private List<LikeListBean.DataBean.ListBean> list;
//    private LinearLayoutManager linearLayoutManager;
//
//    @Override
//    protected void initInject() {
//        getFragmentComponent().inject(this);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_mylikeactivity;
//    }
//
//    @Override
//    protected void initEventAndData() {
//        //reload按钮监听
//        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
//                mPresenter.getLikeListInfo(1);
//            }
//        });
//        if (!SystemUtil.isNetworkConnected()) {
//            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
//            return;
//        }
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
//                mPresenter.getLikeListInfo(1);
//            }
//
//            @Override
//            public void onLoadMore() {
//                // load more data here
//                if (mCurrenPage <= mTotalPage) {
//                    mPresenter.getMoreData(mCurrenPage);
//                } else if (mCurrenPage > mTotalPage) {
//                    //没有更多了
//                    rvList.loadMoreComplete();
//                }
//            }
//        });
//
//        linearLayoutManager = new LinearLayoutManager(mContext);
//        rvList.setLayoutManager(linearLayoutManager);
//        rvList.addItemDecoration(new RecycleViewDivider(mContext,LinearLayoutManager.HORIZONTAL,10
//                ,getResources().getColor(R.color.divider_gray)));
//        myLikeListAdapter = new MyLikeListAdapter(mContext, list);
//        rvList.setAdapter(myLikeListAdapter);
//        mPresenter.getLikeListInfo(1);
//    }
//
//
//
//    @Override
//    public void showContent(final LikeListBean likeListBean) {
//        rvList.refreshComplete();
//        rvList.loadMoreComplete();
//
//        if (likeListBean.getData().getList() == null ) {
//            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
//            return;
//        }
//        if (likeListBean.getData().getList().size() == 0 ) {
//            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
//            return;
//        }
//        //如果刷新 重置初始页
//        mCurrenPage = 2;
//        mTotalPage = likeListBean.getData().getTotal_page();
//        if (loadingLayout != null) {
//            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
//        }
//
//
//
//        myLikeListAdapter.setOnItemClickListener(new MyLikeListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View view) {
//                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
//                intent.putExtra("type", Constants.TYPE_ACTIVITY);
//                intent.putExtra("id", likeListBean.getData().getList().get(position).getActivity_id());
//                mContext.startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.right_in,R.anim.right_out);
//            }
//        });
//        final List<LikeListBean.DataBean.ListBean> list = likeListBean.getData().getList();
//
//
//
//        myLikeListAdapter.addData(list);
//
//        myLikeListAdapter.setOnDeleteClickListener(new MyLikeListAdapter.OnDeleteClickListener() {
//            @Override
//            public void onDeleteClick(int position) {
//                myLikeListAdapter.removeData(position);
//            }
//        });
//
//
//
//        //设置收藏状态监听
//
//        myLikeListAdapter.setOnCheckChangeListener(new MyLikeListAdapter.OnCheckChangeListener() {
//            @Override
//            public void onCheckChange(int position, boolean isCheck) {
//
//                if (isCheck) {
////                    RealmLikeBean realmLikeBean = new RealmLikeBean();
////                    realmLikeBean.setId(String.valueOf(list.get(position).getActivity_id()));
////                    realmLikeBean.setType(Constants.LIKE_TYPE_SHOP);
//                    mPresenter.insetLike(String.valueOf(list.get(position).getActivity_id()), position);
//                } else {
//                    mPresenter.deleteLike(String.valueOf(list.get(position).getActivity_id()), position);
//
//                }
//            }
//        });
//
//
//    }
//
//    @Override
//    public void showMore(LikeListBean likeListBean) {
//
//        rvList.loadMoreComplete();
//        if (likeListBean.getCode() == 100) {
//            List<LikeListBean.DataBean.ListBean> list = likeListBean.getData().getList();
//            myLikeListAdapter.addMore(list);
//            mCurrenPage = likeListBean.getData().getCurrent_page();
//            mCurrenPage++;
//            mTotalPage = likeListBean.getData().getTotal_page();
//        } else {
//            ToastUtils.showShrotMsg(mContext,likeListBean.getMessage());
//        }
//    }
//
//    @Override
//    public void setLikeState(Boolean isLiked, int position) {
//        myLikeListAdapter.updateCheckState(isLiked, position);
//
//    }
//
//    @Override
//    public void showLikeResult(int result, String msg) {
//        if (result == Constants.LIKE_RESULT_SINGFILED) {
//            ToastUtils.showLongMsg(mContext, msg);
//            CheckLoginUtil.reLogin(mContext,Constants.LOGIN_FROMCOLLECT);
//        } else {
//            ToastUtils.showShrotMsg(mContext, msg);
//        }
//
//    }
//
//
//    @Override
//    public void showError(String msg) {
//        rvList.refreshComplete();
//        rvList.loadMoreComplete();
//        loadingLayout.setStatus(LoadingLayout.Success);//加载成功
//        ToastUtils.showShrotMsg(mContext,msg);
//    }

}
