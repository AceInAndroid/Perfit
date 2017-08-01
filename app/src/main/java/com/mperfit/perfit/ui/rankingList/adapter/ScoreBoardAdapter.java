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
import com.mperfit.perfit.model.bean.ScoreBoardBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangbing on 2017/3/24.
 */

public class ScoreBoardAdapter extends BaseQuickAdapter<ScoreBoardBean.DataBean.ListBean,BaseViewHolder> {


    private Context mContext;

    public ScoreBoardAdapter(List<ScoreBoardBean.DataBean.ListBean> data, Context mContext) {
        super(R.layout.item_scoreboad_list,data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, ScoreBoardBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_liveness,String.valueOf(item.getTotal() + "积分"));
        CircleImageView mCircleImg = (CircleImageView) helper.getView(R.id.iv_head);
        ImageLoader.loadHead(mContext,item.getImg_url(),mCircleImg);

        ImageView mIvMedal = (ImageView) helper.getView(R.id.iv_medal);
        TextView mTvRanking = (TextView) helper.getView(R.id.tv_ranking);
        TextView mTvName = (TextView) helper.getView(R.id.tv_name);
        LinearLayout mAll = (LinearLayout) helper.getView(R.id.ll_all);


        if (item.getRank() == 1){
            mIvMedal.setVisibility(View.VISIBLE);
            mTvRanking.setVisibility(View.GONE);
            mIvMedal.setBackground(mContext.getResources().getDrawable(R.drawable.integral_no1));
        } else if (item.getRank() ==2){
            mIvMedal.setVisibility(View.VISIBLE);
            mTvRanking.setVisibility(View.GONE);
            mIvMedal.setBackground(mContext.getResources().getDrawable(R.drawable.integral_no2));

        } else if ( item.getRank()== 3){
            mIvMedal.setVisibility(View.VISIBLE);
            mTvRanking.setVisibility(View.GONE);
            mIvMedal.setBackground(mContext.getResources().getDrawable(R.drawable.integral_no3));
        }
        mTvRanking.setText(String.valueOf(item.getRank()));
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


    private ScoreBoardAdapter.OnItemClickListener onItemClickListener;

    //设置文章列表条目点击
    public void setOnItemClickListener(ScoreBoardAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, View view,long id);
    }


}
