package com.mperfit.perfit.ui.article.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.ArticleListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 16/10/18.
 */

public class FitnessAdapter extends RecyclerView.Adapter {

    private Context context;
    private final LayoutInflater inflater;
    List<ArticleListBean.DataBean.ListBean> mList;

    OnItemClickListener onItemClickListener;

    public FitnessAdapter(Context context, List<ArticleListBean.DataBean.ListBean> mList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_fitness_fragment_list, parent, false);
        return new FitnessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageLoader.load(context, mList.get(position).getImg_url(), ((FitnessViewHolder) holder).ivItemActivityImag);
        ((FitnessViewHolder) holder).tvItemActivityTitle.setText(mList.get(position).getName());
        ((FitnessViewHolder) holder).tvItemActivityIndustion.setText(mList.get(position).getContent());
        if (mList.get(position).getRandom() == 1) {
            ((FitnessViewHolder) holder).tvItemActivityTag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.yellow));
        } else if (mList.get(position).getRandom() == 2) {
            ((FitnessViewHolder) holder).tvItemActivityTag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green));

        } else if (mList.get(position).getRandom()  == 3) {
            ((FitnessViewHolder) holder).tvItemActivityTag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.blue));

        } else {
            ((FitnessViewHolder) holder).tvItemActivityTag.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.purple));

        }
        ((FitnessViewHolder) holder).tvItemActivityTag.setText(mList.get(position).getTag());
        ((FitnessViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    ImageView shareView = (ImageView) view.findViewById(R.id.tv_item_fitness_imag);
                    onItemClickListener.onItemClick(position, shareView);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public void addDate(List<ArticleListBean.DataBean.ListBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addMoreDate(List<ArticleListBean.DataBean.ListBean> mList) {
        for (int i = 0 ; i <mList.size();i++){
            this.mList.add(mList.get(i));
            notifyItemInserted(this.mList.size()+i + 1);
        }
    }

    class FitnessViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_fitness_imag)
        ImageView ivItemActivityImag;
        @BindView(R.id.tv_item_fitness_tag)
        TextView tvItemActivityTag;
        @BindView(R.id.tv_item_fitness_title)
        TextView tvItemActivityTitle;
        @BindView(R.id.tv_item_fitness_industion)
        TextView tvItemActivityIndustion;


        public FitnessViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


}
