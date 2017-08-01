package com.mperfit.perfit.ui.comment.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mperfit.perfit.R;
import com.mperfit.perfit.component.ImageLoader;
import com.mperfit.perfit.widget.ImageCompress;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbing on 2016/12/5.
 */

public class CommentPhotoGalleryAdapter extends RecyclerView.Adapter<CommentPhotoGalleryAdapter.CommentGalleryViewHolder> {


    private Context mContext;
    private final LayoutInflater inflater;
    private ArrayList<String> bitmapResult;

    public enum ITEM_TYPE {
        ITEM_ADD,       //添加按钮
        ITEM_IMAGE,        //图片
    }


    public CommentPhotoGalleryAdapter(Context mContext, ArrayList<String> bitmapResult) {
        bitmapResult = new ArrayList<>();
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.bitmapResult = bitmapResult;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public CommentGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_comment_photogallery, parent, false);
        return new CommentGalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentGalleryViewHolder holder, final int position) {
        ImageLoader.load(mContext,bitmapResult.get(position),holder.ivImage);
        holder.ibDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDeleteClick(position);
                deleteData(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (bitmapResult==null){
            return 0;
        }
        if (bitmapResult.size() >= 3){
            onItemClickListener.onHideAddBtn();
            return 3;
        } else {
            onItemClickListener.onShowAddBtn();
            return bitmapResult.size();
        }
    }

    public void addData(ArrayList<String> result) {
        for (int i = 0; i<result.size();i++){
            this.bitmapResult.add(result.get(i));
        }
        notifyDataSetChanged();
    }


    public void deleteData(int position) {
        if (bitmapResult !=null && bitmapResult.size() !=0 ){
            bitmapResult.remove(position);
        }
        notifyDataSetChanged();

    }



    /**
     * 设置文章列表条目点击
     *
     * */
    CommentPhotoGalleryAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CommentPhotoGalleryAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onHideAddBtn();
        void onShowAddBtn();
        void onDeleteClick(int position);
        void onItemClick(int position, View view);

    }






    class CommentGalleryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.ib_delect)
        ImageButton ibDelect;

        public CommentGalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
