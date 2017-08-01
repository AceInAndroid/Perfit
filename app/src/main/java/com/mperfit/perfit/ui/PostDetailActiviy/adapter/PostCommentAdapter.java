package com.mperfit.perfit.ui.postdetailactiviy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.model.bean.PostDetailBean;
import com.mperfit.perfit.ui.comment.adapter.PostCommentListAdapter;
import com.mperfit.perfit.ui.home.adapter.HomeListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2017/2/15.
 */

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.PostCommentViewHolder> {

    private Context mContext;
    private final LayoutInflater inflater;
    private List<PostDetailBean.DataBean.CommentBean> mCommentList;

    public PostCommentAdapter(Context context, List<PostDetailBean.DataBean.CommentBean> mCommentList) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.mCommentList = mCommentList;
    }

    @Override
    public PostCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostCommentViewHolder(inflater.inflate(R.layout.item_post_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(final PostCommentViewHolder holder, int position) {
        PostDetailBean.DataBean.CommentBean commentBean = mCommentList.get(position);
        holder.tvId.setText(mCommentList.get(position).getName());
        holder.tvTimes.setText(commentBean.getTime());
        holder.commentContaint.setText(commentBean.getContent());


        holder.tvId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(holder.getAdapterPosition(), holder.tvId);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCommentList != null) {
            return mCommentList.size();
        } else {
            return 0;
        }
    }

    class PostCommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_times)
        TextView tvTimes;
        @BindView(R.id.comment_containt)
        TextView commentContaint;


        public PostCommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addData(List<PostDetailBean.DataBean.CommentBean> mCommentList){
        this.mCommentList = mCommentList;
        notifyDataSetChanged();
    }

    private PostCommentListAdapter.OnItemClickListener onItemClickListener;
    /**
     * 设置文章列表条目点击
     */
    public void setOnItemClickListener(PostCommentListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


}
