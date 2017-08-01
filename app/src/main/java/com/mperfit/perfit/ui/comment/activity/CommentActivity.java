package com.mperfit.perfit.ui.comment.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.model.bean.AddShopCommentBean;
import com.mperfit.perfit.model.bean.CommentSuccessBean;
import com.mperfit.perfit.model.bean.QiNiuBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.ui.comment.adapter.CommentPhotoGalleryAdapter;
import com.mperfit.perfit.ui.imagechooser.imagechooser.IcFinal;
import com.mperfit.perfit.utils.DateUtil;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.widget.LoadingDialogNormal;
import com.mperfit.perfit.widget.MyRatingBar;
import com.mperfit.perfit.widget.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangbing on 2016/12/5.
 */

public class CommentActivity extends SimpleActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mrb_class_detail_ratingbar)
    MyRatingBar mrbClassDetailRatingbar;
    @BindView(R.id.met_content)
    MaterialEditText metContent;
    @BindView(R.id.ll_select_photo)
    LinearLayout llSelectPhoto;
    @BindView(R.id.tv_imagelist)
    RecyclerView tvImagelist;
    @BindView(R.id.tv_right_text)
    TextView tvToolbarRight;
    @BindView(R.id.tv_text_amount)
    TextView tvAmount;
    @BindView(R.id.tv_text_count)
    TextView textCount;
    @BindView(R.id.ll_text_caintainer)
    LinearLayout llTextCaintainer;
    private long orderId;
    private RetrofitHelper retrofitHelper;
    private String token;
    private CommentPhotoGalleryAdapter galleryAdapter;
    private UploadManager uploadManager;
    private List<String> imageNetPathList;
    private StringBuffer stringBufferImage = new StringBuffer();
    private boolean isupdateOk;
    protected CompositeSubscription mCompositeSubscription;

    private ArrayList<String> bitmapResult = new ArrayList<>();
    private LoadingDialogNormal loadingDialog;


    @Override
    protected int getLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initEventAndData() {
        tvToolbarRight.setText("发布");
        Intent intent = mContext.getIntent();
        orderId = intent.getLongExtra("order_id", 0);
        tvTitle.setText("评论");
        mCompositeSubscription = new CompositeSubscription();
        galleryAdapter = new CommentPhotoGalleryAdapter(mContext, bitmapResult);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tvImagelist.setLayoutManager(linearLayoutManager);
        tvImagelist.setAdapter(galleryAdapter);

        loadingDialog = new LoadingDialogNormal(mContext);
        setUploadData();
        getToken();
        initEvent();
        setBackTouch();
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

    private void getToken() {
        //获取七牛token
        retrofitHelper = new RetrofitHelper(mContext);
        Subscription subscribe = retrofitHelper.fetchQiniuToken()
                .compose(RxUtil.<QiNiuBean>rxSchedulerHelper())
                .subscribe(new Action1<QiNiuBean>() {
                    @Override
                    public void call(QiNiuBean qiNiuBean) {
                        token = qiNiuBean.getData().getToken();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        mCompositeSubscription.add(subscribe);
    }

    private void initEvent() {
        galleryAdapter.setOnItemClickListener(new CommentPhotoGalleryAdapter.OnItemClickListener() {
            @Override
            public void onHideAddBtn() {
                llSelectPhoto.setVisibility(View.GONE);
            }

            @Override
            public void onShowAddBtn() {
                llSelectPhoto.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDeleteClick(int position) {
                selImageList.remove(position);
//                mThumbnailImageList.remove(position);
            }

            @Override
            public void onItemClick(int position, View view) {

            }
        });

        //
        metContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((metContent.getText().toString().length()) < 15) {
                    llTextCaintainer.setVisibility(View.VISIBLE);
                    textCount.setText(15 - metContent.getText().toString().length() + "");
                } else {
                    llTextCaintainer.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    /**
     * 初始化图片上传服务器
     */
    private void setUploadData() {
        //存放照片网络路径
        imageNetPathList = new ArrayList<>();
        //七牛图片上传的配置
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(30) // 服务器响应超时。默认60秒
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        if (uploadManager == null){
            uploadManager = new UploadManager(config);
        }
    }

    @OnClick(R.id.tv_right_text)
    void finishw() {

        if (mrbClassDetailRatingbar.getStarRating() == 0) {
            ToastUtils.showShrotMsg(mContext, "亲~请留下您的评分~");
            return;
        }
        if (TextUtils.isEmpty(metContent.getText().toString())) {
            ToastUtils.showShrotMsg(mContext, "亲~请留下您的宝贵评价~");
            return;
        }
        if (metContent.getText().toString().length() < 15) {
            ToastUtils.showShrotMsg(mContext, "亲~多说两句嘛~");
            return;
        }
        if (loadingDialog!=null)
        loadingDialog.show();

        for (int i = 0; i < selImageList.size(); i++) {
            //遍历原始路径
            String originalPath = selImageList.get(i);
            String key = "seller_comment/" + DateUtil.getCurrentTimes() + DateUtil.getFixLenthString(3);

            uploadManager.put(originalPath, key, token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    //添加上传的图片的路径
                    imageNetPathList.add(key);
                    Logger.e("七牛图片上传complete imageNetPathList：" + imageNetPathList.size());
                    if (imageNetPathList != null && imageNetPathList.size() == selImageList.size()) {
                        for (int j = 0; j < imageNetPathList.size(); j++) {
                            if ((j + 1) == imageNetPathList.size()) {
                                stringBufferImage.append(imageNetPathList.get(j));
                                isupdateOk = true;
                            } else {
                                stringBufferImage.append(imageNetPathList.get(j) + ",");
                                isupdateOk = false;
                            }

                            if (isupdateOk ) {

                                Subscription subscription = retrofitHelper.fetchAddShopComment(orderId, (int) mrbClassDetailRatingbar.getStarRating(),
                                        metContent.getText().toString(), String.valueOf(stringBufferImage))
                                        .compose(RxUtil.<AddShopCommentBean>rxSchedulerHelper())
                                        .unsubscribeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<AddShopCommentBean>() {
                                            @Override
                                            public void call(AddShopCommentBean addShopCommentBean) {
                                                ToastUtils.showShrotMsg(mContext, addShopCommentBean.getMessage() + "");
                                                if (loadingDialog!=null)
                                                    loadingDialog.close();
                                                if (addShopCommentBean.getCode() == 100) {
//                                                    RxBusUtils.getDefault().postSticky(new CommentSuccessBean());
                                                    finish();
                                                }
                                            }
                                        }, new Action1<Throwable>() {
                                            @Override
                                            public void call(Throwable throwable) {
                                                if (loadingDialog!=null)
                                                loadingDialog.close();
                                            }
                                        });

                                mCompositeSubscription.add(subscription);

                            }
                        }
                    }
                }
            }, null);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            adapter.data.clear();
//            adapter.data.addAll(data.getStringArrayListExtra(IcFinal.RESULT_DATA_IMG));
//            adapter.notifyDataSetChanged();
            ArrayList<String> extra = data.getStringArrayListExtra(IcFinal.RESULT_DATA_IMG);
            if (extra != null && extra.size() != 0) {
                galleryAdapter.addData(data.getStringArrayListExtra(IcFinal.RESULT_DATA_IMG));
                for (String path : extra) {
                    selImageList.add(path);
                }
            }
        }
    }

    private ArrayList<String> selImageList = new ArrayList<>(); //当前选择的所有图片
    private int maxImgCount = 3;               //允许选择图片最大数

    @OnClick(R.id.ll_select_photo)
    void select() {
        int canChoose = maxImgCount - selImageList.size();
        Intent intent = new Intent(IcFinal.ACTION_ALBUM);
        intent.putExtra(IcFinal.INTENT_MAX_IMG, canChoose);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onDestroy() {
        if (uploadManager!=null)
        uploadManager = null;
        if (mCompositeSubscription!=null)
        mCompositeSubscription.unsubscribe();
        if (loadingDialog!=null){
            loadingDialog.close();
        }

        super.onDestroy();
    }
}
