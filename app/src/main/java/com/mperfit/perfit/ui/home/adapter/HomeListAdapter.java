package com.mperfit.perfit.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.DailyListBean;
import com.mperfit.perfit.model.bean.HomeDataBean;
import com.mperfit.perfit.ui.article.activity.ArticleDetailActivity;
import com.mperfit.perfit.ui.classes.activity.ClassDetailActivity;
import com.mperfit.perfit.ui.classes.activity.ShopDetailActivity;
import com.mperfit.perfit.ui.webview.WebViewActivity;
import com.mperfit.perfit.widget.DividerItemDecoration;
import com.mperfit.perfit.widget.RecycleViewDivider;
import com.mperfit.perfit.widget.SquareImageView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by zhangbing on 16/10/17.
 * <p>
 * 一开始打算用ScrollView嵌套RecyclerView来实现
 * 但是RecyclerView23.1.1之后的版本嵌套会显示不全
 * Google也不推荐ScrollView嵌套RecyclerView
 * 还是采取getItemViewType来实现
 */

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;

    //=====头条======
    private TopPagerAdapter mAdapter;
    private ViewPager topViewPager;

    //========================
    private OnSortItemClickListener onSortItemClickListener;
    private OnItemClickListener onItemClickListener;
    private OnGoArticleClickListener ongOArticleClickListener;
    //======用于记录是当天的日报还是以前的,如果是以前的 那么title要换成日期,并且不显示头条
    private String currentTitle = "干 货";
    private List<HomeDataBean.DataBean.ArticeleListBean> articeleList;
    private SortAdapter sortAdapter;
    private LinearLayoutManager linearLayoutManager;

    public enum ITEM_TYPE {
        ITEM_TOP,       //滚动栏
        ITEM_SORT,        //课程分类
        ITEM_TITLE,      //文章标题栏
        ITEM_CONTENT    //内容
    }

    //======model=======
    private HomeDataBean.DataBean data;

    public HomeListAdapter(Context mContext, HomeDataBean.DataBean data) {
        this.data = data;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        articeleList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.ITEM_TOP.ordinal();
        } else if (position == 1) {
            return ITEM_TYPE.ITEM_SORT.ordinal();
        } else if (position == 2) {
            return ITEM_TYPE.ITEM_TITLE.ordinal();
        } else {
            return ITEM_TYPE.ITEM_CONTENT.ordinal();
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            //初始化viewpage适配器
//            mAdapter = new TopPagerAdapter(mContext,data.getBanner_list());
            return new TopViewHolder(inflater.inflate(R.layout.item_top, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_SORT.ordinal()) {
            View view = inflater.inflate(R.layout.item_sort, parent, false);
            sortAdapter = new SortAdapter(mContext, data.getSeller_list());
            linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            RecyclerView sortList = (RecyclerView) view.findViewById(R.id.gv_sort_root);
            //设置分类
            sortList.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL));
            sortList.setLayoutManager(linearLayoutManager);
            sortList.setAdapter(sortAdapter);
            return new SortViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_TITLE.ordinal()) {
            return new TitleViewHolder(inflater.inflate(R.layout.item_title, parent, false));
        }
        return new ContentViewHolder(inflater.inflate(R.layout.item_homelist, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {
            //文章列表
            //对内容位置进行修正,如果是以前的日期的那么内容的位置就是从分类之后开始的,内容位置就是-2,
            final int contentPosition;
            contentPosition = position - 3;
            ((ContentViewHolder) holder).title.setText(data.getArticele_list().get(contentPosition).getName());
            ImageLoader.load(mContext, data.getArticele_list().get(contentPosition).getImg_url(), ((ContentViewHolder) holder).image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        ImageView iv = (ImageView) view.findViewById(R.id.iv_daily_item_image);
                        onItemClickListener.onItemClick(contentPosition, iv);
                    }
                }
            });
        } else {
            if (holder instanceof SortViewHolder) {
                sortAdapter.addData(data.getSeller_list());
                sortAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view) {
                        onSortItemClickListener.onItemClick(position, view);
                    }
                });
            } else {
                if (holder instanceof TitleViewHolder) {
                    //文章标题 和分类
                    ((TitleViewHolder) holder).tvTitlle.setText(currentTitle);
                    ((TitleViewHolder) holder).tvSubtitle.setText(data.getArticele_category_str());
                    ((TitleViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ongOArticleClickListener.onItemClick();
                        }
                    });
                } else {

//
//            ((TopViewHolder) holder).vpTop.setAdapter(mAdapter);
//            topViewPager = ((TopViewHolder) holder).vpTop;
//            ((TopViewHolder) holder).mIndicator.setViewPager(topViewPager);
//                    ((TopViewHolder) holder).vpTop.setmData(data.getBanner_list().get(position).getImg_url_android(),data.getBanner_list().get(position).getDepict());
                    List<String> mImageList = new ArrayList<>();
                    List<String> mTitleList = new ArrayList<>();
                    for (HomeDataBean.DataBean.BannerListBean dataBean : data.getBanner_list()) {
                        mImageList.add(dataBean.getImg_url());

//                        mTitleList.add(dataBean.getDepict());//描述
                    }
                    ((TopViewHolder) holder).vpTop.setData(mImageList, mTitleList);
                    ((TopViewHolder) holder).vpTop.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                        @Override
                        public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                            ImageLoader.load(mContext, model, itemView);
                        }
                    });

                    ((TopViewHolder) holder).vpTop.setDelegate(new BGABanner.Delegate<ImageView, String>() {
                        @Override
                        public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                            long bizId = data.getBanner_list().get(position).getBiz_id();
                            //biz_type业务类型  （1，活动2文章，3课程，4商家 5站外链接）
                            int bizType = data.getBanner_list().get(position).getBiz_type();
                            switch (bizType) {
                                case 1:
                                    if (data.getBanner_list().get(position).getIs_click() == 0){
                                        //不能跳转
                                        return;
                                    }
                                    Intent intentToActivity = new Intent();
                                    intentToActivity.setClass(mContext, ArticleDetailActivity.class);
                                    intentToActivity.putExtra("id", bizId);
                                    intentToActivity.putExtra("type", Constants.TYPE_ACTIVITY);
                                    mContext.startActivity(intentToActivity);
                                    break;
                                case 2:
                                    if (data.getBanner_list().get(position).getIs_click() == 0){
                                        //不能跳转
                                        return;
                                    }

                                    Intent intentToArticle = new Intent();
                                    intentToArticle.setClass(mContext, ArticleDetailActivity.class);
                                    intentToArticle.putExtra("id", String.valueOf(bizId));
                                    intentToArticle.putExtra("type", Constants.TYPE_ARTICLE);
                                    mContext.startActivity(intentToArticle);
                                    break;
                                case 3:
                                    if (data.getBanner_list().get(position).getIs_click() == 0){
                                        //不能跳转
                                        return;
                                    }
                                    Intent intentToClass = new Intent();
                                    intentToClass.setClass(mContext, ClassDetailActivity.class);
                                    intentToClass.putExtra(Constants.COURSE_ID, String.valueOf(bizId));

                                    mContext.startActivity(intentToClass);
                                    break;
                                case 4:
                                    if (data.getBanner_list().get(position).getIs_click() == 0){
                                        //不能跳转
                                        return;
                                    }

                                    Intent intentToShop = new Intent();
                                    intentToShop.setClass(mContext, ShopDetailActivity.class);
                                    intentToShop.putExtra(Constants.COURSE_SELLERID, String.valueOf(bizId));
                                    mContext.startActivity(intentToShop);
                                    break;
                                case 5:
                                    if (data.getBanner_list().get(position).getIs_click() == 0){
                                        //不能跳转
                                        return;
                                    }

                                    Intent intentToWebView = new Intent();
                                    intentToWebView.setClass(mContext, WebViewActivity.class);
                                    intentToWebView.putExtra("url", data.getBanner_list().get(position).getUrl());
                                    mContext.startActivity(intentToWebView);
                                    break;

                            }


                        }
                    });


                }
            }
        }


    }

    @Override
    public int getItemCount() {
        if (articeleList == null) {
            return 0;
        }
        if (articeleList.size() == 0) {
            return 0;
        }
        return data.getArticele_list().size() + 3;
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daily_item_title)
        TextView title;
        @BindView(R.id.iv_daily_item_image)
        ImageView image;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_home_artical_title)
        TextView tvTitlle;
        @BindView(R.id.tv_home_artical_subtitle)
        TextView tvSubtitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vp_top)
        BGABanner vpTop;


        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SortViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gv_sort_root)
        RecyclerView gvSortRoot;

        public SortViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //加载
    public void addDailyDate(HomeDataBean.DataBean data) {
        this.data = data;
        this.articeleList = data.getArticele_list();
        notifyDataSetChanged();
    }

    public void changeTopPager(int currentCount) {

        if (topViewPager != null) {
            topViewPager.setCurrentItem(currentCount);
        }
    }


    /**
     * 设置文章列表条目点击
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


    /**
     * 设置分类点击的方法
     */
    public void setOnSortItemClickListener(OnSortItemClickListener onSortItemClickListener) {
        this.onSortItemClickListener = onSortItemClickListener;
    }


    public interface OnSortItemClickListener {
        void onItemClick(int position, View view);
    }

    //

    /**
     * 设置进入文章列表点击事件
     */
    public void setGoArticleClickListener(OnGoArticleClickListener ongOArticleClickListener) {
        this.ongOArticleClickListener = ongOArticleClickListener;
    }


    public interface OnGoArticleClickListener {
        void onItemClick();
    }
}
