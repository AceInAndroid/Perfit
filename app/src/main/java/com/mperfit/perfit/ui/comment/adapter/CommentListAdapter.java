package com.mperfit.perfit.ui.comment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.ShopCommentListBean;
import com.mperfit.perfit.presenter.contract.MainContract;
import com.mperfit.perfit.ui.classes.adapter.AssesSmallListAdapter;
import com.mperfit.perfit.ui.classes.adapter.ClassAccessImageAdapter;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.MyRatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangbing on 2016/12/19.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentListViewViewholder> {

    private final LayoutInflater inflater;

    private List<ShopCommentListBean.DataBean.ListBean> mList;
    private Context mContext;

    public CommentListAdapter(Context context, List<ShopCommentListBean.DataBean.ListBean> mList) {
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        this.mContext = context;

    }

    @Override
    public CommentListViewViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_classdetail_assess_havaphoto, parent, false);
        return new CommentListViewViewholder(view);
    }

    @Override
    public void onBindViewHolder(CommentListViewViewholder holder, final int position) {
        ShopCommentListBean.DataBean.ListBean listBean = mList.get(position);
        holder.tvItemAsseesId.setText(listBean.getUser_name());
        holder.tvItemAsseesContent.setText(listBean.getContent());
        holder.tvItemAsseesDate.setText(SystemUtil.getFormatTime(listBean.getCreate_time()));
        holder.mrbItemAssesRatingbar.setStarRating(listBean.getScore());
        ImageLoader.load(mContext,listBean.getUser_img_url(),holder.ivItemAssessHead);
        holder.gvItemSssessPhoto.setHorizontalSpacing(SystemUtil.px2dp(mContext, 10));
        holder.gvItemSssessPhoto.setNumColumns(3);
        holder.gvItemSssessPhoto.setSelector(new ColorDrawable(Color.TRANSPARENT));
        ClassAccessImageAdapter accessImageAdapter = new ClassAccessImageAdapter(mContext, listBean.getImg_list());
        holder.gvItemSssessPhoto.setAdapter(accessImageAdapter);
        accessImageAdapter.setOnItemClickListener(new ClassAccessImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int gridPosition, View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(gridPosition,position);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    class CommentListViewViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_assees_id)
        TextView tvItemAsseesId;
        @BindView(R.id.mrb_item_asses_ratingbar)
        MyRatingBar mrbItemAssesRatingbar;
        @BindView(R.id.tv_item_assees_date)
        TextView tvItemAsseesDate;
        @BindView(R.id.tv_item_assees_content)
        TextView tvItemAsseesContent;
        @BindView(R.id.gv_item_sssess_photo)
        GridView gvItemSssessPhoto;
        @BindView(R.id.iv_item_assess_head)
        CircleImageView ivItemAssessHead;

        public CommentListViewViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void addData(List<ShopCommentListBean.DataBean.ListBean> mList){
        this.mList = mList;
        notifyDataSetChanged();

    }

    public void addMore(List<ShopCommentListBean.DataBean.ListBean> mList){
        for (ShopCommentListBean.DataBean.ListBean dataBean:mList){
            this.mList.add(dataBean);
        }
        notifyDataSetChanged();
    }



    /**
     * 设置文章列表条目点击
     */
    private CommentListAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CommentListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, int itemPosition);
    }

}
