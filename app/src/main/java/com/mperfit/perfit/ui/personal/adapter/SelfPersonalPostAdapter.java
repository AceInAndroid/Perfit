package com.mperfit.perfit.ui.personal.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

//public class SelfPersonalPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//
//    private List<NewPersonalBean.DataBean.NoteListBean> mNoteList;
//    private List<NewPersonalBean.DataBean.LikeListBean> mLikeList;
//    private Context mContext;
//    private RecyclerView mRecyclerView;
//
//    private LayoutInflater inflater;
//
//
//    public SelfPersonalPostAdapter(Context context, List<NewPersonalBean.DataBean.NoteListBean> noteList, List<NewPersonalBean.DataBean.LikeListBean> likeList, RecyclerView recyclerView) {
//        inflater = LayoutInflater.from(context);
//        this.mNoteList = noteList;
//        this.mLikeList = likeList;
//        this.mContext = context;
//        this.mRecyclerView = recyclerView;
//        initMoreDataListener();
//    }
//
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new PostViewHolder(inflater.inflate(R.layout.item_personal_post, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//            //对内容位置进行修正,内容位置是-1,
//            //修正列表点击位置
//            final PostViewHolder postViewHolder = (PostViewHolder) holder;
//            ImageLoader.loadPersonalPost(mContext, mNoteList.get(position).getImg_url(), postViewHolder.mImageview);
//            postViewHolder.mImageview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onPostItemClick(position, postViewHolder.mImageview);
//                    }
//                }
//            });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        if (mNoteList != null) {
//            return mNoteList.size();
//        } else {
//            return 0;
//        }
//    }
//
//    public void removePost(int position) {
//
//        try {
//            mNoteList.remove(position);
//        } catch (IndexOutOfBoundsException e){
//            Logger.e("SelfPersonalLikeAdapter" + e.toString());
//        }
//
//        try {
//            notifyItemRemoved(position);
//            if (mNoteList!=null && mNoteList.size()-position >0){
//                notifyItemRangeChanged(position,mNoteList.size()-position);
//            }
//        }catch (NullPointerException e){
//            Logger.e("SelfPersonalLikeAdapter" + e.toString());
//        }
//    }
//
//
//    class PostViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.iv_image)
//        ImageView mImageview;
//
//        public PostViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//
//    class PersonalHeaderViewHolder extends RecyclerView.ViewHolder {
//
//
//        @BindView(R.id.ll_post)
//        LinearLayout llPost;
//        @BindView(R.id.v_indicator1)
//        View vIndicator1;
//        @BindView(R.id.ll_like)
//        LinearLayout llLike;
//        @BindView(R.id.v_indicator2)
//        View vIndicator2;
//
//        public PersonalHeaderViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//
//    public void loadPostData(List<NewPersonalBean.DataBean.NoteListBean> list) {
//        this.mNoteList = list;
//        notifyDataSetChanged();
//    }
//
//    public void addNewData(PersonalMyPostBean.DataBean dataBean) {
//        if (mNoteList==null){
//            return;
//        }
//        int startPosition = this.mNoteList.size();
//        if (dataBean==null){
//            return;
//        }
//        List<PersonalMyPostBean.DataBean.ListBean> list = dataBean.getList();
//        if (list==null){
//            return;
//        }
//
//        for (int i = 0; i < list.size(); i++) {
//            PersonalMyPostBean.DataBean.ListBean listBean = list.get(i);
//            NewPersonalBean.DataBean.NoteListBean bean = new NewPersonalBean.DataBean.NoteListBean();
//            String imgUrl = listBean.getImg_url();
//            long noteId = listBean.getNote_id();
//            bean.setImg_url(imgUrl);
//            bean.setNote_id(noteId);
//            this.mNoteList.add(bean);
//            notifyItemInserted(mNoteList.size());
//        }
//
//        notifyItemRangeChanged(startPosition,this.mNoteList.size());
//
//    }
//
//
//
//
//
//    OnItemClickListener onItemClickListener;
//
//    /**
//     * 设置列表条目点击
//     */
//    public void setOnItemClickListener(SelfPersonalPostAdapter.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener {
//        void onPostItemClick(int position, View view);
//
//
//    }
//
//    private OnLoadingListener mOnLoadingListener;
//
//    /**
//     * 加载更多接口
//     */
//    public interface OnLoadingListener {
//        void loadMorePost();
//    }
//
//    /**
//     * 设置监听接口
//     *
//     * @param onLoadingListener onLoadingListener
//     */
//    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
//        this.mOnLoadingListener = onLoadingListener;
//    }
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
////                    int lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
//
////                    if(lastVisiblePosition >= gridLayoutManager.getItemCount() - 1){
////                        if (mOnLoadingListener!=null){
////                            if (isPost){
////                                mOnLoadingListener.loadMorePost();
////                            } else {
////                                mOnLoadingListener.loadMoreLike();
////                            }
////                        }
////
////                    }
//
//                    if (isSlideToBottom(mRecyclerView)) {
//                        if (mOnLoadingListener != null) {
//                            mOnLoadingListener.loadMorePost();
//                        }
//                    }
//                }
//            }
//        });
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
//    private static final String TAG = "PersonalPostAdapter";
//
//
////    /**
////     * 将Header、Footer挂靠到RecyclerView
////     *
////     * @param recyclerView
////     */
////    @Override
////    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
////        super.onAttachedToRecyclerView(recyclerView);
////        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
////        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
////            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
////            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
////                @Override
////                public int getSpanSize(int position) {
////                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
////                    return (isHeader(position) || isFooter(position)) ? gridLayoutManager
////                            .getSpanCount() : 1;
////                }
////            });
////        }
////    }
//
//
//    /**
//     * 判断是否是Header的位置
//     * 如果是Header的则返回true否则返回false
//     *
//     * @param position
//     * @return
//     */
//    public boolean isHeader(int position) {
//        return position == 0;
//    }
//
//    /**
//     * 判断是否是Footer的位置
//     * 如果是Footer的位置则返回true否则返回false
//     *
//     * @param position
//     * @return
//     */
//    public boolean isFooter(int position) {
//        return position == 0;
//    }
//
//
//}
