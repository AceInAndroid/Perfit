package com.mperfit.perfit.ui.newhome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.model.bean.NewHomeBean;
import com.mperfit.perfit.model.bean.SquareListBean;
import com.mperfit.perfit.ui.community.adapter.SquareFragmentAdapter;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.utils.SystemUtil;
import com.mperfit.perfit.widget.LoadingView;
import com.mperfit.perfit.widget.nestfulllistview.NestFullListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangbing on 2017/2/10.
 */

public class NewHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private Context mContext;
    private NewHomeBean.DataBean dataBean;
    private List<NewHomeBean.DataBean.NoteListBean> noteList;
    private ContentViewHolder contentViewHolder;
    /**
     * 喜欢数量
     */
    private int likeCount;
    private int mCommentCount;


    public enum ITEM_TYPE {
        ITEM_TOP,       //广告
        ITEM_TAGS,        //标签
        ITEM_TITLE,      //标题
        ITEM_CONTENT    //list
    }


    private String[] colors = new String[]{"#ddcfa6", "#c0dfd3", "#8c9bb9", "#caa196", "#bbcf97"};


    public NewHomeAdapter(Context mContext, NewHomeBean.DataBean dataBean) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.dataBean = dataBean;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.ITEM_TOP.ordinal();
        } else if (position == 1) {
            return ITEM_TYPE.ITEM_TAGS.ordinal();
        } else if (position == 2) {
            return ITEM_TYPE.ITEM_TITLE.ordinal();
        } else {
            return ITEM_TYPE.ITEM_CONTENT.ordinal();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            return new BannerAdsViewHolder(inflater.inflate(R.layout.item_banner_ads_home, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TAGS.ordinal()) {
            final TagsViewHolder tagsViewHolder = new TagsViewHolder(inflater.inflate(R.layout.item_home_ranking_list, parent, false));
            return tagsViewHolder;
        } else if (viewType == ITEM_TYPE.ITEM_TITLE.ordinal()) {
            return new TitleViewHolder(inflater.inflate(R.layout.item_home_title, parent, false));
        } else {
            return new ContentViewHolder(inflater.inflate(R.layout.item_home_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        noteList = dataBean.getNote_list();
        final List<NewHomeBean.DataBean.BannerListBean> bannerList = dataBean.getBanner_list();
        if (holder instanceof BannerAdsViewHolder) {
            //广告条
            List<String> mImageList = new ArrayList<>();
            List<String> mTitleList = new ArrayList<>();
            for (NewHomeBean.DataBean.BannerListBean item : bannerList) {
                mImageList.add(item.getImg_url());
                mTitleList.add(item.getDepict());
            }
            BannerAdsViewHolder adsViewHolder = (BannerAdsViewHolder) holder;
            adsViewHolder.vpTop.setData(mImageList, mTitleList);
            adsViewHolder.vpTop.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                    ImageLoader.loadBannaerAds(mContext, model, itemView);
                }
            });

            adsViewHolder.vpTop.setDelegate(new BGABanner.Delegate<ImageView, String>() {
                @Override
                public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                    if (onClickListener != null) {
                        onClickListener.onBannerItemClick(position, itemView);
                    }
                }
            });

        } else if (holder instanceof TagsViewHolder) {
            //榜单入口
            TagsViewHolder rankHolder = (TagsViewHolder) holder;
            rankHolder.mRivImagview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onStarRankingListClick();
                    }
                }
            });

            rankHolder.mRivImagview2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onScoreboardClick();
                    }


                }
            });


        } else if (holder instanceof TitleViewHolder) {
            //标题栏


        } else {
            //对内容位置进行修正
            final int contentPosition;
            contentPosition = position - 3;
            final NewHomeBean.DataBean.NoteListBean listBean = noteList.get(contentPosition);
            contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.tvName.setText(listBean.getUser_name());
            contentViewHolder.tvTime.setText(listBean.getTime());
            ImageLoader.loadHead(mContext, listBean.getIcon(), contentViewHolder.ivHead);




            int imgWidth = listBean.getImg_width();
            int imgHeight = listBean.getImg_height();
            int vw = App.SCREEN_WIDTH;
            float scale = (float) vw / (float) imgWidth;
            int vh = Math.round(imgHeight * scale);

            ViewGroup.LayoutParams params = contentViewHolder.ivFeedCenter.getLayoutParams();

            //横屏图片(长大于宽的 不变)
            if (imgWidth > imgHeight) {
                if (contentViewHolder.ivFeedCenter.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    contentViewHolder.ivFeedCenter.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                params.height = vh;
            } else {
                if (App.SCREEN_HEIGHT != 0 && vh > App.SCREEN_HEIGHT * 0.70) {
                    params.height = (int) (App.SCREEN_HEIGHT * 0.70);

                } else {
                    if (contentViewHolder.ivFeedCenter.getScaleType() != ImageView.ScaleType.FIT_XY) {
                        contentViewHolder.ivFeedCenter.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    params.height = vh;
                }
            }



            ImageLoader.loadCommuniteList(mContext, listBean.getImg_url_android(), contentViewHolder.ivFeedCenter,contentViewHolder.pb,imgWidth,imgHeight);

            try {
                if (listBean.getContent() != null && listBean.getContent().equals("")) {
                    contentViewHolder.tvContent.setVisibility(View.GONE);
                } else {
                    contentViewHolder.tvContent.setText(listBean.getContent());
                }

                if (listBean.getLike() != null && listBean.getLike().equals("1")) {
                    //则喜欢
                    contentViewHolder.btnLike.setBackgroundResource(0);
                    contentViewHolder.btnLike.setBackgroundResource(R.drawable.home_heart_selected);
                } else {
                    //不喜欢

                    contentViewHolder.btnLike.setBackgroundResource(0);
                    contentViewHolder.btnLike.setBackgroundResource(R.drawable.home_list_heart);
                }
                if (!SharedPreferenceUtil.isMe(noteList.get(contentPosition).getUser_id())) {
                    if (contentViewHolder.mFlFollow.getVisibility() == View.GONE) {
                        contentViewHolder.mFlFollow.setVisibility(View.VISIBLE);
                    }
                    if (listBean.getFriend() != null && listBean.getFriend().equals("1")) {
                        //已关注
                        contentViewHolder.tvFollowText.setText("已关注");
                        contentViewHolder.tvFollowText.setTextColor(mContext.getResources().getColor(R.color.white));
                        //设置margin
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) contentViewHolder.ivFollowIcon.getLayoutParams();
                        layoutParams.setMargins(SystemUtil.dp2px(mContext, 9), 0, 0, 0);
                        contentViewHolder.ivFollowIcon.setLayoutParams(layoutParams);
                        contentViewHolder.ivFollowIcon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_icon_concern));
                        contentViewHolder.imageButton.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_myjoin_bg));
                    } else {
                        //未关注
                        contentViewHolder.tvFollowText.setText("关注");
                        //设置margin
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) contentViewHolder.ivFollowIcon.getLayoutParams();
                        layoutParams.setMargins(SystemUtil.dp2px(mContext, 16), 0, 0, 0);
                        contentViewHolder.ivFollowIcon.setLayoutParams(layoutParams);
                        contentViewHolder.tvFollowText.setTextColor(mContext.getResources().getColor(R.color.groupbuying_price));
                        contentViewHolder.ivFollowIcon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_icon_add));
                        contentViewHolder.imageButton.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_bg_follow));
                    }
                } else {
                    contentViewHolder.mFlFollow.setVisibility(View.GONE);
                }
            } catch (NullPointerException e) {
                Logger.e("NewhomeAdapter:" + e.getMessage());
            }


            likeCount = listBean.getLike_count();
            mCommentCount = listBean.getComment_count();
            contentViewHolder.tvHeartCount.setText(likeCount + "");
            contentViewHolder.tvConmmentCount.setText(mCommentCount + "");
            contentViewHolder.ivFeedCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        ImageView iv = (ImageView) v.findViewById(R.id.ivFeedCenter);
                        onClickListener.onPostItemClick(contentPosition, iv);
                    }
                }
            });

            contentViewHolder.tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        ImageView iv = (ImageView) v.findViewById(R.id.ivFeedCenter);
                        onClickListener.onPostItemClick(contentPosition, iv);
                    }
                }
            });
