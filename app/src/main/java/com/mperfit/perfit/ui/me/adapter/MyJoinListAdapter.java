package com.mperfit.perfit.ui.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.MyJoinedActivityBean;
import com.mperfit.perfit.model.bean.PersonalMyGameBean;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 16/11/3.
 */

public class MyJoinListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final LayoutInflater inflater;
    private List<PersonalMyGameBean.DataBean.ListBean> list;

    private OnItemClickListener onItemClickListener;

    public MyJoinListAdapter(Context context, List<PersonalMyGameBean.DataBean.ListBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list =list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_myjoin, parent, false);
        return new MyJoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyJoinViewHolder myJoinViewHolder = (MyJoinViewHolder) holder;

        myJoinViewHolder.tvTitle.setText(list.get(position).getName());
        myJoinViewHolder.tvData.setText(list.get(position).getTime());
        myJoinViewHolder.mTvAddress.setText(list.get(position).getAddress());
        int status = list.get(position).getStatus();
        if (status == 1){
            //即将开始
            myJoinViewHolder.tvState.setText(R.string.join_neabystatr);
        } else if (status ==2 ){

            myJoinViewHolder.tvState.setText(R.string.join_ing);
        } else {
            myJoinViewHolder.tvState.setText(R.string.join_end);
            myJoinViewHolder.tvState.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_myjoin_end));
        }

        ImageLoader.load(context,list.get(position).getImg_url(),myJoinViewHolder.ivImg);

        myJoinViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position,view);
            }
        });


    }


    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }
        return list.size();
    }

    public  static  class MyJoinViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_address)
        TextView mTvAddress;

        public MyJoinViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //加载
    public void addData(List<PersonalMyGameBean.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //加载更多
    public void addMoreDate(List<PersonalMyGameBean.DataBean.ListBean> list) {
        for (int i = 0 ; i < list.size();i ++){
            this.list.add(list.get(i));
        }
        notifyDataSetChanged();
    }



    //设置文章列表条目点击
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


}
