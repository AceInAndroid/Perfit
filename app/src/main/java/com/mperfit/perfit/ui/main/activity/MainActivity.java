package com.mperfit.perfit.ui.main.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.model.bean.APIExceptionBean;
import com.mperfit.perfit.model.bean.EventReleasePreviewBean;
import com.mperfit.perfit.model.bean.ReLoadPersonalDataBean;
import com.mperfit.perfit.ui.community.fragment.CommunityFragment;
import com.mperfit.perfit.ui.competition.fragment.CompetitionFragment;
import com.mperfit.perfit.ui.imagechooser.imagechooser.IcFinal;
import com.mperfit.perfit.ui.main.adapter.ActivityViewPagerAdaper;
import com.mperfit.perfit.ui.newhome.fragment.NewHomeFragment;
import com.mperfit.perfit.ui.personal.fragment.NewPersonalFragment;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.widget.NoScrollViewPager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends SimpleActivity {
    @BindView(R.id.vp_main_viewPager)
    NoScrollViewPager mContentViewPager;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_release)
    ImageView ivRelease;
    @BindView(R.id.ll_me)
    LinearLayout llMe;
    @BindView(R.id.iv_tab_home)
    ImageView ivTabHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_tab_me)
    ImageView ivTabMe;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.iv_tab_community)
    ImageView ivTabCommunity;
    @BindView(R.id.tv_community)
    TextView tvCommunity;
    @BindView(R.id.ll_community)
    LinearLayout llCommunity;
    @BindView(R.id.iv_tab_competition)
    ImageView ivTabCompetition;
    @BindView(R.id.tv_competition)
    TextView tvCompetition;
    @BindView(R.id.ll_competition)
    LinearLayout llCompetition;

//    @BindView(R.id.tl_1)
//    CommonTabLayout tabLayout;

    //记录返回键间隔 小于2秒为连续按
    private long mExitTime = 0;

