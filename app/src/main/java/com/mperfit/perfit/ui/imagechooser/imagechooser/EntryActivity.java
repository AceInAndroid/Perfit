package com.mperfit.perfit.ui.imagechooser.imagechooser;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.FileUriExposedException;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.model.bean.PhotoBackBean;
import com.mperfit.perfit.model.bean.PhotoSelectCountMsgBean;
import com.mperfit.perfit.ui.imagechooser.imagechooser.abslayer.IPhotoShoot;
import com.mperfit.perfit.ui.imagechooser.imagechooser.album.AlbumEntry;
import com.mperfit.perfit.ui.imagechooser.imagechooser.album.AlbumPopup;
import com.mperfit.perfit.ui.imagechooser.imagechooser.album.FolderFragment;
import com.mperfit.perfit.ui.imagechooser.imagechooser.album.ImageFolder;
import com.mperfit.perfit.ui.imagechooser.imagechooser.album.ImageInfo;
import com.mperfit.perfit.ui.imagechooser.imagechooser.preview.AlbumImagePagerActivity;
import com.mperfit.perfit.ui.imagechooser.utils.LogUtils;
import com.mperfit.perfit.ui.main.activity.MainActivity;
import com.mperfit.perfit.ui.release.activity.ReleasePreviewActivity;
import com.mperfit.perfit.utils.ClickFilter;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.ToastUtil;
import com.mperfit.perfit.widget.ToastUtils;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import static com.mperfit.perfit.ui.main.activity.MainActivity.BROADCAST_ACTION;

/**
 * 相册入口
 * 没有时间来适配7.0，暂时把target设置到了7。0以下  但适配7。0以上的工作也不能拉下 7.0以上获取图片路径要用fileprovider (继承自contentprovider) 替换uri.fromfile()  拍照 图片裁剪也是 交给兄弟你了
 */
public class EntryActivity extends SimpleActivity implements IPhotoShoot, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.iv_indicator)
    ImageView ivIndicator;
    @BindView(R.id.ll_title_container)
    LinearLayout llTitleContainer;
    @BindView(R.id.ib_next)
    ImageButton ibNext;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.mTitle)
    Toolbar mTitle;
    @BindView(R.id.mEntry)
    RelativeLayout mEntry;
    @BindView(R.id.fl_next_container)
    FrameLayout flNextContainer;

    private AlbumEntry entry;
    private Toolbar toolbar;
    private MenuItem mSure;

    private String tackPicStr;
    private static final int REQ_TACK_PIC = 0x15;
    private CompositeSubscription mCompositeSubscription;
    private String mChooseType;
