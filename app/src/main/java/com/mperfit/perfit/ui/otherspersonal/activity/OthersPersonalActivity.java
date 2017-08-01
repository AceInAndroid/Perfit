package com.mperfit.perfit.ui.otherspersonal.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.APIExceptionBean;
import com.mperfit.perfit.model.bean.EventFollowBean;
import com.mperfit.perfit.model.bean.OthersUserCenterBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;
import com.mperfit.perfit.presenter.contract.OthersPersonalContract;
import com.mperfit.perfit.presenter.presenter.OthersPersonalPresenterImpl;
import com.mperfit.perfit.ui.otherspersonal.adapter.PersonalLikeAdapter;
import com.mperfit.perfit.ui.otherspersonal.adapter.PersonalPostAdapter;
import com.mperfit.perfit.ui.otherspersonal.adapter.SpaceItemDecoration;
import com.mperfit.perfit.ui.personal.listener.AppBarStateChangeListener;
import com.mperfit.perfit.ui.postdetailactiviy.activity.PostDetaillActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhangbing on 2017/2/14.
 */

public class OthersPersonalActivity extends BaseActivity<OthersPersonalPresenterImpl> implements OthersPersonalContract.View {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.ll_follow_container)
    LinearLayout llFollowContainer;
    @BindView(R.id.tv_slogan)
    TextView tvSlogan;
    @BindView(R.id.fl_top)
    FrameLayout flTop;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;
    @BindView(R.id.tb_detail_toolbar)
    Toolbar tbDetailToolbar;
    @BindView(R.id.ctl_detail_toobar)
    CollapsingToolbarLayout ctlDetailToobar;
    @BindView(R.id.app_detail_bar)
    AppBarLayout appDetailBar;
    @BindView(R.id.tv_follow_count)
    TextView tvFollowCount;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.ib_follow_bg)
    ImageButton ibFollowBg;
    @BindView(R.id.tv_follow_text)
    TextView tvFollowText;
    @BindView(R.id.iv_follow_icon)
    ImageView ivFollowIcon;
    @BindView(R.id.v_indicator1)
    View vIndicator1;
    @BindView(R.id.v_indicator2)
    View vIndicator2;
    @BindView(R.id.swipe_target)
    RecyclerView mPostRecyclerView;
    @BindView(R.id.swipe_target_like)
    RecyclerView mLikeRecyclerView;
    @BindView(R.id.ll_post)
    LinearLayout llPost;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.tv_id_name)
    TextView mTvIdName;
    @BindView(R.id.iv_sex)
    ImageView mIvSex;
    @BindView(R.id.tv_liveness)
    TextView mTvLivenNess;


    private List<OthersUserCenterBean.DataBean.NoteListBean> mNoteList;
    private List<OthersUserCenterBean.DataBean.LikeListBean> mLikeList;
    private PersonalPostAdapter mPersonalPostAdapter;
    private PersonalLikeAdapter mPersonalLikeAdapter;
    private int mIsFollow;
    private OthersUserCenterBean.DataBean.UserBean userBean;
    private long mUserId;
    private int mLikeTotalPage;
    private int mNoteTotalPage;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_others;
    }

    @Override
    protected void initEventAndData() {
        Intent intent = mContext.getIntent();
        mUserId = intent.getLongExtra("user_id", 0);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mPostRecyclerView.setLayoutManager(gridLayoutManager);
        mPostRecyclerView.setHasFixedSize(true);
//        mPostRecyclerView.setNestedScrollingEnabled(false);
        mPostRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(mContext, 3);
        gridLayoutManager2.setSmoothScrollbarEnabled(true);
        gridLayoutManager2.setAutoMeasureEnabled(true);
        mLikeRecyclerView.setLayoutManager(gridLayoutManager2);
        mLikeRecyclerView.setHasFixedSize(true);
//        mLikeRecyclerView.setNestedScrollingEnabled(false);
        mLikeRecyclerView.addItemDecoration(new SpaceItemDecoration(15));


        mPresenter.getOthersPersonalInfo(mUserId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        initEvnet();
    }

    private void initEvnet() {

        appDetailBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    if (tvTbTitle.getVisibility() != View.GONE)
                        tvTbTitle.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态显示改变seticon 和标题
                    ObjectAnimator.ofFloat(tvTbTitle, "alpha", 0, 1).setDuration(200).start();
                    tvTbTitle.setVisibility(View.VISIBLE);

                } else {
                    //中间状态
                    if (tvTbTitle.getVisibility() != View.GONE)
                        tvTbTitle.setVisibility(View.GONE);

                }
            }
        });

        setBackTouch();

    }


    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            return true;
        }
        return false;
    }



    private void setBackTouch() {
        Rect delegateArea = new Rect();
        // Hit rectangle in parent's coordinates
        ibBack.getHitRect(delegateArea);


        // 扩大触摸区域矩阵值
        delegateArea.left -= 200;
        delegateArea.top -= 200;
        delegateArea.right += 200;
        delegateArea.bottom += 200;

        /**
         * 构造扩大后的触摸区域对象
         * 第一个构造参数表示  矩形面积
         * 第二个构造参数表示 被扩大的触摸视图对象
         * <也是本demo最核心用到的类之一>
         */
        TouchDelegate expandedArea = new TouchDelegate(delegateArea, ibBack);
        if (View.class.isInstance(ibBack.getParent())) {
            // 设置视图扩大后的触摸区域
            ((View) ibBack.getParent()).setTouchDelegate(expandedArea);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.slide_right_out);
            }
        });
    }


    @Override
    public void showError(String msg) {

    }


    private int REQUSTCODE = 0X10;

    private int mPostPage = 2;
    private int mLikePage = 2;

    @Override
    public void showContent(OthersUserCenterBean.DataBean dataBean) {
        if (dataBean==null){
            return;
        }
        mIsFollow = dataBean.getIs_follow();
        changeFollowState();
        //用户个人信息
        userBean = dataBean.getUser();
        userBean.getUser_id();
        mLikeTotalPage = dataBean.getLike_total_page();
        mNoteTotalPage = dataBean.getNote_total_page();
        int fansCount = userBean.getFans_count();
        //设置粉丝数
        tvFansCount.setText(fansCount + "");
        int followCount = userBean.getFollow_count();
        //设置关注数
        tvFollowCount.setText(followCount + "");
        String imgUrl = userBean.getImg_url();
        //设置头像
        ImageLoader.loadHead(mContext, imgUrl, ivHead);
        String name = userBean.getName();
        //设置名字
        tvTitle.setText(name);
        tvTbTitle.setText(name);
        //设置签名
        String signature = userBean.getSignature();
        tvSlogan.setText(signature);

        mTvIdName.setText(name);
        mTvLivenNess.setText(String.valueOf(userBean.getTotal_hot()));
        if (Integer.valueOf(userBean.getSex())==1){
            //男
            mIvSex.setBackground(getResources().getDrawable(R.drawable.my_icon_boy));
        }else {
            //女
            mIvSex.setBackground(getResources().getDrawable(R.drawable.my_icon_girl));
        }


        mNoteList = dataBean.getNote_list();
        mLikeList = dataBean.getLike_list();
        mPersonalPostAdapter = new PersonalPostAdapter(mContext, mNoteList, mPostRecyclerView);
        mPersonalLikeAdapter = new PersonalLikeAdapter(mContext, mLikeList, mLikeRecyclerView);

        mLikeRecyclerView.setAdapter(mPersonalLikeAdapter);
        mPostRecyclerView.setAdapter(mPersonalPostAdapter);


        mPersonalPostAdapter.setOnLoadingListener(new PersonalPostAdapter.OnLoadingListener() {
            @Override
            public void loadMorePost() {
                if (mPostPage <= mNoteTotalPage)
                    mPresenter.getMorePost(mPostPage, mUserId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }
        });


        mPersonalLikeAdapter.setOnLoadingListener(new PersonalLikeAdapter.OnLoadingListener() {
            @Override
            public void loadMoreLike() {
                if (mLikePage <= mLikeTotalPage)
                mPresenter.getMoreLike(mPostPage, mUserId, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
            }
        });
        mPersonalPostAdapter.setOnItemClickListener(new PersonalPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //进入帖子详情
                Intent intent = new Intent(mContext, PostDetaillActivity.class);
                intent.putExtra("postId", mNoteList.get(position).getNote_id());
                intent.putExtra("position", position);
                intent.putExtra(Constants.TYPE, Constants.TYPE_POSTDEFORMPOST);
//                startActivityForResult(intent,REQUSTCODE);
                startActivity(intent);
            }
        });

        mPersonalLikeAdapter.setOnItemClickListener(new PersonalLikeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //进入帖子详情
                Intent intent = new Intent(mContext, PostDetaillActivity.class);
                intent.putExtra("postId", mLikeList.get(position).getNote_id());
                intent.putExtra("position", position);
                intent.putExtra(Constants.TYPE, Constants.TYPE_POSTDEFORMLIKE);
//                startActivityForResult(intent,REQUSTCODE);
                startActivity(intent);
            }
        });







    }

    private void changeFollowState() {

        if (checkLogin()){
            //未关注
            tvFollowText.setText("关注");
            //设置margin
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ivFollowIcon.getLayoutParams();
            layoutParams.setMargins(SystemUtil.dp2px(mContext,18), 0, 0, 0);
            ivFollowIcon.setLayoutParams(layoutParams);
            tvFollowText.setTextColor(getResources().getColor(R.color.groupbuying_price));
            ivFollowIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_icon_add));
            ibFollowBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg_follow));
            return;
        }


        if (mIsFollow == 1) {
            //已关注
            tvFollowText.setText("已关注");
            tvFollowText.setTextColor(getResources().getColor(R.color.white));
            //设置margin
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ivFollowIcon.getLayoutParams();
            layoutParams.setMargins(SystemUtil.dp2px(mContext,9), 0, 0, 0);
            ivFollowIcon.setLayoutParams(layoutParams);

            ivFollowIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_icon_concern));
            ibFollowBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_myjoin_bg));

        } else {
            //未关注
            tvFollowText.setText("关注");
            //设置margin
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ivFollowIcon.getLayoutParams();
            layoutParams.setMargins(SystemUtil.dp2px(mContext,18), 0, 0, 0);
            ivFollowIcon.setLayoutParams(layoutParams);
            tvFollowText.setTextColor(getResources().getColor(R.color.groupbuying_price));
            ivFollowIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_icon_add));
            ibFollowBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg_follow));
        }
    }


    @Override
    public void showFollowResult() {
        mIsFollow = 1;
        changeFollowState();
        //发送消息
        RxBusUtils.getDefault().postSticky(new EventFollowBean(1, mUserId));
    }

    @Override
    public void showRemoveFollowResult() {
        mIsFollow = 0;
        //更改状态
        changeFollowState();
        RxBusUtils.getDefault().postSticky(new EventFollowBean(0, mUserId));

    }

    @Override
    public void showLoadMorePostResult(PersonalMyPostBean.DataBean postDataBean) {
        if (postDataBean.getList() == null) {
            return;
        }
        mPostPage++;
        mPersonalPostAdapter.addNewData(postDataBean.getList());
    }

    @Override
    public void showLoadMoreLikeResult(PersonalMyPostBean.DataBean likeDataBean) {

        if (likeDataBean.getList() == null) {
            return;
        }

        mLikePage++;
        mPersonalLikeAdapter.addNewData(likeDataBean.getList());


    }

    @OnClick(R.id.ll_post)
    void post() {
        mPostRecyclerView.setVisibility(View.VISIBLE);
        mLikeRecyclerView.setVisibility(View.GONE);
        vIndicator1.setVisibility(View.VISIBLE);
        vIndicator2.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.ll_like)
    void like() {
        mPostRecyclerView.setVisibility(View.GONE);
        mLikeRecyclerView.setVisibility(View.VISIBLE);
        vIndicator1.setVisibility(View.INVISIBLE);
        vIndicator2.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ib_follow_bg)
    void follow() {
        if (checkLogin()){
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
            return;
        }

        if (mIsFollow == 1) {
            //取消关注
            mPresenter.removeFollow(userBean.getUser_id(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        } else {
            //添加关注
            mPresenter.addFollow(userBean.getUser_id(), ActivityLifeCycleEvent.DESTROY, lifecycleSubject);

        }
    }


    @OnClick(R.id.ll_fans_container)
    void toFansList() {
        Intent intent = new Intent(mContext, FansListActivity.class);
        intent.putExtra(Constants.USER_ID,mUserId);
        startActivity(intent);
    }

    private static final int RQUST_CODE_TOFOLLOW = 0x102;


    @OnClick(R.id.ll_follow_container)
    void toFollowList() {
        Intent intent = new Intent(mContext, FollowLitsActivity.class);
        intent.putExtra(Constants.USER_ID,mUserId);
        startActivity(intent);
    }






}

