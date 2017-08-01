package com.mperfit.perfit.ui.personal.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.NewPersonalBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;
import com.mperfit.perfit.presenter.contract.NewPersonalContract;
import com.mperfit.perfit.presenter.presenter.NewPersonalPresenterImpl;
import com.mperfit.perfit.ui.otherspersonal.adapter.SpaceItemDecoration;
import com.mperfit.perfit.ui.personal.adapter.MyContentLikeAdapter;
import com.mperfit.perfit.ui.personal.adapter.MyContentPostAdapter;
import com.mperfit.perfit.ui.postdetailactiviy.activity.PostDetaillActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.SharePreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangbing on 2017/3/14.
 */

public class MyContentActivity extends BaseActivity<NewPersonalPresenterImpl> implements NewPersonalContract.View {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.ll_post)
    LinearLayout llPost;
    @BindView(R.id.v_indicator1)
    View vIndicator1;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.v_indicator2)
    View vIndicator2;
    @BindView(R.id.ll_like_container)
    LinearLayout llLikeContainer;
    @BindView(R.id.swipe_target_post)
    RecyclerView mPostRecyclerView;
    @BindView(R.id.swipe_target_like)
    RecyclerView mLikeRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager gridLayoutManager2;

    private List<PersonalMyPostBean.DataBean.ListBean> mLikeList;
    private List<PersonalMyPostBean.DataBean.ListBean> mNoteList;
    private MyContentLikeAdapter myContentLikeAdapter;
    private MyContentPostAdapter myContentPostAdapter;

    private int mPostTotalPage;
    private int mLikeTotalPage;

    private int mPostCurrentPage = 2;
    private int mLikeCurrentPage = 2;

    private long mUserId;

    private static final int REQUSTCODE = 0x101;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_content;
    }

    @Override
    protected void initEventAndData() {

        setBackTouch(ibBack);

        if (!CheckLoginUtil.isLogin(mContext, Constants.LOGIN_FROMCOLLECT)) {
            return;
        }

        gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mPostRecyclerView.setLayoutManager(gridLayoutManager);
        mPostRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        myContentPostAdapter = new MyContentPostAdapter(R.layout.item_personal_post, mLikeList, mPostRecyclerView);
        mPostRecyclerView.setHasFixedSize(true);
        mPostRecyclerView.setAdapter(myContentPostAdapter);

        gridLayoutManager2 = new GridLayoutManager(mContext, 3);
        gridLayoutManager2.setSmoothScrollbarEnabled(true);
        gridLayoutManager2.setAutoMeasureEnabled(true);
        mLikeRecyclerView.setLayoutManager(gridLayoutManager2);
        mLikeRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        mLikeRecyclerView.setHasFixedSize(true);
        myContentLikeAdapter = new MyContentLikeAdapter(R.layout.item_personal_post, mLikeList,mLikeRecyclerView);
        mLikeRecyclerView.setAdapter(myContentLikeAdapter);
        mUserId = SharePreferenceUtils.getLong(mContext.getApplicationContext(), Constants.USER_ID, 0);
        mPresenter.getMyselfPost(1, mUserId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        mPresenter.getMyselfLike(1, mUserId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);

        initEvent();


    }

    private void initEvent() {
        myContentLikeAdapter.setOnLoadingListener(new MyContentLikeAdapter.OnLoadingListener() {
            @Override
            public void loadMorePost() {

                if (mLikeCurrentPage <= mLikeTotalPage) {
                    mPresenter.getMoreMyselfLike(mLikeCurrentPage, mUserId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
                }

            }
        });

        myContentPostAdapter.setOnLoadingListener(new MyContentPostAdapter.OnLoadingListener() {
            @Override
            public void loadMorePost() {
                if (mPostCurrentPage <= mPostTotalPage) {
                    mPresenter.getMoreMyselfPost(mPostCurrentPage,mUserId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
                }
            }
        });

        myContentLikeAdapter.setOnItemClickListener(new MyContentLikeAdapter.OnItemClickListener() {
            @Override
            public void onPostItemClick(int position, View view) {
                //进入我喜欢的帖子详情
                Intent intent = new Intent(mContext, PostDetaillActivity.class);
                intent.putExtra("postId", mLikeList.get(position).getNote_id());
                intent.putExtra("position", position);
                intent.putExtra(Constants.TYPE, Constants.TYPE_POSTDEFORMLIKE);
                startActivityForResult(intent, REQUSTCODE);
            }

        });


        myContentPostAdapter.setOnItemClickListener(new MyContentPostAdapter.OnItemClickListener() {
            @Override
            public void onPostItemClick(int position, View view) {
                //进入帖子详情
                try {
                    Intent intent = new Intent(mContext, PostDetaillActivity.class);
                    intent.putExtra("postId", mNoteList.get(position).getNote_id());
                    intent.putExtra("position", position);
                    intent.putExtra(Constants.TYPE, Constants.TYPE_POSTDEFORMPOST);
                    startActivityForResult(intent, REQUSTCODE);
                } catch (IndexOutOfBoundsException e){

                }

            }
        });
    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showContent(NewPersonalBean.DataBean dataBean) {

    }

    @Override
    public void showMorePost(PersonalMyPostBean.DataBean noteListBean) {
        mPostTotalPage = noteListBean.getTotal_page();
        mPostCurrentPage = noteListBean.getCurrent_page();
        mPostCurrentPage++;
        myContentPostAdapter.addMoreData(noteListBean.getList());
    }

    @Override
    public void showPost(PersonalMyPostBean.DataBean noteListBean) {
        mNoteList = noteListBean.getList();
        mPostTotalPage = noteListBean.getTotal_page();
        mPostCurrentPage = 2;
        myContentPostAdapter.setData(noteListBean.getList());
    }

    @Override
    public void showMoreLike(PersonalMyPostBean.DataBean likeListBean) {


        mLikeTotalPage = likeListBean.getTotal_page();
        mLikeCurrentPage = likeListBean.getCurrent_page();
        mLikeCurrentPage++;
        myContentLikeAdapter.addMoreData(likeListBean.getList());

    }

    @Override
    public void showLike(PersonalMyPostBean.DataBean likeListBean) {
        mLikeList = likeListBean.getList();
        mLikeTotalPage = likeListBean.getTotal_page();
        mLikeCurrentPage = 2;
        myContentLikeAdapter.setData(likeListBean.getList());
    }



    @OnClick(R.id.ll_post)
    void post() {
//        isPost = true;

        mPostRecyclerView.setVisibility(View.VISIBLE);
        mLikeRecyclerView.setVisibility(View.GONE);
        vIndicator1.setVisibility(View.VISIBLE);
        vIndicator2.setVisibility(View.INVISIBLE);


//        changeState();
    }


    @OnClick(R.id.ll_like)
    void getLike() {

        mPostRecyclerView.setVisibility(View.GONE);
        mLikeRecyclerView.setVisibility(View.VISIBLE);
        vIndicator1.setVisibility(View.INVISIBLE);
        vIndicator2.setVisibility(View.VISIBLE);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUSTCODE) {
                int type = data.getIntExtra(Constants.INTENT_ISLIKE_POST_TYPE, -1);
                int position = data.getIntExtra(Constants.INTENT_ISLIKE_POSITION, -1);
                if(type == Constants.TYPE_POSTDEFORMLIKE){
                    int isLike = data.getIntExtra(Constants.INTENT_ISLIKE, -1);
                    if (isLike == 0) {
                        myContentLikeAdapter.updateLikeState(position);
                    }
                } else {
                    //删除帖子
                    myContentPostAdapter.removePost(position);
                }
            }
        }
    }





}
