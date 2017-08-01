package com.mperfit.perfit.ui.rankingList.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.DayRankingListBean;
import com.mperfit.perfit.ui.me.adapter.MyJoinListAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangbing on 2017/3/13.
 */

public class DayRankingListAdapter extends BaseQuickAdapter<DayRankingListBean.DataBean.ListBean,BaseViewHolder> {


    private Context mContext;

    public DayRankingListAdapter(List<DayRankingListBean.DataBean.ListBean> data, Context mContext) {
        super(R.layout.item_day_ranking_list,data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, DayRankingListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_liveness,String.valueOf(item.getTotal()));
        CircleImageView mCircleImg = (CircleImageView) helper.getView(R.id.iv_head);
        ImageLoader.loadHead(mContext,item.getImg_url(),mCircleImg);

        ImageView mIvDecorate = (ImageView) helper.getView(R.id.iv_decorate);
        TextView mTvDecorate = (TextView) helper.getView(R.id.tv_decorate);
        TextView mTvName = (TextView) helper.getView(R.id.tv_name);
        LinearLayout mAll = (LinearLayout) helper.getView(R.id.ll_all);


        if (item.getRank() == 1){
            mIvDecorate.setVisibility(View.VISIBLE);
            mTvName.setTextColor(mContext.getResources().getColor(R.color.ranking_num1_bg));
            mTvDecorate.setBackground(mContext.getResources().getDrawable(R.drawable.item_ranking_num));

        } else if (item.getRank() ==2){
            mIvDecorate.setVisibility(View.VISIBLE);
            mTvName.setTextColor(mContext.getResources().getColor(R.color.ranking_num2_bg));
            mIvDecorate.setBackground(mContext.getResources().getDrawable(R.drawable.red_no2));
            mTvDecorate.setBackground(mContext.getResources().getDrawable(R.drawable.item_ranking_num2));

        } else if ( item.getRank()== 3){
            mIvDecorate.setVisibility(View.VISIBLE);
            mTvName.setTextColor(mContext.getResources().getColor(R.color.ranking_num3_bg));
            mIvDecorate.setBackground(mContext.getResources().getDrawable(R.drawable.red_no3));
            mTvDecorate.setBackground(mContext.getResources().getDrawable(R.drawable.item_ranking_num3));
        }
        mTvDecorate.setText("No" + item.getRank());
        final long mUserId = item.getUser_id();

        if (item.getRank() % 2 == 0){
            mAll.setBackgroundColor(mContext.getResources().getColor(R.color.ranking_bg));
        } else {
            mAll.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        mCircleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(helper.getAdapterPosition(),v,mUserId);
                }
            }
        });

        mTvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(helper.getAdapterPosition(),v,mUserId);
                }
            }
        });
    }


    private DayRankingListAdapter.OnItemClickListener onItemClickListener;

    //设置文章列表条目点击
    public void setOnItemClickListener(DayRankingListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, View view,long id);
    }


}