//            setBackTouch(contentViewHolder.btnLike);

            contentViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {


                        if (listBean.getLike().equals("0")) {
                            //则喜欢
                            onClickListener.onBtnLikeClick(contentPosition);
                        } else {
                            //取消喜欢
                            onClickListener.onBtnUnLikeClick(contentPosition);
                        }
                    }
                }
            });

            contentViewHolder.btnComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onBtnCommentClick(contentPosition);
                    }
                }
            });


            contentViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onBtnShareClick(contentPosition);
                    }
                }
            });

            contentViewHolder.ivHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onBtnHeadAndNameClick(contentPosition);
                    }
                }
            });

            contentViewHolder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onBtnHeadAndNameClick(contentPosition);
                    }
                }
            });

            contentViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listBean.getFriend().equals("0")) {
                        if (onClickListener != null) {
                            onClickListener.onBtnFollowClick(contentPosition);
                        }
                    } else {
                        if (onClickListener != null) {
                            onClickListener.onBtnRemoveFollowClick(contentPosition);
                        }

                    }

                }
            });

        }
    }


//    /**
//     * 设置返回键触控区域
//     * @param ibBack
//     */
//    public void setBackTouch(View ibBack ) {
//        Rect delegateArea = new Rect();
//        // Hit rectangle in parent's coordinates
//        ibBack.getHitRect(delegateArea);
//
//
//        // 扩大触摸区域矩阵值
//        delegateArea.left -= 100;
//        delegateArea.top -= 100;
//        delegateArea.right += 100;
//        delegateArea.bottom += 100;
//
//        /**
//         * 构造扩大后的触摸区域对象
//         * 第一个构造参数表示  矩形面积
//         * 第二个构造参数表示 被扩大的触摸视图对象
//         * <也是本demo最核心用到的类之一>
//         */
//        TouchDelegate expandedArea = new TouchDelegate(delegateArea, ibBack);
//        if(View.class.isInstance(ibBack.getParent())){
//            // 设置视图扩大后的触摸区域
//            ((View)ibBack.getParent()).setTouchDelegate(expandedArea);
//        }
//
//    }


    @Override
    public int getItemCount() {
        if (dataBean != null && dataBean.getNote_list() != null) {
            if (dataBean.getNote_list().size() > 0) {
                return dataBean.getNote_list().size() + 3;
            }

            if (dataBean.getNote_list().size() == 0) {
                return 0;
            }
        } else {
            return 0;
        }
        return 0;
    }

    class BannerAdsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vp_top)
        BGABanner vpTop;

        public BannerAdsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    class TagsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.riv_imagview1)
        RoundedImageView mRivImagview1;
        @BindView(R.id.iv_imageView2)
        RoundedImageView mRivImagview2;

        public TagsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        public TitleViewHolder(View itemView) {
            super(itemView);
        }
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ivFeedCenter)
        ImageView ivFeedCenter;
        @BindView(R.id.btnLike)
        ImageButton btnLike;
        @BindView(R.id.tv_heart_count)
        TextView tvHeartCount;
        @BindView(R.id.btnComments)
        ImageButton btnComments;
        @BindView(R.id.tv_conmment_count)
        TextView tvConmmentCount;
        @BindView(R.id.btnMore)
        ImageButton btnMore;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.iv_follow_icon)
        ImageView ivFollowIcon;
        @BindView(R.id.tv_follow_text)
        TextView tvFollowText;
        @BindView(R.id.ib_follow_bg)
        ImageButton imageButton;
        @BindView(R.id.fl_follow)
        FrameLayout mFlFollow;
        @BindView(R.id.pb_prograss)
        LoadingView pb;
        @BindView(R.id.fl_image_container)
        FrameLayout flImageContainer;

