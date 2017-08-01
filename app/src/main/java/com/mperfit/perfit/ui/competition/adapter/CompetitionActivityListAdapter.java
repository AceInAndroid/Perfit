package com.mperfit.perfit.ui.competition.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.ActivityBean;
import com.mperfit.perfit.ui.activities.adapter.ActivityAdapter;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/23.
 */

public class CompetitionActivityListAdapter extends BaseQuickAdapter<ActivityBean.DataBean.ListBean,BaseViewHolder> {

    private Context mContext;
    public CompetitionActivityListAdapter(List<ActivityBean.DataBean.ListBean> data, Context context) {
        super(R.layout.item_competition_activity,data);
        this.mContext = context;

    }

    @Override
    protected void convert(final BaseViewHolder helper, ActivityBean.DataBean.ListBean item) {
        ImageView mImag = (ImageView) helper.getView(R.id.tv_item_activity_imag);
        FrameLayout mAll = (FrameLayout) helper.getView(R.id.fl_competition_all);
        ImageLoader.load(mContext,item.getImg_url(),mImag);
        helper.setText(R.id.tv_item_activity_title,item.getName())
                .setText(R.id.tv_item_activity_date,item.getTime())
                .setText(R.id.tv_item_activity_place,item.getAddress());

        TextView mTag = (TextView) helper.getView(R.id.tv_item_activity_tag);
        mTag.setText(item.getTag());
        if (item.getRandom() == 1) {
            mTag.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yellow));
        } else if (item.getRandom() == 2) {
            mTag.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.green));
        } else if (item.getRandom() == 3) {
            mTag.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue));
        } else {
            mTag.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.purple));
        }

        mAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(helper.getAdapterPosition(),v);
                }
            }
        });
    }

    private CompetitionGameListAdapter.OnItemClickListener onItemClickListener;

    //设置文章列表条目点击
    public void setOnItemClickListener(CompetitionGameListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

}
