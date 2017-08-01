package com.mperfit.perfit.ui.release.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.AddPostLikeBean;
import com.mperfit.perfit.model.bean.EventReleasePreviewBean;
import com.mperfit.perfit.model.bean.PublishTopicBean;
import com.mperfit.perfit.model.bean.QiNiuBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.imagechooser.imagechooser.ChooserSetting;
import com.mperfit.perfit.ui.imagechooser.imagechooser.IcFinal;
import com.mperfit.perfit.ui.main.activity.MainActivity;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.mperfit.perfit.utils.ClickFilter;
import com.mperfit.perfit.utils.DateUtil;
import com.mperfit.perfit.utils.FileUtils;
import com.mperfit.perfit.utils.PictureUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadingDialogNormal;
import com.mperfit.perfit.widget.ToastUtils;
import com.mperfit.perfit.widget.flowlayout.ColorTextView;
import com.mperfit.perfit.widget.flowlayout.FlowLayout;
import com.mperfit.perfit.widget.flowlayout.TagAdapter;
import com.mperfit.perfit.widget.flowlayout.TagFlowLayout;
import com.orhanobut.logger.Logger;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by zhangbing on 2017/2/13.
 */

public class ReleasePreviewActivity extends SimpleActivity {
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_caninput)
    TextView tvCanInput;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nestedScrollView;
    @BindView(R.id.ll_container)
    LinearLayout linearLayout;

    private RetrofitHelper mRetrofitHelper;
    private List<PublishTopicBean.DataBean.TopicListBean> mTopicList;
    private LayoutInflater mInflater;

    private String[] colors = new String[]{"#c9c2c2", "#88c3ab", "#caa196", "#ddcfa6", "#c9c2c2", "#88c3ab", "#caa196", "#ddcfa6"};
    private UploadManager uploadManager;
    private String key;
    private String mImgUrl;
    private String mQiniuToken;
    private long tagId;

    private LoadingDialogNormal loadingDialog;
    private Bitmap compressImage;

    @Override
    protected int getLayout() {
        return R.layout.activity_release;
    }

    @Override
    protected void initEventAndData() {
        ArrayList<String> data = getIntent().getStringArrayListExtra(IcFinal.RESULT_DATA_IMG);
        if (data != null && data.size() != 0) {
            mImgUrl = data.get(0);
            ImageLoader.loadBannaerAds(mContext, mImgUrl, ivImg);
        }


        setBackTouch(tvBack);

        mRetrofitHelper = new RetrofitHelper(mContext);

        mInflater = LayoutInflater.from(mContext);
        //获取标签
        loadingDialog = new LoadingDialogNormal(mContext);

        Subscription subscription = mRetrofitHelper.getPublishTopics()
                .compose(RxUtil.<PublishTopicBean.DataBean>handleResult())
                .subscribe(new Action1<PublishTopicBean.DataBean>() {
                    @Override
                    public void call(PublishTopicBean.DataBean dataBean) {
                        mTopicList = dataBean.getTopicList();
                        idFlowlayout.setAdapter(new TagAdapter(mTopicList) {
                            @Override
                            public boolean setSelected(int position, Object o) {
                                return o.equals("全部");
                            }

                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
                                ColorTextView textView = (ColorTextView) mInflater.inflate(R.layout.item_tags, idFlowlayout, false);
                                textView.setBackgroundColor(colors[mTopicList.get(position).getRandom()]);
                                textView.setText(mTopicList.get(position).getName());
                                return textView;
                            }
                        });



                        idFlowlayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                int width = idFlowlayout.getMeasuredWidth();
                                int height = idFlowlayout.getMeasuredHeight();

                                ViewGroup.LayoutParams params = idFlowlayout.getLayoutParams();
                                params.width = width;
                                params.height = height *2 ;
                                idFlowlayout.setLayoutParams(params);
                                Logger.e("releaseWidth:" + width + "height:" + height);
                                idFlowlayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });





                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });






        addSubscrebe(subscription);
        //热门标签
        initQiNiuImageUpdate();
        initEvent();

        Subscription subscribe = mRetrofitHelper.fetchQiniuToken()
                .compose(RxUtil.<QiNiuBean>rxSchedulerHelper())
                .subscribe(new Action1<QiNiuBean>() {
                    @Override
                    public void call(QiNiuBean qiNiuBean) {
                        mQiniuToken = qiNiuBean.getData().getToken();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        addSubscrebe(subscribe);

    }

    private void initEvent() {
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((etInput.getText().toString().length()) < 41) {
                    tvCanInput.setText("还能输入");
                    tvCount.setText(40 - etInput.getText().toString().length() + "");
                } else {
                    tvCanInput.setText("已超出");
                    tvCount.setText(Math.abs(40 - etInput.getText().toString().length()) + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 七牛图片上传服务初始化
     */
    private void initQiNiuImageUpdate() {

        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(30) // 服务器响应超时。默认60秒
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }


    @OnClick(R.id.tv_right_text)
    void publish() {
        if (checkLogin()) return;


        if (mImgUrl == null) {
            ToastUtils.showShrotMsg(mContext, "还没选择图片呢~");
            return;
        }


        if (etInput.getText().toString().length() > 40) {
            ToastUtils.showShrotMsg(mContext,"亲~不能超过40个字哟~");
            return;
        }
        if (ClickFilter.filter()){
            return;
        }
        loadingDialog.show();

        if (idFlowlayout != null && idFlowlayout.getSelectedList() != null && idFlowlayout.getSelectedList().size() > 0) {
            for (Integer item : idFlowlayout.getSelectedList()) {
                PublishTopicBean.DataBean.TopicListBean bean = mTopicList.get(item);
                tagId = bean.getId();
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImgUrl, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        final String name = DateUtil.getCurrentTimes() + DateUtil.getFixLenthString(3);
        key = "note/" + name + ".JPEG";

        File file = new File(mImgUrl);

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

                        String fileAbsolutePath = file.getAbsolutePath();
                        uploadImag(fileAbsolutePath, height, width);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用

                        uploadImag(mImgUrl, height, width);

                    }
                }).launch();    //启动压缩

    }

    private void uploadImag(String fileAbsolutePath, final int height, final int width) {
        uploadManager.put(fileAbsolutePath, key, mQiniuToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //上传给服务器
                        if (info.isOK()) {
                            MobclickAgent.onEvent(mContext, Constants.EVENT_PUBLISHI_NOTE);
                            MobclickAgent.onKillProcess(mContext);
                            ToastUtils.showShrotMsg(mContext, "发布成功");
                            Logger.e("qiniu", "Upload Success");
                            Subscription subscription = mRetrofitHelper.getPublishPost(etInput.getText().toString().trim(), key, tagId, height, width)
                                    .compose(RxUtil.<AddPostLikeBean.DataBean>handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                                    .unsubscribeOn(AndroidSchedulers.mainThread())
                                    .compose(RxUtil.<AddPostLikeBean.DataBean>rxSchedulerHelper())
                                    .subscribe(new Action1<AddPostLikeBean.DataBean>() {
                                        @Override
                                        public void call(AddPostLikeBean.DataBean dataBean) {
                                            loadingDialog.close();
                                            //通知首页刷新

                                            Intent intent = new Intent(mContext, MainActivity.class);
                                            intent.putExtra(Constants.REFRESH_HOME, 1);
                                            startActivity(intent);
                                            overridePendingTransition(0,R.anim.right_out);
                                            System.exit(0);
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            loadingDialog.close();

                                        }
                                    });

                            addSubscrebe(subscription);

                        } else {
                            ToastUtils.showShrotMsg(mContext, "上传失败~亲重试下~");
                            if (loadingDialog!=null){
                                loadingDialog.close();
                            }
                            Logger.e("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }


                    }
                }, null);
    }


    private boolean checkLogin() {
        if (CheckLoginUtil.CheckLogin(mContext)) {
            CheckLoginUtil.reLogin(mContext, Constants.LOGIN_FROMCOLLECT);
            return true;
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (compressImage != null && !compressImage.isRecycled()) {
            compressImage.recycle();
            compressImage = null;
        }

    }
}
