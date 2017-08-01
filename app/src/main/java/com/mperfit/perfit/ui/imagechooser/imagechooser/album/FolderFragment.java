package com.mperfit.perfit.ui.imagechooser.imagechooser.album;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.model.bean.PhotoBackBean;
import com.mperfit.perfit.model.bean.PhotoSelectCountMsgBean;
import com.mperfit.perfit.ui.imagechooser.imagechooser.ChooserSetting;
import com.mperfit.perfit.ui.imagechooser.imagechooser.IcFinal;
import com.mperfit.perfit.ui.imagechooser.imagechooser.abslayer.IAlpha;
import com.mperfit.perfit.ui.imagechooser.imagechooser.abslayer.IImageClickListener;
import com.mperfit.perfit.ui.imagechooser.imagechooser.abslayer.IPhotoShoot;
import com.mperfit.perfit.ui.imagechooser.imagechooser.preview.AlbumImagePagerActivity;
import com.mperfit.perfit.ui.imagechooser.imagechooser.res.IChooseDrawable;
import com.mperfit.perfit.utils.RxBusUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


/**
 * 图片选择
 */
public class FolderFragment extends Fragment implements AlbumEntry.IFolderShower, IAlpha, EasyPermissions.PermissionCallbacks {
    private ViewGroup rootView;
    private GridView mGrid;
    private View mCover;
    private FolderAdapter adapter;
    private ArrayList<ImageInfo> data = new ArrayList<>();
    private ArrayList<ImageInfo> selectImgs;
    private IImageClickListener listener;
    private IChooseDrawable drawable;
    private IPhotoShoot photoShoot;
    private List<String> initSelect;
    private boolean isCrop;
    private Intent actIntent;
    private Button mBtnPreview;
    private String mType;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        actIntent = getActivity().getIntent();
        isCrop = actIntent.getBooleanExtra(IcFinal.INTENT_IS_CROP, false);

        mType = actIntent.getStringExtra(IcFinal.CHOOSE_TYPE);