//    private ReleaseReceiver mBroadcastReceiver;

    @Override
    protected int getLayout() {
        return R.layout.image_chooser_activity_entry;
    }

    @Override
    protected void initEventAndData() {
        setTitle();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            init();
        } else {
            methodRequiresTwoPermission();
        }
    }

    private void init() {

        Rect outRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        ChooserSetting.albumPopupHeight = (int) (outRect.height() * 0.6f);
        FolderFragment m = new FolderFragment();
        m.setPhotoShoot(this);
        m.setSelectImgs(getIntent().getStringArrayListExtra(IcFinal.INTENT_EXIST_DATA));
        mChooseType = getIntent().getStringExtra(IcFinal.CHOOSE_TYPE);
        entry = new AlbumEntry(this, R.id.mEntry, m, new AlbumPopup(this, toolbar, m)) {
            @Override
            public void onAlbumClick(ImageFolder folder) {
                super.onAlbumClick(folder);
                //当相册被点击

            }

            @Override
            public boolean onAdd(List<ImageInfo> data, ImageInfo info) {
                boolean a = super.onAdd(data, info);
                if (!a && mSure != null && getMax() != 1) {
                    mSure.setEnabled(true);
                }
                return a;
            }

            @Override
            public boolean onCancel(List<ImageInfo> data, ImageInfo info) {
                boolean a = super.onCancel(data, info);

                if (!a && mSure != null && data.size() <= 1) {
                    mSure.setEnabled(false);
                }
                return a;
            }
        };

//        //如果是单选 则隐藏下一步

        if (mChooseType != null && mChooseType.equals(IcFinal.TYPE_RELEASE)) {
            //如果是发布模式 下一步进入发布预览
            flNextContainer.setVisibility(View.VISIBLE);
            llTitleContainer.setVisibility(View.VISIBLE);
            ibNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ClickFilter.filter()) {
                        return;
                    }
                    //进入发布预览
                    entry.chooseFinishToPublish();
                }
            });

        } else {
            //如果是其他模式
            ibNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择完关闭
                    entry.chooseFinish();
                }
            });
            if (entry.getMax() > 1) {
                flNextContainer.setVisibility(View.VISIBLE);
                llTitleContainer.setVisibility(View.VISIBLE);
            } else {
//            toolbar.getMenu().getItem(1).setVisible(false);
                llTitleContainer.setVisibility(View.GONE);
                flNextContainer.setVisibility(View.GONE);
            }

        }


        Subscription subscription = RxBusUtils.getDefault().toObservable(PhotoSelectCountMsgBean.class)
                .subscribe(new Action1<PhotoSelectCountMsgBean>() {
                    @Override
                    public void call(PhotoSelectCountMsgBean photoSelectCountMsgBean) {
                        if (photoSelectCountMsgBean != null) {
                            if (photoSelectCountMsgBean.getCount() == 0) {
                                tvCount.setVisibility(View.GONE);
                            } else {
                                tvCount.setVisibility(View.VISIBLE);
                                tvCount.setText(photoSelectCountMsgBean.getCount() + "");

                            }
                        }
                    }
                });

        addSubscrebe(subscription);


        Subscription subscription2 = RxBusUtils.getDefault().toObservable(PhotoBackBean.class)
                .subscribe(new Action1<PhotoBackBean>() {
                    @Override
                    public void call(PhotoBackBean photoBackBean) {
                        //更新数量
                        if (photoBackBean != null && photoBackBean.getMlist() != null) {
                            if (photoBackBean.getMlist().size() == 0) {
                                tvCount.setVisibility(View.GONE);
                            } else {
                                tvCount.setVisibility(View.VISIBLE);
                                tvCount.setText(photoBackBean.getMlist().size() + "");
                            }

                        }

                    }
                });

        addSubscrebe(subscription2);

//
//        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        registerReceiver(receiver, filter);
    }


    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscrebe(Subscription subscription) {

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }


    //相册拍照刷新相册
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            entry.refreshData();
            Logger.e("receiver调用了");
        }
    };


    @OnClick(R.id.tv_back)
    void back() {
        finish();
        overridePendingTransition(0, R.anim.right_out);
    }


    private void setTitle() {
        toolbar = (Toolbar) findViewById(R.id.mTitle);
        toolbar.setBackgroundColor(getResources().getColor(ChooserSetting.TITLE_COLOR));
        llTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册选择
                entry.showAlbumChooser();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("result-" + resultCode + "/" + requestCode);
        entry.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_TACK_PIC) {
                //Bitmap bmp= (Bitmap) data.getExtras().get("data");
                //让拍摄的图片可以在相册目录中出现
//                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(tackPicStr))));
                if (entry.isCrop()) {
                    entry.crop(tackPicStr);
                } else {
                    //拍完照刷新数据
                    entry.refreshData();
                    //刷新完进入发布
                    Intent intent = new Intent(mContext, ReleasePreviewActivity.class);
                    ArrayList<String> d = new ArrayList<>();
                    d.add(tackPicStr);
                    intent.putExtra(IcFinal.RESULT_DATA_IMG, d);
//                    setResult(Activity.RESULT_OK, intent);
//                    finish();

                    startActivity(intent);
                }
            }
        }
    }






//    private void initEvent() {
//        if (mBroadcastReceiver == null) {
//            mBroadcastReceiver = new ReleaseReceiver();
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(BROADCAST_ACTION);
//            registerReceiver(mBroadcastReceiver, intentFilter);
//        }
//    }

    private int mPageMark = 0;

