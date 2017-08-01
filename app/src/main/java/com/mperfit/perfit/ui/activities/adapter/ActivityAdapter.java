package com.mperfit.perfit.ui.activities.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.ActivityBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 16/10/24.
 */

public class ActivityAdapter extends RecyclerView.Adapter {

    private Context context;
    private final LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    private List<ActivityBean.DataBean.ListBean> list;

    public ActivityAdapter(Context context, List<ActivityBean.DataBean.ListBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActivityViewHolder(inflater.inflate(R.layout.item_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null && list != null) {
            ;
            ActivityBean.DataBean.ListBean listBean = list.get(position);
            ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;
            activityViewHolder.mTag.setText(listBean.getTag());

                if (listBean.getRandom() == 1) {
                    activityViewHolder.mTag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.yellow));
                } else if (listBean.getRandom() == 2) {
                    activityViewHolder.mTag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green));

                } else if (listBean.getRandom() == 3) {
                    activityViewHolder.mTag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.blue));

                } else {
                    activityViewHolder.mTag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.purple));
                }


            activityViewHolder.mTitle.setText(listBean.getName());
            activityViewHolder.mDate.setText(listBean.getTime());
            activityViewHolder.mIndustion.setText(listBean.getContent());
            activityViewHolder.mPlace.setText(listBean.getAddress());

            ImageLoader.load(context, listBean.getImg_url(), activityViewHolder.mImag);
            activityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.tv_item_activity_imag);
                    onItemClickListener.onItemClick(position, imageView);
                }
            });
        }

    }


    public void addActivityData(ActivityBean activityBean) {
        this.list = activityBean.getData().getList();
        notifyDataSetChanged();

    }

    public void addActivityMoreData(ActivityBean activityBean) {

        List<ActivityBean.DataBean.ListBean> list = activityBean.getData().getList();


        for (int i = 0; i < list.size(); i++) {
            this.list.add(list.get(i));
        }
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;

    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_activity_imag)
        ImageView mImag;
        @BindView(R.id.tv_item_activity_tag)
        TextView mTag;
        @BindView(R.id.tv_item_activity_title)
        TextView mTitle;
        @BindView(R.id.tv_item_activity_industion)
        TextView mIndustion;
        @BindView(R.id.tv_item_activity_date)
        TextView mDate;
        @BindView(R.id.tv_item_activity_oldprice)
        TextView mOldprice;
        @BindView(R.id.tv_item_activity_hotprice)
        TextView mHotprice;
        @BindView(R.id.tv_item_activity_place)
        TextView mPlace;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //设置文章列表条目点击
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


}
