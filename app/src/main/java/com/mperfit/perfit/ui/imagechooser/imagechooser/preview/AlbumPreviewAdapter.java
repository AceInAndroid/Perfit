package com.mperfit.perfit.ui.imagechooser.imagechooser.preview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.ui.imagechooser.imagechooser.album.ImageInfo;
import com.mperfit.perfit.ui.photogallery.PinchImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbing on 2017/1/16.
 */

public class AlbumPreviewAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> mList;
    private List<ImageInfo> imageList;
    private final LayoutInflater inflater;

    public AlbumPreviewAdapter(Context mContext, List<View> mList, ArrayList<ImageInfo> imageList) {
        this.mContext = mContext;
        this.mList = mList ;
        inflater = LayoutInflater.from(mContext);
        this.imageList = imageList;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //从container中删除指定position的View
//        container.removeView(mList.get(position));
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {

        //将当前视图添加到View中
        View view = inflater.inflate(R.layout.item_album_preview,null);
        FrameLayout frameLayout =(FrameLayout) view.findViewById(R.id.fl_container);
        PinchImageView imageview =(PinchImageView) frameLayout.findViewById(R.id.iv_image);
        ImageButton ibSelected =(ImageButton) frameLayout.findViewById(R.id.ib_cancel);

        if (imageList.get(position).getState() == 0){
            //没选中
            ibSelected.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.preview));
        } else {
            ibSelected.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.preview_sellect));
        }
        ibSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteListener.onCheckClickListener(position);
            }
        });
//        mList.add(view);
//        Bitmap getimage = ImageCompress.getimage(imageList.get(position));
//        if (getimage !=null){
//            imageview.setImageBitmap(getimage);
//        }
        ImageLoader.load(mContext,imageList.get(position).getPath(),imageview);
        container.addView(frameLayout);
//        返回当前视图
        return view;
    }

    private OnDeleteListener onDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.onDeleteListener = onDeleteListener;
    }

    interface OnDeleteListener {
        void onCheckClickListener(int position);
    }

    public void delete(int position){
        imageList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        //返回要滑动的View个数
        return imageList.size();
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
