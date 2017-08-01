package com.mperfit.perfit.ui.me.activity.articlecollect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.ArticleCollectBean;
import com.mperfit.perfit.widget.SquareImageView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2016/11/28.
 */

public class ArticleCollectAdapter extends RecyclerView.Adapter<ArticleCollectAdapter.ArticleCollectViewHolder> {


    private Context mContext;
    private final LayoutInflater inflater;
    private List<ArticleCollectBean.DataBean.ListBean> list;
    private boolean isEditMode = false;


    private List<Integer> recodeRemove; //记录选中的位置
    private SparseBooleanArray selectedItems;//记录选中的position


    public ArticleCollectAdapter(Context mContext, List<ArticleCollectBean.DataBean.ListBean> list) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        recodeRemove = new ArrayList<>();
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public ArticleCollectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_collect_article, parent, false);
        return new ArticleCollectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArticleCollectViewHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position).getName());
        ImageLoader.load(mContext, list.get(position).getImg_url(), holder.sivImag);
        if (isEditMode) {
            holder.cbEditMode.setVisibility(View.VISIBLE);
            if (onStateChangeListener != null) {
                onStateChangeListener.onSateChange(position, isEditMode);
            }

            if (isSelected(position)) {
                holder.cbEditMode.setChecked(true);
            } else {
                holder.cbEditMode.setChecked(false);
            }
            holder.cbEditMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (onStateChangeListener !=null)
                    onStateChangeListener.onCheckSateChange(position, isChecked);//设置被选中的条目
                    switchSelectedState(position);
                    //记录被选中的位置
                    if (isChecked){
                        recodeRemove.add(position);
                    } else {
                        if (recodeRemove !=null ){
                            for (int i= 0 ; i < recodeRemove.size();i++){
                                if (recodeRemove.get(i) == position){
                                    recodeRemove.remove(i);
                                }
                            }
                        }
                    }

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


        } else {
            holder.cbEditMode.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, null);
                    }
                }
            });
        }



    }

    /**
     * 判读是否选中
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    /**
     * 切换选中或取消选中
     */
    public void switchSelectedState(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
//        notifyDataSetChanged();
    }

    /**
     * 清除所有选中item的标记
     */
    public void clearSelectedState() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * 全选
     */
    public void selectAllItems() {
        clearSelectedState();
        for (int i = 0; i < getItemCount(); i++) {
            selectedItems.put(i, true);
            notifyItemChanged(i);
        }
    }

    /**
     * 获取item数目
     */
    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    /**
     * 获取选中的items
     */
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }



    /**
     * 记录要移除的item 但是还不移除(去请求网络)
     */
    public void removeData() {
        if (onStateRemoveChangeListener != null) {
            onStateRemoveChangeListener.onRemove(recodeRemove);
        }
    }

    /**
     * 从list中移除（当网络请求成功后）清楚所有状态
     */
    public void removeDatas() {
        //清楚选中状态
        clearSelectedState();
        notifyDataSetChanged();
    }


    public void changeMode() {
        if (isEditMode) {
            isEditMode = false;
            changeData();
        } else {
            isEditMode = true;
            changeData();
        }
    }

    public void changeMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
        Logger.e("changeMode(boolean isEditMode)调用了");
        notifyDataSetChanged();
    }



    public void changeData() {
        notifyDataSetChanged();
    }

    public void addData(List<ArticleCollectBean.DataBean.ListBean> list) {
        clearSelectedState();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addMore(List<ArticleCollectBean.DataBean.ListBean> list){
        for (ArticleCollectBean.DataBean.ListBean listBean:list){
            this.list.add(listBean);
        }

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    class ArticleCollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.siv_imag)
        SquareImageView sivImag;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.cb_edit)
        CheckBox cbEditMode;

        public ArticleCollectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 设置列表条目点击
     */

    private ArticleCollectAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ArticleCollectAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


    /**
     * 设置编辑状态监听器
     */

    private ArticleCollectAdapter.OnStateChangeListener onStateChangeListener;

    public void setOnStateChangeListener(ArticleCollectAdapter.OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public interface OnStateChangeListener {
        void onSateChange(int position, boolean isEditmode);

        void onCheckSateChange(int position, boolean isEditmode);
    }


    /**
     * 设置删除完成监听器
     */

    private ArticleCollectAdapter.OnStateRemoveChangeListener onStateRemoveChangeListener;

    public void setOnRemoveStateChangeListener(ArticleCollectAdapter.OnStateRemoveChangeListener onStateRemoveChangeListener) {
        this.onStateRemoveChangeListener = onStateRemoveChangeListener;
    }

    public interface OnStateRemoveChangeListener {
        void onRemove(List<Integer> recodeRemove);
    }


}
