package com.mperfit.perfit.ui.me.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.BottomSheetBehavior;
import android.text.Html;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.BaseActivity;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.component.RxBus;
import com.mperfit.perfit.model.bean.ProfileBean;
import com.mperfit.perfit.model.bean.ReLoadPersonalDataBean;
import com.mperfit.perfit.presenter.contract.ProfileContract;
import com.mperfit.perfit.presenter.presenter.ProfilePresenterImpl;
import com.mperfit.perfit.ui.imagechooser.imagechooser.IcFinal;
import com.mperfit.perfit.ui.imagechooser.imagechooser.crop.CropPath;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.DateUtil;
import com.mperfit.perfit.widget.ImageCompress;
import com.mperfit.perfit.widget.LoadingDialogNormal;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by zhangbing on 16/11/3.
 */

public class ProfileActivity extends BaseActivity<ProfilePresenterImpl> implements ProfileContract.View {


    @BindView(R.id.tv_title)
    TextView tvId;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.met_name)
    MaterialEditText metName;
    @BindView(R.id.tv_select_sex)
    TextView tvSelectSex;
    @BindView(R.id.tv_select_date)
    TextView tvSelectDate;
    @BindView(R.id.met_work)
    MaterialEditText met_work;
    @BindView(R.id.tv_select_single)
    TextView tvSelectSingle;
    @BindView(R.id.met_more)
    MaterialEditText metMore;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.ll_select_sex)
    LinearLayout llSelectSex;
    @BindView(R.id.ll_select_birth)
    LinearLayout llSelectBirth;
    @BindView(R.id.ll_select_emotionstate)
    LinearLayout llSelectEmotionstate;
    @BindView(R.id.ib_back)
    ImageButton ibBack;


    private OptionPicker mEmotionStatesPicker;
    private OptionPicker mSexPicker;
    private DatePicker datePicker;
    private ViewTarget viewTarget;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private String mCropHeadPath;
    private RequestBody requestBody;
    private String key;
    private UploadManager uploadManager;
    private String token;
    private boolean isHeadUploadOk;
    private LoadingDialogNormal loadingDialogNormal;
    private String mHeandImgUrl;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initEventAndData() {
        initEvent();
        setTransparencyState(false);
        mPresenter.getProfileInfo();
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(30) // 服务器响应超时。默认60秒
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
        setBackTouch();
        loadingDialogNormal = new LoadingDialogNormal(mContext);

    }


    private void initEvent() {
        selectSex();
        selectBirthDay();
        selectEmotionStates();
        setHead();
        tvRightText.setText("保存");



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
         * <一>
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

    @OnClick(R.id.tv_right_text)
    void save() {

        int sex = 0;
        if (tvSelectSex.getText().toString().equals("男")) {
            sex = 1;
        } else {
            sex = 2;
        }

        if (metName==null){
            return;
        }
        if (metName.getText().toString().equals("")){
            ToastUtils.showShrotMsg(mContext,"请输入昵称～");
            return;
        }

        if (loadingDialogNormal!=null)
        loadingDialogNormal.show();


        if (mCropHeadPath == null){
            try {
                mPresenter.saveProfileInfo(metName.getText().toString(), sex,tvSelectDate.getText().toString(),
                        met_work.getText().toString(), tvSelectSingle.getText().toString(),
                        metMore.getText().toString());

            }catch (NullPointerException e){
                Logger.e("ProfileActivity:" + e.getMessage());
            }

            return;
        }
        try {
            key = "/user/" + DateUtil.getCurrentTimes() + DateUtil.getFixLenthString(3);
            final int finalSex = sex;
            File file = new File(mCropHeadPath);
            final int finalSex1 = sex;
            Luban.get(this)
                    .load(file)                     //传人要压缩的图片
                    .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                    .setCompressListener(new OnCompressListener() { //设置回调

                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }
                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            String path = file.getPath();
                            if (path==null){
                                return;
                            }

                            uploadManager.put(path, key, token,
                                    new UpCompletionHandler() {
                                        @Override
                                        public void complete(String key, ResponseInfo info, JSONObject res) {
                                            try {

                                                mPresenter.saveProfileInfo(metName.getText().toString(), finalSex,
                                                        key, tvSelectDate.getText().toString(),
                                                        met_work.getText().toString(), tvSelectSingle.getText().toString(),
                                                        metMore.getText().toString());

                                            } catch (NullPointerException e){
                                                Logger.e("ProfileActivity" + e.getMessage());
                                            }

                                        }
                                    }, null);


                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过去出现问题时调用
                            mPresenter.saveProfileInfo(metName.getText().toString(), finalSex1,tvSelectDate.getText().toString(),
                                    met_work.getText().toString(), tvSelectSingle.getText().toString(),
                                    metMore.getText().toString());


                        }
                    }).launch();    //启动压缩



        }catch (NullPointerException e){

        }





    }

    //选择性别
    private void selectSex() {
        mSexPicker = new OptionPicker(mContext, new String[]{"男", "女"});
        mSexPicker.setOffset(2);
        mSexPicker.setTitleText("请选择性别");
        mSexPicker.setTitleTextSize(18);
        mSexPicker.setTitleTextColor(getResources().getColor(R.color.activity_text));
        mSexPicker.setTopHeight(50);
        mSexPicker.setHeight(400);
        mSexPicker.setSubmitTextSize(18);
        mSexPicker.setCancelVisible(false);
        mSexPicker.setTextSize(20);
        mSexPicker.setSubmitText("完成");
        mSexPicker.setSelectedIndex(0);
        mSexPicker.setTextSize(11);
        mSexPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                tvSelectSex.setText(option);
            }

        });
        llSelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSexPicker.show();
            }
        });
    }

    private void selectBirthDay() {
        datePicker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
        datePicker.setRangeStart(1970, 8, 29);//开始范围
        datePicker.setRangeEnd(2022, 1, 1);//结束范围
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvSelectDate.setText(year + "-" + month + "-" + day);
            }
        });
        llSelectBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();

            }
        });

    }

    private void setHead() {

    }


    //选择性别
    private void selectEmotionStates() {
        mEmotionStatesPicker = new OptionPicker(mContext, new String[]{"单身", "热恋中", "已婚族"});
        mEmotionStatesPicker.setOffset(1);
        mEmotionStatesPicker.setTitleText("请选择情感状态");
        mEmotionStatesPicker.setTitleTextSize(18);
        mEmotionStatesPicker.setTitleTextColor(getResources().getColor(R.color.activity_text));
        mEmotionStatesPicker.setTopHeight(50);
        mEmotionStatesPicker.setHeight(400);
        mEmotionStatesPicker.setSubmitTextSize(18);
        mEmotionStatesPicker.setCancelVisible(false);
        mEmotionStatesPicker.setTextSize(20);
        mEmotionStatesPicker.setSubmitText("完成");
        mEmotionStatesPicker.setSelectedIndex(0);
        mEmotionStatesPicker.setTextSize(11);
        mEmotionStatesPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                tvSelectSingle.setText(option);
            }

        });
        llSelectEmotionstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmotionStatesPicker.show();
            }
        });
    }


    @Override
    public void showProfileInfo(final ProfileBean profileBean) {
        token = profileBean.getData().getToken();
        final ProfileBean.DataBean.UserBean userBean = profileBean.getData().getUser();
        mHeandImgUrl = profileBean.getData().getUser().getImg_url();
        loadImageViewTarget(profileBean.getData().getUser().getImg_url());
        tvId.setText(userBean.getName());
        if (userBean.getSex().equals("1")) {
            tvSelectSex.setText("男");
        } else {
            tvSelectSex.setText("女");
        }
        metName.setText(userBean.getName());
        metMore.setText(userBean.getName());
        tvSelectDate.setText(userBean.getBirth());
        tvSelectSingle.setText(userBean.getFeeling());
        met_work.setText(userBean.getProfession());
        metMore.setText(userBean.getSignature());
    }

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private int maxImgCount = 1;               //允许选择图片最大数

    @OnClick(R.id.ll_head)
    void uploadHead() {

        Intent intent = new Intent(IcFinal.ACTION_ALBUM);
        intent.putExtra(IcFinal.INTENT_MAX_IMG, maxImgCount);
        //设置裁剪
        intent.putExtra(IcFinal.INTENT_IS_CROP, true);
        intent.putExtra(IcFinal.CHOOSE_TYPE, IcFinal.TYPE_CROP);
        intent.putExtra(IcFinal.INTENT_CROP_SHAPE, CropPath.SHAPE_RECT);
        intent.putExtra(IcFinal.INTENT_CROP_WIDTH, App.SCREEN_WIDTH);
        intent.putExtra(IcFinal.INTENT_CROP_HEIGHT, App.SCREEN_WIDTH);
        startActivityForResult(intent, 1);
    }



    @OnClick(R.id.iv_head)
    void toChooseHead(){

        Intent intent = new Intent(IcFinal.ACTION_ALBUM);
        intent.putExtra(IcFinal.INTENT_MAX_IMG, maxImgCount);
        //设置裁剪
        intent.putExtra(IcFinal.INTENT_IS_CROP, true);
        intent.putExtra(IcFinal.CHOOSE_TYPE, IcFinal.TYPE_CROP);
        intent.putExtra(IcFinal.INTENT_CROP_SHAPE, CropPath.SHAPE_RECT);
        intent.putExtra(IcFinal.INTENT_CROP_WIDTH, App.SCREEN_WIDTH);
        intent.putExtra(IcFinal.INTENT_CROP_HEIGHT, App.SCREEN_WIDTH);
        startActivityForResult(intent, 1);



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ArrayList<String> extra = data.getStringArrayListExtra(IcFinal.RESULT_DATA_IMG);
            if (extra != null && extra.size() != 0) {
                Bitmap getimage = ImageCompress.getimage(extra.get(0));
                ivHead.setImageBitmap(getimage);
                //上传图片路径
                mCropHeadPath = extra.get(0);
            }
        }
    }


    private void loadImageViewTarget(String img_url) {

        viewTarget = new ViewTarget<CircleImageView, GlideDrawable>(ivHead) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                Drawable current = resource.getCurrent();
                this.view.setImageDrawable(current);
            }
        };
        ImageLoader.load(mContext, img_url, viewTarget);

    }

    @Override
    public void showSaveReult(int type, String msg) {
        if (loadingDialogNormal!=null)
        loadingDialogNormal.close();
        switch (type) {
            case Constants.LIKE_RESULT_SINGFILED:
                ToastUtils.showShrotMsg(mContext, msg);
                CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                break;
            case Constants.LIKE_TYPE_FILED:
                ToastUtils.showShrotMsg(mContext, msg);
                CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
                break;
            case Constants.LIKE_RESULT_SUCCESS:
                ToastUtils.showShrotMsg(mContext, msg);
                RxBus.getDefault().post(new ReLoadPersonalDataBean());
                break;

        }
    }

    @Override
    public void showError(String msg) {
        if (loadingDialogNormal!=null)
        loadingDialogNormal.close();


    }


}
