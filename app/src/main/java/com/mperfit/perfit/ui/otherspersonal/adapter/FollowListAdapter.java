package com.mperfit.perfit.ui.otherspersonal.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.FansListBean;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/21.
 */

public class FollowListAdapter extends BaseQuickAdapter<FansListBean.DataBean.ListBean, BaseViewHolder> {

    private List<FansListBean.DataBean.ListBean> data;

    public FollowListAdapter(int layoutResId, List<FansListBean.DataBean.ListBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FansListBean.DataBean.ListBean item) {

        TextView tvFollowText = helper.getView(R.id.tv_follow_text);
        ImageView ivFollowIcon = helper.getView(R.id.iv_follow_icon);
        ImageButton ibFollowBg = helper.getView(R.id.ib_follow_bg);
        RelativeLayout flFollow = helper.getView(R.id.fl_follow);

        if (item.getIs_follow() == 0) {
            //设置margin
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ivFollowIcon.getLayoutParams();
            layoutParams.setMargins(SystemUtil.dp2px(mContext, 18), 0, 0, 0);
            ivFollowIcon.setLayoutParams(layoutParams);
        } else {
            //设置margin
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ivFollowIcon.getLayoutParams();
            layoutParams.setMargins(SystemUtil.dp2px(mContext, 9), 0, 0, 0);
            ivFollowIcon.setLayoutParams(layoutParams);
        }


        helper.setText(R.id.tv_name, item.getUser_name());
        ImageLoader.loadHead(mContext, item.getUser_img_url(), (ImageView) helper.getView(R.id.iv_head));

        ibFollowBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFollowClickListener != null) {
                    if (item.getIs_follow() == 0) {
                        onFollowClickListener.onFollowClick(helper.getAdapterPosition(), item.getUser_id());
                    } else {
                        onFollowClickListener.onRemoveFollowClick(helper.getAdapterPosition(), item.getUser_id());
                    }
                }
            }
        });


        changeFollowBtn(item, tvFollowText, ivFollowIcon, ibFollowBg,flFollow);


        ((ImageView) helper.getView(R.id.iv_head)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(helper.getAdapterPosition(), helper.getView(R.id.iv_head));
                }
            }
        });


        helper.getView(R.id.tv_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(helper.getAdapterPosition(), helper.getView(R.id.iv_head));
                }
            }
        });


    }


    private void changeFollowBtn(FansListBean.DataBean.ListBean item, TextView tvFollowText, ImageView ivFollowIcon, ImageButton ibFollowBg, RelativeLayout flFollow) {

        if (SharedPreferenceUtil.isMe(item.getUser_id())){
            flFollow.setVisibility(View.GONE);
        } else {
            if (flFollow.getVisibility() == View.GONE){
                flFollow.setVisibility(View.VISIBLE);
            }
            if (item.getIs_follow() == 0) {
                //未关注
                tvFollowText.setText("关注");
                tvFollowText.setTextColor(mContext.getResources().getColor(R.color.groupbuying_price));
                ivFollowIcon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_icon_add));
                ibFollowBg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_bg_follow));
            } else {
                //已关注
                tvFollowText.setText("已关注");
                tvFollowText.setTextColor(mContext.getResources().getColor(R.color.white));
                ivFollowIcon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_icon_concern));
                ibFollowBg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_myjoin_bg));
            }


        }

    }


    FollowListAdapter.OnFollowClickListener onFollowClickListener;

    /**
     * 设置文章列表条目点击
     */
    public void setOnFollowClickListener(FollowListAdapter.OnFollowClickListener onFollowClickListener) {
        this.onFollowClickListener = onFollowClickListener;
    }

    public interface OnFollowClickListener {
        void onFollowClick(int position, long id);

        void onRemoveFollowClick(int position, long id);
    }


    private OnHeadAndNameClickListener onItemClickListener;

    /**
     * 设置头像跳转
     */
    public void setOnItemClickListener(OnHeadAndNameClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnHeadAndNameClickListener {
        void onItemClick(int position, View view);
    }


    public void addData(List<FansListBean.DataBean.ListBean> dataList) {
        setNewData(dataList);
    }


    public void addMore(List<FansListBean.DataBean.ListBean> dataList) {
        addData(dataList);
    }


    public void updateState(int position, int type) {
        Logger.e("updateState调用了" + data.get(position).getIs_follow());

        if (type == 1) {
            data.get(position).setIs_follow(1);
        } else {
            data.get(position).setIs_follow(0);
        }

        notifyItemChanged(position);
    }


}
