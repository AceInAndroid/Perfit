package com.mperfit.perfit.ui.imagechooser.imagechooser.album;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.mperfit.perfit.R;
import com.mperfit.perfit.ui.imagechooser.imagechooser.ChooserSetting;
import com.mperfit.perfit.ui.imagechooser.imagechooser.EntryActivity;
import com.mperfit.perfit.ui.imagechooser.imagechooser.IcFinal;
import com.mperfit.perfit.ui.imagechooser.imagechooser.abslayer.IImageClickListener;
import com.mperfit.perfit.ui.imagechooser.imagechooser.res.IChooseDrawable;
import com.mperfit.perfit.ui.imagechooser.utils.LogUtils;
import com.mperfit.perfit.ui.release.activity.ReleasePreviewActivity;
import com.mperfit.perfit.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 *
 */
public class AlbumEntry extends AbsAlbumEntry implements AlbumTool.Callback, IAlbumClickListener, IImageClickListener, EasyPermissions.PermissionCallbacks {

    private FragmentActivity activity;
    private int containerId;
    private IFolderShower folderShower;
    private IAlbumShower albumShower;

    private Intent actIntent;

    private AlbumTool tool;

    private final int REQ_CROP = 0x10;
    private final int REQ_CHOOSE = 0x11;
    private String chooseType;

    public AlbumEntry(FragmentActivity activity, int containerId, IFolderShower fShower, IAlbumShower aShower) {
        this.activity = activity;
        this.containerId = containerId;
        this.folderShower = fShower;
        this.albumShower = aShower;
        init();
    }


    private void init() {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, folderShower.getFragment())
                .commitAllowingStateLoss();
        albumShower.setAlbumClickListener(this);
        folderShower.setImageClickListener(this);
        tool = new AlbumTool(activity);
        tool.setCallback(this);
        tool.findAlbumsAsync();

        actIntent = activity.getIntent();

        setCrop(actIntent.getBooleanExtra(IcFinal.INTENT_IS_CROP, false));

        if (isCrop()) {
            setMax(1);
        } else {
            setMax(actIntent.getIntExtra(IcFinal.INTENT_MAX_IMG, getMax()));
        }

        //判断类型 如果是release

        chooseType = actIntent.getStringExtra(IcFinal.CHOOSE_TYPE);


