package com.mperfit.perfit.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.HomeDataBean;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页轮播条
 * Created by zhangbing on 16/10/13.
 */

public class TopPagerAdapter extends PagerAdapter{

    private List<HomeDataBean.DataBean.BannerListBean> mList = new ArrayList<>();
    private Context mContext;

    public TopPagerAdapter(Context context, List<HomeDataBean.DataBean.BannerListBean> mList)
    {
        this.mList = mList;
        this.mContext = context;
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top_pager, container, false);
        ImageView ivImage = (ImageView) view.findViewById(R.id.iv_top_image);

//        TextView tvTitle = (TextView) view.findViewById(R.id.tv_top_title);
        ImageLoader.load(mContext,mList.get(position).getImg_url(),ivImage);
//        tvTitle.setText(mList.get(position).getDepict());
        final Long id = mList.get(position).getBiz_id();
        final int bizType = mList.get(position).getBiz_type();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ArticleDetailActivity.class);
                intent.putExtra("id",String.valueOf(id));
                intent.putExtra(Constants.BANNER_TYPE,bizType);
                mContext.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



}
