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
import com.mperfit.perfit.model.bean.OthersUserCenterBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2017/2/17.
 */

public class PersonalPostAdapter extends RecyclerView.Adapter<PersonalPostAdapter.PostViewHolder> {


    private final LayoutInflater inflater;
    private List<OthersUserCenterBean.DataBean.NoteListBean> noteList;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public PersonalPostAdapter(Context context, List<OthersUserCenterBean.DataBean.NoteListBean> noteList, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.noteList = noteList;
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        initMoreDataListener();
    }


    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostViewHolder(inflater.inflate(R.layout.item_personal_post, parent, false));
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        ImageLoader.loadPersonalPost(mContext, noteList.get(position).getImg_url(), holder.mImageview);
        holder.mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, holder.mImageview);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (noteList != null) {
            return noteList.size();
        } else {
            return 0;
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView mImageview;

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void loadData(List<OthersUserCenterBean.DataBean.NoteListBean> list) {
        this.noteList = list;
        notifyDataSetChanged();
    }


    public void addNewData(List<PersonalMyPostBean.DataBean.ListBean> list) {
        for (PersonalMyPostBean.DataBean.ListBean listBean : list) {
            OthersUserCenterBean.DataBean.NoteListBean dataBean = new OthersUserCenterBean.DataBean.NoteListBean();
            dataBean.setNote_id(listBean.getNote_id());
            dataBean.setImg_url(listBean.getImg_url());
            this.noteList.add(dataBean);
            notifyItemInserted(noteList.size());
        }
    }



    /**
     * @param position
     * @param type     1 是添加 0 是移除
     */
    public void updateLikeState(int position) {

        noteList.remove(position);
        notifyItemChanged(position);
    }


    PersonalPostAdapter.OnItemClickListener onItemClickListener;

    /**
     * 设置列表条目点击
     */
    public void setOnItemClickListener(PersonalPostAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


    public void initMoreDataListener() {

        final GridLayoutManager gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

//
//                    int lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
//
//                    if (lastVisiblePosition >= gridLayoutManager.getItemCount() - 1) {
//                        if (mOnLoadingListener != null) {
//                                mOnLoadingListener.loadMorePost();
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


    private PersonalPostAdapter.OnLoadingListener mOnLoadingListener;

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
    public void setOnLoadingListener(PersonalPostAdapter.OnLoadingListener onLoadingListener) {
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
