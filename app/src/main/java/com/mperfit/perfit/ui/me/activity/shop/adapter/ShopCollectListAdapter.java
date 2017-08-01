package com.mperfit.perfit.ui.me.activity.shop.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.ShopCollectBean;
import com.mperfit.perfit.ui.me.activity.articlecollect.adapter.ArticleCollectAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2016/11/29.
 */

public class ShopCollectListAdapter extends RecyclerView.Adapter<ShopCollectListAdapter.ShopCollectViewHolder> {


    private Context mContext;
    private final LayoutInflater inflater;
    private List<ShopCollectBean.DataBean.ListBean> list;

    public ShopCollectListAdapter(Context mContext, List<ShopCollectBean.DataBean.ListBean> list) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
    }


    @Override
    public ShopCollectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_shop_collect, parent, false);
        return new ShopCollectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopCollectViewHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position).getName());
        holder.tvIntroduction.setText(list.get(position).getIntroduction());
        holder.tvAddress.setText(list.get(position).getAddress());
        ImageLoader.load(mContext, list.get(position).getImg_url(), holder.ivImg);
        holder.cbCollect.setSelected(list.get(position).isChecked());
        if (list.get(position).isChecked()){
            Logger.e("onBindViewHolder 的 sellect" + "true");
        } else {
            Logger.e("onBindViewHolder 的 sellect" + "false");

        }
        setLikeTouch(holder.cbCollect);
        holder.cbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //监听checkbox
                if (onCollectedListener != null) {
                    onCollectedListener.onItemClick(position, holder.cbCollect, !isChecked);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener !=null){
                    onItemClickListener.onItemClick(position,null);
                }
            }
        });
    }

    private void setLikeTouch(View view) {
        Rect delegateArea = new Rect();
        // Hit rectangle in parent's coordinates
        view.getHitRect(delegateArea);


        // 扩大触摸区域矩阵值
        delegateArea.left -= 200;
        delegateArea.top -= 200;
        delegateArea.right += 200;
        delegateArea.bottom += 200;

        /**
         * 构造扩大后的触摸区域对象
         * 第一个构造参数表示  矩形面积
         * 第二个构造参数表示 被扩大的触摸视图对象
         * <也是本demo最核心用到的类之一>
         */
        TouchDelegate expandedArea = new TouchDelegate(delegateArea, view);
        if(View.class.isInstance(view.getParent())){
            // 设置视图扩大后的触摸区域
            ((View)view.getParent()).setTouchDelegate(expandedArea);
        }

    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void addMore(List<ShopCollectBean.DataBean.ListBean> list) {
        for (ShopCollectBean.DataBean.ListBean listBean : list){
            listBean.setChecked(true);
            list.add(listBean);
        }
        notifyDataSetChanged();
    }

    public void addData(List<ShopCollectBean.DataBean.ListBean> list) {
        this.list =list;
        notifyDataSetChanged();
    }



    class ShopCollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_introduction)
        TextView tvIntroduction;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.cb_collect)
        CheckBox cbCollect;


        public ShopCollectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void updateCheckState(boolean isChck, int position) {
        Logger.e("ischeck:" + isChck + "position" + position);
        list.get(position).setChecked(isChck);
        notifyDataSetChanged();
    }


    /**
     * 设置条目点击
     */

    private ShopCollectListAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ShopCollectListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


    /**
     * 设置收藏/取消收藏监听
     */

    private ShopCollectListAdapter.OnCollectedListener onCollectedListener;

    public void setOnItemClickListener(ShopCollectListAdapter.OnCollectedListener onCollectedListener) {
        this.onCollectedListener = onCollectedListener;
    }

    public interface OnCollectedListener {
        void onItemClick(int position, View view, boolean isCheck);
    }


}
