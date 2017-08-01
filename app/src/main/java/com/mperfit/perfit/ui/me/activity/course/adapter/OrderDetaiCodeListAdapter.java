package com.mperfit.perfit.ui.me.activity.course.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.model.bean.CourseOrderCompletedBean;
import com.mperfit.perfit.model.bean.OrderDetailBean;
import com.mperfit.perfit.ui.home.adapter.HomeListAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2016/12/2.
 */

public class OrderDetaiCodeListAdapter extends RecyclerView.Adapter<OrderDetaiCodeListAdapter.OrderDetailCodeViewHolder> {

    private Context mContext;
    private final LayoutInflater inflater;
    List<OrderDetailBean.DataBean.QdListBean> qdList;

    private int viewType;

    public OrderDetaiCodeListAdapter(Context mContext, List<OrderDetailBean.DataBean.QdListBean> qdList, int status) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.qdList = qdList;
        this.viewType = status ;
    }


    @Override
    public OrderDetailCodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_detai_list, parent, false);
        return new OrderDetailCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderDetailCodeViewHolder holder, final int position) {
        holder.tvCode.setText(qdList.get(position).getCode());


        //status 消费码状态 (1未使用  2已使用 3退款中 4已退款)
        switch (qdList.get(position).getStatus()) {
            case 1:
                holder.tvState.setText("待上课");
                break;
            case 2:
                holder.tvCode.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvState.setText("已消费");
                break;
            case 3:
                if (viewType == 5){
                    holder.tvCode.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvState.setText("退款中");
                } else {
                    holder.tvCode.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvState.setText("退款中");
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.todetail);
                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                    holder.tvState.setCompoundDrawables(null,null,drawable,null);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.e("调用了");
                            if (onItemClickListener !=null){
                                onItemClickListener.onItemClick(position,null);
                            }
                        }
                    });
                }


                break;
            case 4:
                if (viewType ==5){
                    holder.tvCode.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvState.setText("已退款");
                } else {
                    holder.tvCode.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvState.setText("已退款");
                    Drawable drawable2 = mContext.getResources().getDrawable(R.drawable.todetail);
                    drawable2.setBounds(0,0,drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
                    holder.tvState.setCompoundDrawables(null,null,drawable2,null);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.e("调用了");
                            if (onItemClickListener !=null){
                                onItemClickListener.onItemClick(position,null);
                            }
                        }
                    });
                }

                break;

        }
    }

    @Override
    public int getItemCount() {
        if (qdList==null){
            return 0;
        }
        return qdList.size();
    }


    /**
     * 设置文章列表条目点击
     *
     * */

    OrderDetaiCodeListAdapter.OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OrderDetaiCodeListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }




    class OrderDetailCodeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_code)
        TextView tvCode;

        @BindView(R.id.tv_state)
        TextView tvState;

        @BindView(R.id.ll_container)
        LinearLayout llContainer;


        public OrderDetailCodeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
