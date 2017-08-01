package com.mperfit.perfit.ui.competition.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.CategoryCompetitionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbing on 2017/3/20.
 */

public class CompetitionGameListAdapter extends BaseMultiItemQuickAdapter<CategoryCompetitionBean.DataBean.ListBean,BaseViewHolder> {

    private Context mContext;
    public CompetitionGameListAdapter( List<CategoryCompetitionBean.DataBean.ListBean> data, Context mContext) {
        super(data);
        this.mContext = mContext;
        addItemType(CategoryCompetitionBean.DataBean.ListBean.LOCKED, R.layout.item_competition_game_infolist_lock);
        addItemType(CategoryCompetitionBean.DataBean.ListBean.UNLOCKED, R.layout.item_competition_game_infolist);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CategoryCompetitionBean.DataBean.ListBean item) {
        switch (helper.getItemViewType()){
            case CategoryCompetitionBean.DataBean.ListBean.UNLOCKED:
                //解锁
                RoundedImageView mBgView =(RoundedImageView)helper.getView(R.id.tv_item_activity_imag);
                ImageLoader.loadnoAnim(mContext,item.getImg_url(),mBgView);

                helper.setText(R.id.tv_game_name,item.getName())
                        .setText(R.id.tv_game_address,item.getAddress())
                        .setText(R.id.tv_game_time,item.getTime());

                TextView mTvState = (TextView) helper.getView(R.id.tv_game_state);
                FrameLayout flGame = (FrameLayout)helper.getView(R.id.fl_image_container);
                FrameLayout flState = (FrameLayout)helper.getView(R.id.fl_state_container);


                flGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener!=null){
                            onItemClickListener.onItemClick(helper.getAdapterPosition(),v);
                        }
                    }
                });
                if (item.getStatus() == 1){
                    flGame.setForeground(mContext.getResources().getDrawable(R.drawable.bg_competition_game20));
                    mTvState.setText("正在进行中");
                } else {
                    mTvState.setText("已结束");
                    flGame.setForeground(mContext.getResources().getDrawable(R.drawable.bg_competition_70));
                    flState.setForeground(mContext.getResources().getDrawable(R.drawable.bg_competition_70));
                }

                flGame.setForeground(mContext.getResources().getDrawable(R.drawable.bg_competition_game20));





                break;

            case CategoryCompetitionBean.DataBean.ListBean.LOCKED:
                //锁定状态下不用解析其他字段

                break;
        }

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
