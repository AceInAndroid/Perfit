package com.mperfit.perfit.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.CourseDetailBean;
import com.mperfit.perfit.model.bean.HomeDataBean;
import com.mperfit.perfit.ui.me.adapter.MyLikeListAdapter;
import com.mperfit.perfit.widget.RoundImageViewLast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页课程分类适配器
 * Created by zhangbing on 16/10/18.
 */

public class SortAdapter extends RecyclerView.Adapter {

    private final LayoutInflater inflater;
    private Context context;

    private List<HomeDataBean.DataBean.SellerListBean> courseCategoryList;

    private OnItemClickListener onItemClickListener;

    public SortAdapter(Context context, List<HomeDataBean.DataBean.SellerListBean> courseCategoryList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.courseCategoryList = courseCategoryList;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home_sort, null);
        return new SortListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SortListViewHolder listViewHolder = (SortListViewHolder) holder;
        listViewHolder.ivSortText.setText(courseCategoryList.get(position).getName());
        ImageLoader.load(context,courseCategoryList.get(position).getImg_url(),listViewHolder.ivSortImg);
        listViewHolder.mSortContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position,view);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (courseCategoryList == null){
            return 0;
        }
        return courseCategoryList.size();
    }

    class SortListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_sort_img)
        RoundImageViewLast ivSortImg;
        @BindView(R.id.iv_sort_text)
        TextView ivSortText;
        @BindView(R.id.fl_sort)
        FrameLayout mSortContent;


        public SortListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



    public void addData(List<HomeDataBean.DataBean.SellerListBean> courseCategoryList){
        this.courseCategoryList = courseCategoryList;
        notifyDataSetChanged();
    }



    //设置分类点击的方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
