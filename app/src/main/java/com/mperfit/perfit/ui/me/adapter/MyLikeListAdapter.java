package com.mperfit.perfit.ui.me.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.LikeListBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 16/11/3.
 */

public class MyLikeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final LayoutInflater inflater;
    private List<LikeListBean.DataBean.ListBean> list;

    private OnItemClickListener onItemClickListener;

    private OnDeleteClickListener onDeleteClickListener;

    private OntheDataChangedListener onTheDataChangeListener;

    private boolean isFirst = true;

    public MyLikeListAdapter(Context context, List<LikeListBean.DataBean.ListBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_mylike, parent, false);
        return new MyLikeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        MyLikeViewHolder myLikeViewHolder = (MyLikeViewHolder) holder;
        myLikeViewHolder.tvTitle.setText(list.get(position).getName());
        myLikeViewHolder.tvData.setText(list.get(position).getTime());
        myLikeViewHolder.cbCollect.setSelected(list.get(position).isSelect());
        myLikeViewHolder.cbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //监听checkbox
                if (onCheckChangeListener != null) {
                    onCheckChangeListener.onCheckChange(position, isChecked);
                }
            }
        });
        setBackTouch(myLikeViewHolder.cbCollect);


        int status = list.get(position).getStatus();
        Logger.e("status="+status+"");
        if (status == 1) {
            //即将开始
            myLikeViewHolder.tvState.setText(R.string.startquickly);
        } else if (status == 2) {
            myLikeViewHolder.tvState.setText(R.string.join_ing);
        } else {
            myLikeViewHolder.tvState.setText(R.string.join_end);
            myLikeViewHolder.tvState.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_myjoin_end));
        }
        ImageLoader.load(context, list.get(position).getImg_url(), myLikeViewHolder.ivImg);
        myLikeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position, view);
            }
        });

    }

    private void setBackTouch(View view) {
        Rect delegateArea = new Rect();
        // Hit rectangle in parent's coordinates
        view.getHitRect(delegateArea);


        // 扩大触摸区域矩阵值
        delegateArea.left -= 100;
        delegateArea.top -= 100;
        delegateArea.right += 100;
        delegateArea.bottom += 100;

        /**
         * 构造扩大后的触摸区域对象
         * 第一个构造参数表示  矩形面积
         * 第二个构造参数表示 被扩大的触摸视图对象
         */
        TouchDelegate expandedArea = new TouchDelegate(delegateArea, view);
        if (View.class.isInstance(view.getParent())) {
            // 设置视图扩大后的触摸区域
            ((View) view.getParent()).setTouchDelegate(expandedArea);
        }

    }



    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void addMore(List<LikeListBean.DataBean.ListBean> list) {
        for (LikeListBean.DataBean.ListBean bean : list) {
            this.list.add(bean);
        }
        notifyDataSetChanged();
    }


    public void updateCheckState(boolean isChck, int position) {
        list.get(position).setSelect(isChck);
        notifyDataSetChanged();
    }


    public static class MyLikeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.cb_collect)
        CheckBox cbCollect;


        public MyLikeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //加载
    public void addDate(List<LikeListBean.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        onTheDataChangeListener.onDataChange(position, list);
        list.remove(position);
        notifyItemRemoved(position);
        reChangesData(position);
    }

    public void reChangesData(int position) {

        if (position != list.size()) {
            notifyItemRangeChanged(position, list.size() - position);
//            onTheDataChangeListener.onDataChange(position,list);
        }
    }

    public void setOnDataChangeListener(OntheDataChangedListener onTheDataChangeListener) {
        this.onTheDataChangeListener = onTheDataChangeListener;

    }

    public interface OntheDataChangedListener {
        void onDataChange(int position, List<LikeListBean.DataBean.ListBean> list);
    }


    //设置文章列表条目点击
    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }


    private MyLikeListAdapter.OnCheckChangeListener onCheckChangeListener;

    public void setOnCheckChangeListener(MyLikeListAdapter.OnCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
    }

    public interface OnCheckChangeListener {
        void onCheckChange(int position, boolean isCheck);
    }

    //设置文章列表条目点击
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


}
