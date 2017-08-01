package com.mperfit.perfit.ui.personal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/14.
 */

public class MyContentLikeAdapter extends BaseQuickAdapter<PersonalMyPostBean.DataBean.ListBean,BaseViewHolder> {

    private  RecyclerView mRecyclerView;
    private  List<PersonalMyPostBean.DataBean.ListBean> dataList;

    public MyContentLikeAdapter(int layoutResId, List<PersonalMyPostBean.DataBean.ListBean> data, RecyclerView mRecyclerView) {
        super(layoutResId, data);
        this.dataList = data;
        this.mRecyclerView = mRecyclerView;

    }



    @Override
    protected void convert(final BaseViewHolder helper, PersonalMyPostBean.DataBean.ListBean item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_image);
        ImageLoader.loadPersonalPost(mContext,item.getImg_url(),imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onPostItemClick(helper.getAdapterPosition(),v);
                }
            }
        });
    }






    MyContentLikeAdapter.OnItemClickListener onItemClickListener;

    /**
     * 设置列表条目点击
     */
    public void setOnItemClickListener(MyContentLikeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onPostItemClick(int position, View view);
    }






    private MyContentLikeAdapter.OnLoadingListener mOnLoadingListener;

    /**
     * 加载更多接口
     */
    public interface OnLoadingListener {
        void loadMorePost();
    }

    /**
     * 设置监听接口
     *
     * @param onLoadingListener onLoadingListener
     */
    public void setOnLoadingListener(MyContentLikeAdapter.OnLoadingListener onLoadingListener) {
        this.mOnLoadingListener = onLoadingListener;
    }


    public void initMoreDataListener() {

//        final GridLayoutManager gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    int lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();

//                    if(lastVisiblePosition >= gridLayoutManager.getItemCount() - 1){
//                        if (mOnLoadingListener!=null){
//                            if (isPost){
//                                mOnLoadingListener.loadMorePost();
//                            } else {
//                                mOnLoadingListener.loadMoreLike();
//                            }
//                        }
//
//                    }

                    if (isSlideToBottom(mRecyclerView)) {
                        if (mOnLoadingListener != null) {
                            mOnLoadingListener.loadMorePost();
                        }
                    }
                }
            }
        });
    }


    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }





    /**
     * @param position
     */
    public void updateLikeState(int position) {
        try {
            dataList.remove(position);
            remove(position);
        } catch (IndexOutOfBoundsException e){
            Logger.e("SelfPersonalLikeAdapter" + e.toString());
        }

        try {
//            notifyItemRemoved(position);
            if (dataList!=null && dataList.size()-position >0){
                notifyItemRangeChanged(position,dataList.size()-position);
            }

        }catch (NullPointerException e){
            Logger.e("SelfPersonalLikeAdapter" + e.toString());
        }

    }



    public void setData(List<PersonalMyPostBean.DataBean.ListBean> data){
        dataList = data;
        addData(data);

    }


    public void removePost(int position) {
        if (dataList==null){
            return;
        }
        try {
            dataList.remove(position);
            remove(position);
        } catch (Exception e){
            Logger.e("SelfPersonalLikeAdapter" + e.toString());
        }

        try {
            if (dataList!=null && dataList.size()-position >0){
                notifyItemRangeChanged(position,dataList.size()-position);
            }

        }catch (NullPointerException e){
            Logger.e("SelfPersonalLikeAdapter" + e.toString());
        }
    }

    public void addMoreData(List<PersonalMyPostBean.DataBean.ListBean> data){
        addData(data);
        for (PersonalMyPostBean.DataBean.ListBean listBean : data) {
            dataList.add(listBean);
        }
    }





}
