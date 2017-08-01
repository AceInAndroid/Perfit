package com.mperfit.perfit.ui.personal.adapter;

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
import com.mperfit.perfit.model.bean.PersonalMyPostBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2017/2/17.
 */


//public class SelfPersonalLikeAdapter extends RecyclerView.Adapter<SelfPersonalLikeAdapter.LikeViewHolder> {
//
//
//    private final LayoutInflater inflater;
//    private Context mContext;
//    private RecyclerView mRecyclerView;
//
//    public SelfPersonalLikeAdapter(Context context,  RecyclerView mLikeRecyclerView) {
//        inflater = LayoutInflater.from(context);
//        this.mContext = context;
//        this.mRecyclerView = mLikeRecyclerView;
//        initMoreDataListener();
//    }
//
//    @Override
//    public LikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new LikeViewHolder(inflater.inflate(R.layout.item_personal_post,parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(final LikeViewHolder holder, final int position) {
//        ImageLoader.loadPersonalPost(mContext, mLikeList.get(position).getImg_url(),holder.mImageview);
//        holder.mImageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemClickListener!=null){
//                    onItemClickListener.onItemClick(position,holder.mImageview);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mLikeList !=null){
//            return mLikeList.size();
//        } else {
//            return 0;
//        }
//    }
//
//    public void loadLikeData(List<NewPersonalBean.DataBean.LikeListBean> list) {
//        this.mLikeList =list;
//        notifyDataSetChanged();
//    }
//
//    class LikeViewHolder extends RecyclerView.ViewHolder{
//
//        @BindView(R.id.iv_image)
//        ImageView mImageview;
//
//        public LikeViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
//
//
//
//    public void addNewLikeData(PersonalMyPostBean.DataBean dataBean) {
//        List<PersonalMyPostBean.DataBean.ListBean> list = dataBean.getList();
//        for (int i = 0; i < list.size(); i++) {
//            PersonalMyPostBean.DataBean.ListBean listBean = list.get(i);
//            String imgUrl = listBean.getImg_url();
//            long noteId = listBean.getNote_id();
//            NewPersonalBean.DataBean.LikeListBean bean = new NewPersonalBean.DataBean.LikeListBean();
//            bean.setImg_url(imgUrl);
//            bean.setNote_id(noteId);
//            this.mLikeList.add(bean);
//            notifyItemInserted(mLikeList.size());
//        }
////        notifyDataSetChanged();
//    }
//
//
//    /**
//     * @param position
//     */
//    public void updateLikeState(int position) {
//        try {
//            mLikeList.remove(position);
//        } catch (IndexOutOfBoundsException e){
//            Logger.e("SelfPersonalLikeAdapter" + e.toString());
//        }
//
//        try {
//            notifyItemRemoved(position);
//            if (mLikeList!=null && mLikeList.size()-position >0){
//                notifyItemRangeChanged(position,mLikeList.size()-position);
//            }
//
//        }catch (NullPointerException e){
//            Logger.e("SelfPersonalLikeAdapter" + e.toString());
//        }
//
//
//        if (onDataChangeListener !=null){
//            onDataChangeListener.onDataChange(mLikeList);
//        }
//    }
//
//
//
//
//    private SelfPersonalLikeAdapter.OnItemClickListener onItemClickListener;
//
//    /**
//     * 设置列表条目点击
//     */
//    public void setOnItemClickListener(SelfPersonalLikeAdapter.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int position, View view);
//    }
//
//
//
//    private OnLikeDataChangeListener onDataChangeListener;
//
//    public void setOnLikeDataChangeListener(OnLikeDataChangeListener onDataChangeListener) {
//        this.onDataChangeListener = onDataChangeListener;
//    }
//
//
//    public interface OnLikeDataChangeListener {
//        void onDataChange(List<NewPersonalBean.DataBean.LikeListBean> likeList);
//    }
//
//
//
//
//    public void initMoreDataListener() {
//
//        final GridLayoutManager gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
//
//
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//
//
////                    int lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
////
////                    if (lastVisiblePosition >= gridLayoutManager.getItemCount() - 1) {
////                        if (mOnLoadingListener != null) {
////                                mOnLoadingListener.loadMoreLike();
////                        }
////
////                    }
//
//
//                    if (isSlideToBottom(mRecyclerView)) {
//                        if (mOnLoadingListener != null) {
//                            mOnLoadingListener.loadMoreLike();
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//
//    private SelfPersonalLikeAdapter.OnLoadingListener mOnLoadingListener;
//
//    /**
//     * 加载更多接口
//     */
//    public interface OnLoadingListener {
//        void loadMoreLike();
//    }
//
//
//    /**
//     * 设置监听接口
//     *
//     * @param onLoadingListener onLoadingListener
//     */
//    public void setOnLoadingListener(SelfPersonalLikeAdapter.OnLoadingListener onLoadingListener) {
//        this.mOnLoadingListener = onLoadingListener;
//    }
//
//
//    public static boolean isSlideToBottom(RecyclerView recyclerView) {
//        if (recyclerView == null) return false;
//        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
//                >= recyclerView.computeVerticalScrollRange())
//            return true;
//        return false;
//    }
//

//}

