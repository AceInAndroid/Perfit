package com.mperfit.perfit.ui.competition.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.ActivityBean;
import com.mperfit.perfit.model.bean.CompetitionGameBean;
import com.mperfit.perfit.ui.activities.adapter.ActivityAdapter;
import com.mperfit.perfit.utils.CheckLoginUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/15.
 */

public class MyGameListAdapter extends BaseQuickAdapter<CompetitionGameBean.DataBean.ListBean, BaseViewHolder> {
    private Context mContext;
    public MyGameListAdapter(int layoutResId, List data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, CompetitionGameBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_game,item.getName())
                .setText(R.id.tv_award,item.getContent());

        ImageView mIvLock =(ImageView) helper.getView(R.id.iv_lock);
        TextView mTvUnloack =(TextView) helper.getView(R.id.tv_unlock_the_conditions);
        FrameLayout flAll = helper.getView(R.id.fl_image_container);

        ImageLoader.loadnoAnim(mContext,item.getImg_url(),(ImageView)helper.getView(R.id.iv_bg));

            if (item.getStatus() == 2){
                //已经解锁
                mIvLock.setVisibility(View.GONE);
                mTvUnloack.setVisibility(View.GONE);
                flAll.setForeground(mContext.getResources().getDrawable(R.drawable.bg_competitionfragment_game20));
                flAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(helper.getAdapterPosition(),v);
                        }
                    }
                });
            } else {
                //未解锁 显示赛事介绍
                flAll.setForeground(mContext.getResources().getDrawable(R.drawable.bg_competitionfragment_70));
                mIvLock.setVisibility(View.VISIBLE);
                mTvUnloack.setVisibility(View.VISIBLE);
                mTvUnloack.setText(item.getUnlock_content());

                if (CheckLoginUtil.CheckLogin(mContext)){
                    //

                    flAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClickNoLogin(helper.getAdapterPosition(),v);
                            }
                        }
                    });

                    return;
                }
                    flAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClickUnLock(helper.getAdapterPosition(),v);
                        }
                    }
                });
            }

    }

    private MyGameListAdapter.OnItemClickListener onItemClickListener;

    //设置文章列表条目点击
    public void setOnItemClickListener(MyGameListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
        void onItemClickUnLock(int position, View view);
        void onItemClickNoLogin(int position, View view);
    }


}
