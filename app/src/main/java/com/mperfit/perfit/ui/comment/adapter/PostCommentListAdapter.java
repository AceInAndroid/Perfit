package com.mperfit.perfit.ui.comment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.PostCommentBean;
import com.mperfit.perfit.ui.home.adapter.HomeListAdapter;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/18.
 */

public class PostCommentListAdapter extends BaseQuickAdapter<PostCommentBean.DataBean.ListBean, BaseViewHolder> {

    private Context mContext;
    private List<PostCommentBean.DataBean.ListBean> data;

    public PostCommentListAdapter(Context context, int layoutResId, List<PostCommentBean.DataBean.ListBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, PostCommentBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_time, item.getTime())
                .setText(R.id.tv_content, item.getContent());
        ImageLoader.loadHead(mContext, item.getIcon(), (ImageView) helper.getView(R.id.iv_head));

        ((ImageView) helper.getView(R.id.iv_head)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(helper.getAdapterPosition(),helper.getView(R.id.iv_head));
                }
            }
        });

        helper.getView(R.id.tv_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(helper.getAdapterPosition(),helper.getView(R.id.iv_head));
                }
            }
        });
    }



    public void addData(List<PostCommentBean.DataBean.ListBean> dataList){
            for (PostCommentBean.DataBean.ListBean item : dataList){
                this.data.add(item);
            }
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
