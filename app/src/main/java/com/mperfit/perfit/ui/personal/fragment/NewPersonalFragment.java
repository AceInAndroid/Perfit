package com.mperfit.perfit.ui.personal.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseFragment;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.NewPersonalBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;
import com.mperfit.perfit.presenter.contract.NewPersonalContract;
import com.mperfit.perfit.presenter.presenter.NewPersonalPresenterImpl;
import com.mperfit.perfit.ui.me.activity.SettingActivity;
import com.mperfit.perfit.ui.otherspersonal.activity.FansListActivity;
import com.mperfit.perfit.ui.otherspersonal.activity.FollowLitsActivity;
import com.mperfit.perfit.ui.personal.activity.MyActivityActivity;
import com.mperfit.perfit.ui.personal.activity.MyContentActivity;
import com.mperfit.perfit.ui.personal.activity.MyGameActivity;
import com.mperfit.perfit.ui.personal.activity.MyPointsActivity;
import com.mperfit.perfit.ui.personal.listener.AppBarStateChangeListener;
import com.mperfit.perfit.widget.RefreshHeaderView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangbing on 2017/2/14.
 */

public class NewPersonalFragment extends BaseFragment<NewPersonalPresenterImpl> implements NewPersonalContract.View, OnRefreshListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_slogan)
    TextView tvSlogan;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_follow_count)
    TextView tvFollowCount;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.fl_top)
    FrameLayout flTop;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.ib_set)
    ImageButton ibSet;
    @BindView(R.id.tb_detail_toolbar)
    Toolbar tbDetailToolbar;
    @BindView(R.id.ctl_detail_toobar)
    CollapsingToolbarLayout ctlDetailToobar;
    @BindView(R.id.app_detail_bar)
    AppBarLayout appDetailBar;
    @BindView(R.id.tv_activity)
    TextView mTvActivity;
    @BindView(R.id.tv_content_count)
    TextView mTvNoteCount;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private int fansCount;
    private static final int REQUSTCODE = 0x101;
    private int followCount;
    private long mUserId;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_personal;
    }

    @Override
    protected void initEventAndData() {
        initEvent();
        mPresenter.getOthersPersonalInfo(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
    }

    private void initEvent() {

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);

        appDetailBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeToLoadLayout.setRefreshEnabled(true);
                } else {
                    swipeToLoadLayout.setRefreshEnabled(false);
                }
            }
        });
        appDetailBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if (state == State.EXPANDED) {
                    //展开状态
                    if (tvTbTitle.getVisibility() != View.GONE)
                        tvTbTitle.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    swipeToLoadLayout.setRefreshEnabled(false);
                    //折叠状态显示改变seticon 和标题
                    ObjectAnimator.ofFloat(tvTbTitle, "alpha", 0, 1).setDuration(200).start();
                    tvTbTitle.setText("个人中心");
                    tvTbTitle.setVisibility(View.VISIBLE);
                    ibSet.setBackground(getResources().getDrawable(R.drawable.my_btn_seted));
                } else {
                    //中间状态
                    ibSet.setBackground(getResources().getDrawable(R.drawable.my_btn_set));
                    if (tvTbTitle.getVisibility() != View.GONE)
                        tvTbTitle.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.ib_set)
    void toSetting() {
        Intent intent = new Intent(mContext, SettingActivity.class);
        startActivity(intent);
    }

    private static final int RQUST_CODE_TOFANS = 0x103;

    @OnClick(R.id.ll_fans_container)
    void toFansList() {
        Intent intent = new Intent(mContext, FansListActivity.class);
        intent.putExtra(Constants.USER_ID, mUserId);
        startActivity(intent);
    }

    private static final int RQUST_CODE_TOFOLLOW = 0x102;


    @OnClick(R.id.ll_followandfans_all_container)
    void toFollowList() {
        Intent intent = new Intent(mContext, FollowLitsActivity.class);
        intent.putExtra(Constants.USER_ID, mUserId);
        startActivityForResult(intent, RQUST_CODE_TOFOLLOW);
    }

    @OnClick(R.id.ll_myame)
    void toMyGame(){
        Intent intent = new Intent(mContext, MyGameActivity.class);
        mContext.startActivity(intent);
    }

    @OnClick(R.id.ll_myactivity)
    void toMyActivity(){
        Intent intent = new Intent(mContext, MyActivityActivity.class);
        mContext.startActivity(intent);
    }

    @OnClick(R.id.ll_my_points)
    void toMyPoints(){
        Intent intent = new Intent(mContext, MyPointsActivity.class);
        mContext.startActivity(intent);
    }


    @OnClick(R.id.ll_content_container)
    void toContent(){
        Intent intent = new Intent(mContext,MyContentActivity.class);
        mContext.startActivity(intent);

    }

    @Override
    public void showContent(NewPersonalBean.DataBean dataBean) {
        changeRefreshState();

        final NewPersonalBean.DataBean.UserBean userBean = dataBean.getUser();
        fansCount = userBean.getFans_count();
        tvFansCount.setText(fansCount + "");
        followCount = userBean.getFollow_count();
        tvFollowCount.setText(followCount + "");
        mUserId = dataBean.getUser().getUser_id();
        int totalScore = dataBean.getUser().getTotal_hot();
        mTvActivity.setText(String.valueOf(totalScore) + getString(R.string.activitytext));
        int noteCount = dataBean.getUser().getNote_count();
        mTvNoteCount.setText(String.valueOf(noteCount));
        String imgUrl = userBean.getImg_url();
        ImageLoader.loadHead(mContext, imgUrl, ivHead);
        String sex = userBean.getSex();
        if (sex.equals("1")) {
            //man
            ivSex.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_icon_boy));
        } else {
            //woman
            ivSex.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_icon_girl));
        }

        String signature = userBean.getSignature();
        tvSlogan.setText(signature);

        String name = userBean.getName();
        tvName.setText(name);

        //保存id用于打开他们个人中心的时候判断是不是自己
        userBean.getUser_id();


    }


    @Override
    public void showMorePost(PersonalMyPostBean.DataBean noteListBean) {

    }

    @Override
    public void showPost(PersonalMyPostBean.DataBean noteListBean) {
        if (noteListBean.getList() == null) {
            return;
        }

    }

    @Override
    public void showMoreLike(PersonalMyPostBean.DataBean likeListBean) {

        if (likeListBean.getList() == null) {
            return;
        }

    }

    @Override
    public void showLike(PersonalMyPostBean.DataBean likeListBean) {

        if (likeListBean.getList() == null) {
            return;
        }

    }


    private boolean isPost = true;


    @Override
    public void showError(String msg) {
//        ToastUtils.showShrotMsg(mContext, msg);

        changeRefreshState();


    }


    @Override
    public void onRefresh () {
        changeRefreshState();
        mPresenter.getOthersPersonalInfo(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
    }

    private void changeRefreshState () {
        if (swipeToLoadLayout!=null && swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUSTCODE) {
                if (requestCode == RQUST_CODE_TOFOLLOW) {
                    tvFollowCount.setText(data.getIntExtra(Constants.FOLLOW_COUNT, followCount) + "");
                }
            }
        }
    }
}