        if (chooseType!=null && chooseType.equals(IcFinal.TYPE_RELEASE)) {
            //如果是release模式
            //下一步显示
            //点击图片选择
            setMax(1);
            this.folderShower.setCheckShow(false);
            this.folderShower.setChooseDrawable(null);
//            this.folderShower.setChooseDrawable(ChooserSetting.chooseDrawable);

        } else {
            //判断数量
            if (getMax() == 1) {
                this.folderShower.setChooseDrawable(null);
                //隐藏选择checkbox
                this.folderShower.setCheckShow(false);
            } else {
                this.folderShower.setChooseDrawable(ChooserSetting.chooseDrawable);
                this.folderShower.setCheckShow(true);
            }

        }

    }

    public void refreshData() {
        tool.findAlbumsAsync();
    }


    //显示相册选择器
    public void showAlbumChooser() {
        LogUtils.e("显示相册选择器");
        albumShower.show();
    }

    //关闭相册选择器
    public void cancelAlbumChooser() {
        albumShower.cancel();
    }

    @Override
    public void onFolderFinish(ImageFolder folder) {
        LogUtils.e("图片集《" + folder.getName() + "》查找完毕");
        folderShower.setFolder(folder);
    }

    @Override
    public void onAlbumFinish(ArrayList<ImageFolder> albums) {
        LogUtils.e("相册查找完毕,共" + albums.size() + "个相册");
        albumShower.setAlbums(albums);
    }

    @Override
    public void onAlbumClick(ImageFolder folder) {
        tool.findFolderAsync(folder);
        cancelAlbumChooser();
    }

    public void crop(String path) {
        int shape = actIntent.getIntExtra(IcFinal.INTENT_CROP_SHAPE, 0);
        Intent intent = new Intent(IcFinal.ACTION_CROP);
        intent.putExtra(IcFinal.INTENT_CROP_DATA, path);
        intent.putExtra(IcFinal.INTENT_CROP_SHAPE, shape);
        if (shape == 0) {
            intent.putExtra(IcFinal.INTENT_CROP_COVER, actIntent.getStringExtra(IcFinal.INTENT_CROP_COVER));
            intent.putExtra(IcFinal.INTENT_CROP_PARAM, actIntent.getIntExtra(IcFinal.INTENT_CROP_PARAM, 0));
        } else {
            intent.putExtra(IcFinal.INTENT_CROP_WIDTH, actIntent.getIntExtra(IcFinal.INTENT_CROP_WIDTH, 500));
            intent.putExtra(IcFinal.INTENT_CROP_HEIGHT, actIntent.getIntExtra(IcFinal.INTENT_CROP_HEIGHT, 500));
        }
        activity.startActivityForResult(intent, REQ_CROP);
    }

    /**
     * 添加图片方法
     *
     * @param data
     * @param info
     * @return
     */
    @Override
    public boolean onAdd(List<ImageInfo> data, ImageInfo info) {

        if (chooseType!=null && chooseType.equals(IcFinal.TYPE_RELEASE)) {
            //如果是发布模式
            //单选
//            if (data.size() == 1) {
////                ToastUtils.showShrotMsg(activity,"发布只能选择一张哟");
//                return true;
//            }
            return false;
        } else {
            if (getMax() == 1) {
                if (isCrop()) {
                    crop(info.path);
                } else {
                    Intent intent = new Intent();
                    ArrayList<String> result = new ArrayList<>();
                    result.add(info.path);
                    intent.putExtra(IcFinal.RESULT_DATA_IMG, result);
                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.finish();
                    activity.overridePendingTransition(R.anim.right_in,R.anim.right_out);
                }
                return true;
            } else {
                if (data.size() == getMax()) {
                    ToastUtils.showShrotMsg(activity, "亲~已经够" + getMax() + "张了");
                    return true;
                }
            }

        }


        return false;
    }

    public void chooseFinish() {
        List<ImageInfo> res = folderShower.getSelectedImages();
        int resSize = res.size();
        if (resSize > 0) {
            ArrayList<String> data = new ArrayList<>(resSize);
            for (int i = 0; i < resSize; i++) {
                data.add(res.get(i).path);
            }
            chooseFinish(data);
        }
    }

    public void chooseFinishToPublish() {
        List<ImageInfo> res = folderShower.getSelectedImages();
        int resSize = res.size();
        if (resSize > 0) {
            ArrayList<String> data = new ArrayList<>(resSize);
            for (int i = 0; i < resSize; i++) {
                data.add(res.get(i).path);
            }
            chooseFinishToActivity(data);
        }
    }


    private void chooseFinishToActivity(ArrayList<String> data) {
        if (data.size() > 0) {

            Intent intent = new Intent(activity,ReleasePreviewActivity.class);
            intent.putExtra(IcFinal.RESULT_DATA_IMG, data);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.right_in,R.anim.right_out);
        } else {
            ToastUtils.showShrotMsg(activity,"请选择图片");
        }
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_CROP) {
                chooseFinish(data.getStringExtra(IcFinal.RESULT_DATA_IMG));
            } else if (requestCode == REQ_CHOOSE) {
                ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra(IcFinal.RESULT_DATA_IMG);
                chooseFinish(list);
            }
        }
    }

    private void chooseFinish(ArrayList<String> data) {
        if (data.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra(IcFinal.RESULT_DATA_IMG, data);
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
            activity.overridePendingTransition(R.anim.right_in,R.anim.right_out);
        } else {
            activity.finish();
            activity.overridePendingTransition(R.anim.right_in,R.anim.right_out);
        }
    }

    private void chooseFinish(String data) {
        if (data != null) {
            ArrayList<String> c = new ArrayList<>(1);
            c.add(data);
            chooseFinish(c);
        }
    }

    @Override
    public boolean onCancel(List<ImageInfo> data, ImageInfo info) {
        return false;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public interface IAlbumShower {
        void setAlbums(ArrayList<ImageFolder> albums);

        void setAlbumClickListener(IAlbumClickListener listener);

        void show();

        void cancel();
    }

    public interface IFolderShower {
        void setChooseDrawable(IChooseDrawable drawable);

        void setCheckShow(boolean isShow);

        void setFolder(ImageFolder folder);

        void setImageClickListener(IImageClickListener listener);

        List<ImageInfo> getSelectedImages();

        void onDataChange(List<ImageInfo> data);

        Fragment getFragment();
    }

}