//    class ReleaseReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            finish();
//            Logger.e("关闭" + mPageMark);
//        }
//
//    }


    @Override
    protected void onDestroy() {
        unSubscribe();
//        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }


    @Override
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //TODO 传入保存路径直接保存
        tackPicStr = ChooserSetting.tempFolder + "photo/" + System.currentTimeMillis() + ".jpg";
        File folder = new File(ChooserSetting.tempFolder + "photo/");
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                Toast.makeText(this, "无法拍照", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        File file = new File(tackPicStr);
        Uri imageUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQ_TACK_PIC);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        init();
        Logger.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        Logger.d("onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "读取存储权限已被拒绝，这将读不到相册内容，请在设置里手动开启")
                    .setTitle("提示")
                    .setPositiveButton("确定")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(PERMISSION_EXTERNAL_STORAGE)
                    .build()
                    .show();
        }
    }

    private static final int PERMISSION_EXTERNAL_STORAGE = 102;


    @AfterPermissionGranted(PERMISSION_EXTERNAL_STORAGE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            init();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Perfit读取相册需要读取存储权限、拍照权限",
                    PERMISSION_EXTERNAL_STORAGE, perms);
        }

    }


    /**
     *               ii.                                         ;9ABH,
     *              SA391,                                    .r9GG35&G
     *              &#ii13Gh;                               i3X31i;:,rB1
     *              iMs,:,i5895,                         .5G91:,:;:s1:8A
     *               33::::,,;5G5,                     ,58Si,,:::,sHX;iH1
     *                Sr.,:;rs13BBX35hh11511h5Shhh5S3GAXS:.,,::,,1AG3i,GG
     *                .G51S511sr;;iiiishS8G89Shsrrsh59S;.,,,,,..5A85Si,h8
     *               :SB9s:,............................,,,.,,,SASh53h,1G.
     *            .r18S;..,,,,,,,,,,,,,,,,,,,,,,,,,,,,,....,,.1H315199,rX,
     *          ;S89s,..,,,,,,,,,,,,,,,,,,,,,,,....,,.......,,,;r1ShS8,;Xi
     *        i55s:.........,,,,,,,,,,,,,,,,.,,,......,.....,,....r9&5.:X1
     *       59;.....,.     .,,,,,,,,,,,...        .............,..:1;.:&s
     *      s8,..;53S5S3s.   .,,,,,,,.,..      i15S5h1:.........,,,..,,:99
     *      93.:39s:rSGB@A;  ..,,,,.....    .SG3hhh9G&BGi..,,,,,,,,,,,,.,83
     *      G5.G8  9#@@@@@X. .,,,,,,.....  iA9,.S&B###@@Mr...,,,,,,,,..,.;Xh
     *      Gs.X8 S@@@@@@@B:..,,,,,,,,,,. rA1 ,A@@@@@@@@@H:........,,,,,,.iX:
     *     ;9. ,8A#@@@@@@#5,.,,,,,,,,,... 9A. 8@@@@@@@@@@M;    ....,,,,,,,,S8
     *     X3    iS8XAHH8s.,,,,,,,,,,...,..58hH@@@@@@@@@Hs       ...,,,,,,,:Gs
     *    r8,        ,,,...,,,,,,,,,,.....  ,h8XABMMHX3r.          .,,,,,,,.rX:
     *   :9, .    .:,..,:;;;::,.,,,,,..          .,,.               ..,,,,,,.59
     *  .Si      ,:.i8HBMMMMMB&5,....                    .            .,,,,,.sMr
     *  SS       :: h@@@@@@@@@@#; .                     ...  .         ..,,,,iM5
     *  91  .    ;:.,1&@@@@@@MXs.                            .          .,,:,:&S
     *  hS ....  .:;,,,i3MMS1;..,..... .  .     ...                     ..,:,.99
     *  ,8; ..... .,:,..,8Ms:;,,,...                                     .,::.83
     *   s&: ....  .sS553B@@HX3s;,.    .,;13h.                            .:::&1
     *    SXr  .  ...;s3G99XA&X88Shss11155hi.                             ,;:h&,
     *     iH8:  . ..   ,;iiii;,::,,,,,.                                 .;irHA
     *      ,8X5;   .     .......                                       ,;iihS8Gi
     *         1831,                                                 .,;irrrrrs&@
     *           ;5A8r.                                            .:;iiiiirrss1H
     *             :X@H3s.......                                .,:;iii;iiiiirsrh
     *              r#h:;,...,,.. .,,:;;;;;:::,...              .:;;;;;;iiiirrss1
     *             ,M8 ..,....,.....,,::::::,,...         .     .,;;;iiiiiirss11h
     *             8B;.,,,,,,,.,.....          .           ..   .:;;;;iirrsss111h
     *            i@5,:::,,,,,,,,.... .                   . .:::;;;;;irrrss111111
     *            9Bi,:,,,,......                        ..r91;;;;;iirrsss1ss1111
     */


}
