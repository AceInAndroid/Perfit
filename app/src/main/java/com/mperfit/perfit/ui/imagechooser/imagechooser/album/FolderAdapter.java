package com.mperfit.perfit.ui.imagechooser.imagechooser.album;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.mperfit.perfit.R;
import com.mperfit.perfit.ui.home.adapter.HomeListAdapter;
import com.mperfit.perfit.ui.imagechooser.imagechooser.ChooserSetting;
import com.mperfit.perfit.ui.imagechooser.imagechooser.res.IChooseDrawable;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Description:
 */
public class FolderAdapter extends BaseAdapter {

    private IChooseDrawable drawable;
    private Fragment fragment;
    public ArrayList<ImageInfo> data;
    private ArrayList<ImageInfo> selectImgs;


    private final int TYPE_DEFAULT = 0;
    private final int TYPE_TAKEPIC = 1;

    private boolean isTackPhoto = ChooserSetting.takePhotoType != ChooserSetting.TP_NONE;

    public FolderAdapter(Fragment fragment, ArrayList<ImageInfo> data, IChooseDrawable drawable) {
        this.fragment = fragment;
        this.data = data;
        this.drawable = drawable;
    }

    public void setChooseDrawable(IChooseDrawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isTackPhoto) {
            return TYPE_TAKEPIC;
        }
        return TYPE_DEFAULT;
    }

    public boolean isTakePhoto(int position) {
        return getItemViewType(position) == TYPE_TAKEPIC;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == TYPE_TAKEPIC) {
            convertView = LayoutInflater.from(fragment.getContext()).inflate(R.layout.image_chooser_item_camera, parent, false);
            try {

                if (ChooserSetting.tackPhotoIcon > 0) {
                    ((ImageView) convertView).setImageResource(ChooserSetting.tackPhotoIcon);
                }

            } catch (ClassCastException e){
                Logger.e("FolderAdapter:" + e.getMessage());
            }

        } else {
            ImageHolder holder;
            if (convertView == null || convertView.getTag() == null) {
                convertView = LayoutInflater.from(fragment.getContext()).inflate(R.layout.image_chooser_item_image, parent, false);
                holder = new ImageHolder(convertView);
            } else {
                holder = (ImageHolder) convertView.getTag();
            }
            if (drawable != null) {
                holder.mFlag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onCheckClick(position);
                        }
                    }
                });

            } else {
                //隐藏flag
                holder.mFlag.setVisibility(View.GONE);
            }


            if (isShowFlag) {
                holder.mFlag.setVisibility(View.VISIBLE);
            } else {
                holder.mFlag.setVisibility(View.GONE);
            }

            holder.setData(data.get(position));
        }
        return convertView;
    }

    public boolean isShowFlag = true;

    //添加选择的条目
    public void addSelectItem(ArrayList<ImageInfo> selectImgs) {
        this.selectImgs = selectImgs;
        notifyDataSetChanged();
    }

    //移除选择的条目
    public void removeSelectItem(ImageInfo info) {
        if (selectImgs ==null){
            return;
        }
        this.selectImgs.remove(info);

        notifyDataSetChanged();
    }


    private class ImageHolder {
        ImageView mImage;
        View mFlag;

        ImageHolder(View convertView) {
            mImage = (ImageView) convertView.findViewById(R.id.mImage);
            mFlag = convertView.findViewById(R.id.mFlag);
            convertView.setTag(this);
        }

        void setData(ImageInfo info) {

            //图片加载
            DrawableRequestBuilder r = Glide.with(fragment).load(info.path)
                    .error(ChooserSetting.errorResId)
                    .placeholder(ChooserSetting.placeResId);
            if (ChooserSetting.loadAnimateResId <= 0) {
                r.dontAnimate();
            } else {
                r.animate(ChooserSetting.loadAnimateResId);
            }
            r.into(mImage);

            //选中状态加载
            //1、选中图片半透明 未选中正常  ChooserSetting中设置
            //2、tag状态更改
            if (selectImgs != null) {
                if (selectImgs.size() != 0) {
                    for (ImageInfo imageInfo : selectImgs) {
                        if (imageInfo.getPath().equals(info.getPath())) {
                            info.setState(1);
                        } else {
                            info.setState(0);
                        }
                    }
                } else {
                    info.setState(0);
                }

            }

            if (info.state <= 0) {
                mImage.setColorFilter(ChooserSetting.unChooseFilter);
            } else {
                mImage.setColorFilter(ChooserSetting.chooseFilter);
            }
            //设置tag
            setBg(mFlag, info.state);
        }


        private void setBg(View v, int state) {
            if (state == 0) {
                v.setBackgroundDrawable(null);
//                v.setBackgroundDrawable(fragment.getResources().getDrawable(R.drawable.preview));
            } else {
                v.setBackgroundDrawable(null);
                v.setBackgroundDrawable(fragment.getResources().getDrawable(R.drawable.photo_added));
            }
        }

    }

    private FolderAdapter.OnCheckClickListener onItemClickListener;

    /**
     * 设置文章列表条目点击
     */
    public void setOnCheckClickListener(FolderAdapter.OnCheckClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnCheckClickListener {
        void onCheckClick(int position);
    }


}