//        @BindView(R.id.nest_fulllistview)
//        NestFullListView mNextFullListview;


        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void addData(List<NewHomeBean.DataBean.NoteListBean> mNoteList) {

        if (mNoteList == null) {
            return;
        }
        this.noteList = mNoteList;
        notifyDataSetChanged();
    }

    public void addData(NewHomeBean.DataBean dataBean) {
        this.dataBean = dataBean;
        notifyDataSetChanged();
    }

    /**
     * @param position
     * @param type     1 是添加 0 是移除
     */
    public void updateLikeState(int position, int type) {
        if (noteList == null) {
            return;
        }
        if (type == 1) {
            int count = noteList.get(position).getLike_count();
            count++;
            noteList.get(position).setLike_count(count);
            noteList.get(position).setLike("1");
        } else {
            int count = noteList.get(position).getLike_count();
            count--;
            noteList.get(position).setLike_count(count);
            noteList.get(position).setLike("0");
        }
        notifyItemChanged(position + 3);
        Logger.e("adapter中改变的位置" + (position + 3));
    }


    /**
     * @param position
     * @param type     1 是添加 0 是移除
     */
    public void updateLikeState(int position, int type, long id) {
        if (noteList == null) {
            return;
        }

        if (type == 1) {
            for (int i = 0; i < noteList.size(); i++) {
                long itemId = noteList.get(i).getId();
                if (itemId == id) {
                    int count = noteList.get(i).getLike_count();
                    count++;
                    noteList.get(i).setLike_count(count);
                    noteList.get(i).setLike("1");
                    notifyItemChanged(i + 3);
                }
            }
        } else {
            for (int i = 0; i < noteList.size(); i++) {
                long itemId = noteList.get(i).getId();
                if (itemId == id) {
                    int count = noteList.get(i).getLike_count();
                    count--;
                    noteList.get(i).setLike_count(count);
                    noteList.get(i).setLike("0");
                    notifyItemChanged(i + 3);
                }
            }
        }

        Logger.e("adapter中改变的位置" + (position + 3));
    }


    public void updateCommentState(int position, int count, long id) {
        if (position == -1) {
            return;
        }
        for (NewHomeBean.DataBean.NoteListBean noteListBean : noteList) {
            if (noteListBean.getId() == id) {
                noteListBean.setComment_count(count);
            }
        }

        notifyDataSetChanged();
        Logger.e("adapter中改变的位置" + (position + 3) + "id" + id);
    }


    /**
     * @param position
     */
    public void deleteItem(int position) {
        if (noteList == null) {
            return;
        }
        noteList.remove(position);
        notifyDataSetChanged();
    }


    /**
     * 更改关注的状态
     *
     * @param changedUserid 用户id
     * @param type          1是关注 0是移除关注
     */
    public void updateFollowState(long changedUserid, int type) {

        if (noteList == null) {
            return;
        }

        if (type == 1) {
            //遍历首页帖子所有的id字段 改变

            for (int i = 0; i < noteList.size(); i++) {
                NewHomeBean.DataBean.NoteListBean listBean = noteList.get(i);
                long userId = listBean.getUser_id();
                if (userId == changedUserid) {
                    listBean.setFriend("1");
                }
                notifyDataSetChanged();
            }
        } else {
            for (int i = 0; i < noteList.size(); i++) {
                NewHomeBean.DataBean.NoteListBean listBean = noteList.get(i);
                long userId = listBean.getUser_id();
                if (userId == changedUserid) {
                    listBean.setFriend("0");
                }
                notifyDataSetChanged();

            }


        }
    }


    private OnClickListener onClickListener;

    /**
     * 设置文章列表条目点击
     */

    public void setOnClickListener(OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }

    public interface OnClickListener {
        void onPostItemClick(int position, View view);

        void onStarRankingListClick();

        void onScoreboardClick();

        void onBannerItemClick(int position, View view);

        void onBtnFollowClick(int position);

        void onBtnLikeClick(int position);

        void onBtnUnLikeClick(int position);

        void onBtnCommentClick(int position);

        void onBtnShareClick(int position);

        void onBtnHeadAndNameClick(int position);

        void onBtnRemoveFollowClick(int contentPosition);



    }


}
