package com.mperfit.perfit.ui.otherspersonal.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.NewPersonalBean;
import com.mperfit.perfit.model.bean.OthersUserCenterBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2017/2/17.
 */


public class PersonalLikeAdapter extends RecyclerView.Adapter<PersonalLikeAdapter.LikeViewHolder> {


    private final LayoutInflater inflater;
    private List<OthersUserCenterBean.DataBean.LikeListBean> mLikeList;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public PersonalLikeAdapter(Context context, List<OthersUserCenterBean.DataBean.LikeListBean> noteList,RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.mLikeList = noteList;
        this.mContext = context;
        mRecyclerView =recyclerView;
        initMoreDataListener();
    }


    @Override
    public LikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LikeViewHolder(inflater.inflate(R.layout.item_personal_post,parent,false));
    }

    @Override
    public void onBindViewHolder(final LikeViewHolder holder, final int position) {
        ImageLoader.load(mContext, mLikeList.get(position).getImg_url(),holder.mImageview);
        holder.mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position,holder.mImageview);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mLikeList !=null){
            return mLikeList.size();
        } else {
            return 0;
        }
    }

    class LikeViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_image)
        ImageView mImageview;

        public LikeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public void loadData(List<OthersUserCenterBean.DataBean.LikeListBean> list){
        this.mLikeList = list;
        notifyDataSetChanged();
    }



    /**
     * @param position
     */
    public void updateLikeState(int position) {

        mLikeList.remove(position);
        notifyItemChanged(position);
    }




    private PersonalLikeAdapter.OnItemClickListener onItemClickListener;

    /**
     * 设置列表条目点击
     */
    public void setOnItemClickListener(PersonalLikeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }



    public void addNewData(List<PersonalMyPostBean.DataBean.ListBean> list) {
        for (PersonalMyPostBean.DataBean.ListBean listBean : list) {
            OthersUserCenterBean.DataBean.LikeListBean dataBean = new OthersUserCenterBean.DataBean.LikeListBean();
            dataBean.setNote_id(listBean.getNote_id());
            dataBean.setImg_url(listBean.getImg_url());
            this.mLikeList.add(dataBean);
            notifyItemInserted(mLikeList.size());
        }
    }


    public void initMoreDataListener() {

        final GridLayoutManager gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {


//                    int lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
//
//                    if (lastVisiblePosition >= gridLayoutManager.getItemCount() - 1) {
//                        if (mOnLoadingListener != null) {
//                                mOnLoadingListener.loadMoreLike();
//                        }
//
//                    }


                    if (isSlideToBottom(mRecyclerView)) {
                        if (mOnLoadingListener != null) {
                            mOnLoadingListener.loadMoreLike();
                        }
                    }
                }
            }
        });
    }


    private PersonalLikeAdapter.OnLoadingListener mOnLoadingListener;

    /**
     * 加载更多接口
     */
    public interface OnLoadingListener {
        void loadMoreLike();
    }


    /**
     * 设置监听接口
     *
     * @param onLoadingListener onLoadingListener
     */
    public void setOnLoadingListener(PersonalLikeAdapter.OnLoadingListener onLoadingListener) {
        this.mOnLoadingListener = onLoadingListener;
    }


    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }



}