//    String[] tabTitle = new String[]{"首页", "我的"};
//    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
//
//    private int[] mIconUnselectIds = {
//            R.drawable.tab_home_defalt, R.drawable.tab_user_default};
//    private int[] mIconSelectIds = {
//            R.drawable.tab_selesthome, R.drawable.tab_user_selectuser};

    List<Fragment> fragments = new ArrayList<>();
    ActivityViewPagerAdaper mViewPagerAdaper;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        //当收到错误 统一清理sp 回到初始状态
        Subscription subscription1 = RxBusUtils.getDefault().toObservableSticky(APIExceptionBean.class)
                .subscribe(new Action1<APIExceptionBean>() {
                    @Override
                    public void call(APIExceptionBean apiExceptionBean) {

                        if (apiExceptionBean.getTYPE() == APIExceptionBean.TYPE_RELOGIN) {
                            SharedPreferenceUtil.setAuthId(Constants.AUTH_ID);
                            SharedPreferenceUtil.setAuthCode(Constants.AUTH_CODE);
                            SharedPreferenceUtil.setUserId(0);
                            SharePreferenceUtils.putLong(getApplicationContext(), Constants.USER_ID, 0);

                        } else if (apiExceptionBean.getTYPE() == APIExceptionBean.TYPE_AUTHERRO) {
                            SharedPreferenceUtil.setAuthId(Constants.AUTH_ID);
                            SharedPreferenceUtil.setAuthCode(Constants.AUTH_CODE);
                            SharedPreferenceUtil.setUserId(0);
                            SharePreferenceUtils.putLong(getApplicationContext(), Constants.USER_ID, 0);

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscription1);


        Subscription subscribe = RxBusUtils.getDefault().toObservableSticky(ReLoadPersonalDataBean.class)
                .subscribe(new Action1<ReLoadPersonalDataBean>() {
                    @Override
                    public void call(ReLoadPersonalDataBean reLoadPersonalDataBean) {
                        SharedPreferenceUtil.setUserId(reLoadPersonalDataBean.getId());
                        SharePreferenceUtils.putLong(getApplicationContext(), Constants.USER_ID, reLoadPersonalDataBean.getId());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscribe);
        fragments.add(new NewHomeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new CompetitionFragment());
        fragments.add(new NewPersonalFragment());
        mViewPagerAdaper = new ActivityViewPagerAdaper(getSupportFragmentManager(), fragments);
        mContentViewPager.setNoScroll(true);
        mContentViewPager.setAdapter(mViewPagerAdaper);
        mContentViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initEvent();

    }

    public static final String BROADCAST_ACTION = "action_select";

    private void initEvent() {
        int refreshPage = getIntent().getIntExtra(Constants.REFRESH_HOME, 0);
        if (refreshPage >0){
            setSelectPage(1);
            switchTabState(2);
        }
//        if (mBroadcastReceiver ==null){
//            mBroadcastReceiver = new ReleaseReceiver();
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(BROADCAST_ACTION);
//            registerReceiver(mBroadcastReceiver,intentFilter);
//        }
    }

    private int mPageMark = 0 ;

//    class ReleaseReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            mPageMark = intent.getIntExtra("page", 0);
//            Logger.e("收到消息 选择社区" + mPageMark);
//
//        }
//    }



    private void switchTabState(int index) {
        switch (index) {
            case 1:
                ivTabHome.setBackground(getResources().getDrawable(R.drawable.tab_selesthome));
                ivTabMe.setBackground(getResources().getDrawable(R.drawable.tab_user_default));
                ivTabCommunity.setBackground(getResources().getDrawable(R.drawable.tab_community));
                ivTabCompetition.setBackground(getResources().getDrawable(R.drawable.tab_game));
                tvHome.setTextColor(getResources().getColor(R.color.groupbuying_price));
                tvMe.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvCommunity.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvCompetition.setTextColor(getResources().getColor(R.color.tab_unselect_textc));

                break;
            case 2:
                ivTabHome.setBackground(getResources().getDrawable(R.drawable.tab_home_defalt));
                ivTabCommunity.setBackground(getResources().getDrawable(R.drawable.tab_communityed_selected));
                ivTabCompetition.setBackground(getResources().getDrawable(R.drawable.tab_game));
                ivTabMe.setBackground(getResources().getDrawable(R.drawable.tab_user_default));
                tvHome.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvCommunity.setTextColor(getResources().getColor(R.color.groupbuying_price));
                tvCompetition.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvMe.setTextColor(getResources().getColor(R.color.tab_unselect_textc));

                break;
            case 3:

                ivTabHome.setBackground(getResources().getDrawable(R.drawable.tab_home_defalt));
                ivTabCommunity.setBackground(getResources().getDrawable(R.drawable.tab_community));
                ivTabCompetition.setBackground(getResources().getDrawable(R.drawable.tab_gamed_selected));
                ivTabMe.setBackground(getResources().getDrawable(R.drawable.tab_user_default));
                tvHome.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvCommunity.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvCompetition.setTextColor(getResources().getColor(R.color.groupbuying_price));
                tvMe.setTextColor(getResources().getColor(R.color.tab_unselect_textc));


                break;
            case 4:

                ivTabHome.setBackground(getResources().getDrawable(R.drawable.tab_home_defalt));
                ivTabCommunity.setBackground(getResources().getDrawable(R.drawable.tab_community));
                ivTabCompetition.setBackground(getResources().getDrawable(R.drawable.tab_game));
                ivTabMe.setBackground(getResources().getDrawable(R.drawable.tab_user_selectuser));
                tvHome.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvCommunity.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvCompetition.setTextColor(getResources().getColor(R.color.tab_unselect_textc));
                tvMe.setTextColor(getResources().getColor(R.color.groupbuying_price));


                break;

        }

    }

    @OnClick(R.id.ll_home)
    void home() {
        setSelectPage(0);
        //改变图标和文字颜色
        switchTabState(1);
    }



    @OnClick(R.id.ll_me)
    void me() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMLOGIN);
            return;
        }
        setSelectPage(3);
        switchTabState(4);

    }

    @OnClick(R.id.ll_community)
    void toCommunity() {
        setSelectPage(1);
        switchTabState(2);

    }


    @OnClick(R.id.ll_competition)
    void toCompetition(){
        setSelectPage(2);
        switchTabState(3);
    }


    private int maxImgCount = 1;               //允许选择图片最大数
    private int REQUSTCODE = 0x101;

    @OnClick(R.id.iv_release)
    void release() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMLOGIN);
            return;
        }

        //去发布
        Intent intent = new Intent(IcFinal.ACTION_ALBUM);
        intent.putExtra(IcFinal.INTENT_MAX_IMG, 1);
        intent.putExtra(IcFinal.CHOOSE_TYPE, IcFinal.TYPE_RELEASE);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void setSelectPage(int item) {
        mContentViewPager.setCurrentItem(item, false);

        Logger.e("setSelectPage调用了" + item);
    }


    /**
     * 获得手机的 宽高 px(像素)
     */

    /*---onKeyDown方法：按两次返回键退出--*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long period;
        if (keyCode == event.KEYCODE_BACK)  //确定按下返回键
        {
            period = System.currentTimeMillis() - mExitTime; //两次按下的时间间隔
            if (period > 2000)    //2s之内两次按下退出有效
            {
                Toast.makeText(mContext, "再按一次退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                App.getInstance().exitApp();   //从全局退出所有的activity防止内存泄露
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}
