package com.mperfit.perfit.ui.me.activity.course.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.OrderListBean;
import com.mperfit.perfit.ui.home.adapter.HomeListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2016/12/2.
 */

public class AllCourseFragmentAdapter extends RecyclerView.Adapter<AllCourseFragmentAdapter.AllCourseViewHolder> {

    private Context mContext;
    private final LayoutInflater inflater;
    private List<OrderListBean.DataBean.ListBean> listBeen;


    public AllCourseFragmentAdapter(Context mContext, List<OrderListBean.DataBean.ListBean> listBeen) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.listBeen = listBeen;
    }

    @Override
    public AllCourseFragmentAdapter.AllCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_all_course_list, parent, false);
        return new AllCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllCourseViewHolder holder, final int position) {
        holder.tvName.setText(listBeen.get(position).getSeller_name() + ":" + listBeen.get(position).getCourse_name());
        ImageLoader.load(mContext, listBeen.get(position).getCourse_img_url(), holder.ivImage);
        holder.tvTime.setText(listBeen.get(position).getBegin_time() + listBeen.get(position).getEnd_time());
        holder.tvCount.setText("数量:" + listBeen.get(position).getNumber()+ "张");
        holder.tvPrice.setText("总价:" + listBeen.get(position).getPay_price() +"元" );

        int status = listBeen.get(position).getStatus();

        holder.llToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position,null);
            }
        });


        holder.llCoursedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener !=null){
                    onItemClickListener.onToCourseDetail(position);
                }
            }
        });
        //1待付款  2已付款  3待评价(已消费) 4已评价 5 已退款（已过期）
        switch (status){
            case 1:
                holder.tvState.setText("待付款");
                holder.tvState2.setVisibility(View.VISIBLE);
                holder.tvState2.setText("去付款");
                holder.tvState2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //去付款
                        onItemClickListener.onGoPay(position);
                    }
                });
                break;
            case 2:

                //已付款 未消费
                holder.tvState.setText("待上课");
                holder.tvState2.setVisibility(View.GONE);
                break;
            case 3:
                holder.tvState.setText("待评价");
                holder.tvState2.setVisibility(View.VISIBLE);
                holder.tvState2.setText("去评价");
                holder.tvState2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //去评价
                        onItemClickListener.onGoComment(position);
                    }
                });
                break;
            case 4:
                holder.tvState.setVisibility(View.VISIBLE);
                holder.tvState2.setText("已评价");
                break;
            case 5:
                holder.tvState.setText("已退款");
                holder.tvState2.setVisibility(View.GONE);
                break;
        }

    }


    @Override
    public int getItemCount() {
        if (listBeen == null) {
            return 0;
        }
        return listBeen.size();
    }



    /**
     *
     * 加载更多数据
     */
    public void addMoreData(List<OrderListBean.DataBean.ListBean> list ){
//        for (int i = this.listBeen.size() ; i < list.size() ;i++){
//            this.listBeen.add(list.get(i));
//            notifyItemInserted(this.listBeen.size()+i);
//        }
        for (int i = 0 ; i < list.size() ;i++){
            this.listBeen.add(list.get(i));
        }
        notifyDataSetChanged();


    }



    private AllCourseFragmentAdapter.OnItemClickListener onItemClickListener;
    /**
     * 设置我的课程列表条目点击
     *
     * */
    public void setOnItemClickListener(AllCourseFragmentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
        void onGoPay(int position);
        void onGoComment(int position);
        void onToCourseDetail(int position);

    }









    class AllCourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_state2)
        Button tvState2;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.ll_to_coursedetai)
        LinearLayout llCoursedetail;
        @BindView(R.id.ll_to_order)
        LinearLayout llToOrder;

        public AllCourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