        if (rootView == null) {
            rootView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.image_chooser_fragment_album, container, false);
            adapter = new FolderAdapter(this, data, drawable);
            initView();
            initData();
        }

        initEvent();


        return rootView;
    }

    private int addCount;
    private void initEvent() {
        //多图选择返回后接收到消息  更新选择项
        Subscription subscription = RxBusUtils.getDefault().toObservable(PhotoBackBean.class)
                .subscribe(new Action1<PhotoBackBean>() {
                    @Override
                    public void call(PhotoBackBean photoBackBean) {
                        ArrayList<String> mlist = photoBackBean.getMlist();
                        //改变 selectImgs ;
                        selectImgs.clear();
                        if (mlist != null) {
                            if (mlist.size() == 0) {
                                adapter.addSelectItem(selectImgs);
                            } else {
                                for (String item : mlist) {
                                    for (int i = 0; i < data.size(); i++) {
                                        if (item.equals(data.get(i).getPath())) {
                                            data.get(i).setState(1);
                                            selectImgs.add(data.get(i));
                                            addCount++;
                                            adapter.addSelectItem(selectImgs);
                                        } else {
                                            data.get(i).setState(0);
                                        }

                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        addSubscrebe(subscription);
    }


    protected CompositeSubscription mCompositeSubscription;

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


    private ArrayList<String> list;

    private boolean first = true;


    private void initView() {

        mGrid = (GridView) rootView.findViewById(R.id.mAlbum);
        mBtnPreview = (Button) rootView.findViewById(R.id.btn_preview);
        LinearLayout llPreview = (LinearLayout) rootView.findViewById(R.id.ll_preview);
        int max = getActivity().getIntent().getIntExtra(IcFinal.INTENT_MAX_IMG, 1);
        if (mType != null && !mType.equals(IcFinal.TYPE_RELEASE) && max > 1) {
            llPreview.setVisibility(View.VISIBLE);
        }
        mGrid.setNumColumns(ChooserSetting.NUM_COLUMNS);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.isTakePhoto(position)) {
                    if (photoShoot != null) {
                        //点击照相
                        cameraTask();
                    }
                } else {


                    ImageInfo imageInfo = data.get(position);

                    if (!isCrop) {

                        if (mType != null && mType.equals(IcFinal.TYPE_RELEASE)) {
                            //单选
                            ImageInfo info = data.get(position);

                            if (info.state > 0) {   //点击已经选择过得
                                boolean a = listener.onCancel(selectImgs, info);
                                if (!a) {
                                    info.state = 0;
                                    selectImgs.remove(info);
//                                    RxBusUtils.getDefault().post(new PhotoSelectCountMsgBean(selectImgs.size()));
                                    int size = selectImgs.size();
                                    for (int i = 0; i < size; i++) {
                                        selectImgs.get(i).state = i + 1;
                                    }
                                }
                            } else {
                                //点击其他的删除前一张
                                //获得前一张
                                if (position > 0) {
                                    ImageInfo infoBefore = data.get(position - 1);
                                    infoBefore.state = 0;
                                    selectImgs.remove(infoBefore);
                                    selectImgs.clear();
                                    info.state = selectImgs.size() + 1;
                                    selectImgs.add(info);
//                                        RxBusUtils.getDefault().post(new PhotoSelectCountMsgBean(selectImgs.size()));
                                    adapter.addSelectItem(selectImgs);
                                }

                            }
                            adapter.notifyDataSetChanged();


                        } else {

                            if (first && data != null) {
                                for (ImageInfo info : data) {
                                    list.add(info.path);
                                }
                                first = false;
                                Intent intent = new Intent(getActivity(), AlbumImagePagerActivity.class);
                                intent.putStringArrayListExtra(Constants.PREVIEWIMAG, list);
                                intent.putParcelableArrayListExtra("list", data);
                                intent.putParcelableArrayListExtra("selectImgs", selectImgs);
                                intent.putExtra(Constants.PREVIEW_POSITION, position);
                                getActivity().startActivityForResult(intent, REQ_CHOOSE);
                            } else {
                                Intent intent = new Intent(getActivity(), AlbumImagePagerActivity.class);
                                intent.putStringArrayListExtra(Constants.PREVIEWIMAG, list);
                                intent.putParcelableArrayListExtra("list", data);
                                intent.putParcelableArrayListExtra("selectImgs", selectImgs);
                                intent.putExtra(Constants.PREVIEW_POSITION, position);
                                getActivity().startActivityForResult(intent, REQ_CHOOSE);
                            }


                        }

                    } else {
                        //裁剪模式下逻辑 回调点击事件
                        listener.onCancel(selectImgs, imageInfo);
                        listener.onAdd(selectImgs, imageInfo);
                    }

                }
            }
        });


        adapter.setOnCheckClickListener(new FolderAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(int position) {

                ImageInfo info = data.get(position);
                if (info.state > 0) {   //点击已经选择过得
                    boolean a = listener.onCancel(selectImgs, info);
                    if (!a) {
                        info.state = 0;
                        selectImgs.remove(info);
                        RxBusUtils.getDefault().post(new PhotoSelectCountMsgBean(selectImgs.size()));
                        int size = selectImgs.size();
                        for (int i = 0; i < size; i++) {
                            selectImgs.get(i).state = i + 1;
                        }
                    }
                } else {
                    boolean b = listener.onAdd(selectImgs, info);
                    if (!b) {
                        info.state = selectImgs.size() + 1;
                        selectImgs.add(info);
                        RxBusUtils.getDefault().post(new PhotoSelectCountMsgBean(selectImgs.size()));
                    }
                }
                adapter.notifyDataSetChanged();

            }
        });

        mBtnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlbumImagePagerActivity.class);
                intent.putParcelableArrayListExtra("selectImgs", selectImgs);
                intent.putExtra(Constants.TYPE_PREVIEW, 1);
                getActivity().startActivityForResult(intent, REQ_CHOOSE);
            }
        });

        //popwindow的遮罩
        mCover = rootView.findViewById(R.id.mCover);
    }


    private static final int RC_CAMERA_PERM = 123;


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            photoShoot.takePhoto();

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "Perfit正在请求拍照权限",
                    RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }


    public void setPhotoShoot(IPhotoShoot photoShoot) {
        this.photoShoot = photoShoot;
    }

    public void setSelectImgs(List<String> data) {
        initSelect = data;
    }

    private void initData() {

        mGrid.setAdapter(adapter);
        selectImgs = new ArrayList<>();
        list = new ArrayList<>();
        if (initSelect != null) {
            int size = initSelect.size();
            for (int i = 0; i < size; i++) {
                ImageInfo info = new ImageInfo();
                info.path = initSelect.get(i);
                info.state = i + 1;
                selectImgs.add(info);
            }
        }
    }

    @Override
    public void setChooseDrawable(IChooseDrawable drawable) {
        this.drawable = drawable;
        if (adapter != null) {
            adapter.setChooseDrawable(drawable);
        }
    }

    @Override
    public void setCheckShow(boolean isShow) {
        if (adapter != null) {
            adapter.isShowFlag = isShow;
        }
    }

    @Override
    public void setFolder(ImageFolder folder) {
        if (data != null) {
            data.clear();
            if (ChooserSetting.takePhotoType != ChooserSetting.TP_NONE) {
                data.add(new ImageInfo());
            }
            data.addAll(folder.getDatas());
            int size = data.size();
            for (ImageInfo s : selectImgs) {
                for (int i = 0; i < size; i++) {
                    ImageInfo info = data.get(i);
                    if (info.state == 0 && info.path != null && info.path.equals(s.path)) {
                        data.remove(i);
                        data.add(i, s);
                        break;
                    }
                }
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        unSubscribe();
        super.onDestroy();
        if (ChooserSetting.chooseDrawable != null) {
            ChooserSetting.chooseDrawable.clear();
        }
    }

    private final int REQ_CHOOSE = 0x11;


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_CHOOSE) {
                ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra(IcFinal.RESULT_DATA_IMG);
                chooseFinish(list);
            }
        }
    }


    private void chooseFinish(ArrayList<String> data) {
        if (data.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra(IcFinal.RESULT_DATA_IMG, data);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }


    @Override
    public void setImageClickListener(IImageClickListener listener) {
        this.listener = listener;
    }

    @Override
    public List<ImageInfo> getSelectedImages() {
        return selectImgs;
    }


    @Override
    public void onDataChange(List<ImageInfo> data) {
        this.selectImgs = (ArrayList<ImageInfo>) data;
        adapter.notifyDataSetChanged();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void setAlpha(int alpha) {
        //设置北京遮盖
        mCover.setBackgroundColor(alpha);
    }

    @Override
    public void show() {
        mCover.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        mCover.setVisibility(View.GONE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        photoShoot.takePhoto();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "拍照权限已被拒绝，但是拍照需要拍照权限才能拍照哦~，请在设置里开启")
                    .setTitle("提示")
                    .setPositiveButton("确定")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(RC_CAMERA_PERM)
                    .build()
                    .show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}
