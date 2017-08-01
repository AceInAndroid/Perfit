package com.mperfit.perfit.ui.photogallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;

import java.util.List;

/**
 *
 * Created by zhangbing on 2016/12/9.
 */

public class GalleryViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> mList;
    private List<String> imageList;
    private final LayoutInflater inflater;

    public GalleryViewPagerAdapter(Context mContext, List<View> mList, List<String> imageList) {
        this.mContext = mContext;
        this.mList = mList ;
        inflater = LayoutInflater.from(mContext);
        this.imageList = imageList;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //从container中删除指定position的View
        container.removeView(mList.get(position));
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //将当前视图添加到View中
        View view = inflater.inflate(R.layout.item_viewpage_photogallery, null);
        ImageView imageview =(ImageView) view.findViewById(R.id.iv_image);
        mList.add(view);
        ImageLoader.load(mContext,imageList.get(position),imageview);

        container.addView(imageview);
        //返回当前视图
        return view;
    }

    @Override
    public int getCount() {
        //返回要滑动的View个数
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
