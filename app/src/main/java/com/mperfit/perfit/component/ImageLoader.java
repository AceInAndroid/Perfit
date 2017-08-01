package com.mperfit.perfit.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.ui.community.adapter.SquareFragmentAdapter;
import com.mperfit.perfit.utils.BitmapFormatTools;
import com.mperfit.perfit.utils.SharedPreferenceUtil;
import com.mperfit.perfit.widget.LoadingView;
import com.orhanobut.logger.Logger;

/**
 * 图片加载类
 * Created by zhangbing on 2016/10/13.
 */
public class ImageLoader {


    public static void loadCommuniteList(Context context, final String url, final ImageView imageView, final LoadingView progressBar, final int weight, final int imgHeight) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!SharedPreferenceUtil.getNoImageState()) {
            progressBar.startAnimator();
            Glide.with(context).load(url)
                    .dontAnimate()
                    .placeholder(R.drawable.place_holder_ccc)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (imageView == null) {
                                return false;
                            }

                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            int vw = App.SCREEN_WIDTH;
                            float scale = (float) vw / (float) weight;
                            int vh = Math.round(imgHeight * scale);

                            //横屏图片(长大于宽的 不变)
                            if (weight > imgHeight){
//                                params.height = vh;
                            }  else {
                                if (App.SCREEN_HEIGHT != 0 && vh > App.SCREEN_HEIGHT * 0.70) {
//                                    params.height = (int) (App.SCREEN_HEIGHT * 0.70);
                                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                } else {
//                                    params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                                }
                            }
//                            params.width = vw;
//                            imageView.setLayoutParams(params);
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }





    public static void loadPostDetail(Context context, final String url, final ImageView imageView, final LoadingView progressBar, final int weight, final int imgHeight) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!SharedPreferenceUtil.getNoImageState()) {
            progressBar.startAnimator();
            Glide.with(context).load(url)
                    .dontAnimate()
                    .placeholder(R.drawable.place_holder_ccc)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (imageView == null) {
                                return false;
                            }

//                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                            int vw = App.SCREEN_WIDTH;
//                            float scale = (float) vw / (float) weight;
//                            int vh = Math.round(imgHeight * scale);
//
//                            //横屏图片(长大于宽的 不变)
//                            if (weight > imgHeight){
//                                params.height = vh;
//
//                                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                                }
//
//
//                            }  else {
//                                if (App.SCREEN_HEIGHT != 0 && vh > App.SCREEN_HEIGHT * 0.70) {
//                                    params.height = (int) (App.SCREEN_HEIGHT * 0.70);
//                                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                                } else {
//                                    params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
//                                    if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                                    }
//
//
//                                }
//                            }
//                            params.width = vw;
//                            imageView.setLayoutParams(params);
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }





    public static void load(Context context, String url, final ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!SharedPreferenceUtil.getNoImageState()) {
            Glide.with(context).load(url)
                    .dontAnimate()
//                    .placeholder(R.drawable.jiazai)     //设置占位图片
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (imageView == null) {
                                return false;
                            }

                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                            float scale = (float) vw / (float) resource.getIntrinsicWidth();
                            int vh = Math.round(resource.getIntrinsicHeight() * scale);
                            params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();

//                            if (vh > 936 * scale ){
//                                params.height = (int) (936 * scale);
//                                if (imageView.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
//                                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                                }
//                            } else {
//                                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
//                                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                                }
//                            }
                            imageView.setLayoutParams(params);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }


    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
//                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);

                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);

                        Logger.e("图片大小是:" + BitmapFormatTools.getInstance().Drawable2Bytes(resource));
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }


    public static void load(Activity activity, String url, final ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()) {
                Glide.with(activity)
                        .load(url)
                        .dontAnimate()
                        .placeholder(R.drawable.place_holder_ccc)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                if (imageView == null) {
                                    return false;
                                }
    //                            if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
    //                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    //                            }
                                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                                imageView.setLayoutParams(params);


                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);
            }
        }
    }

    public static void loadAll(Context context, String url, ImageView iv) {    //不缓存，全部从网络加载
        Glide.with(context).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void loadAll(Activity activity, String url, ImageView iv) {    //不缓存，全部从网络加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()) {
                Glide.with(activity).load(url)
                        .crossFade()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(iv);
            }
        }
    }

    public static void load(Activity mContext, String img_url, ViewTarget viewTarget) {
        Glide.with(mContext).load(img_url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewTarget);
    }


//    public static void loadHead(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
//            Glide.with(context).load(url).transform(new GlideRoundTransform(context)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
//    }


    //加载圆形头像
    public static void loadHead(final Context context, String url, final ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图

        Glide.with(context).load(url).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    /**
     * 记载广告条
     *
     * @param context
     * @param url
     * @param imageView
     */


    public static void loadBannaerAds(Context context, String url, final ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!SharedPreferenceUtil.getNoImageState()) {
            Glide.with(context).load(url)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }


    /**
     * 记载广告条
     *
     * @param context
     * @param url
     * @param imageView
     */


    public static void loadnoAnim(Context context, String url, final ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!SharedPreferenceUtil.getNoImageState()) {
            Glide.with(context).load(url)
                    .placeholder(R.drawable.place_holder_ccc)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }


    /**
     * 加载个人中心九宫格图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadPersonalPost(Context context, String url, final ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!SharedPreferenceUtil.getNoImageState()) {
            Glide.with(context).load(url)
                    .crossFade()
                    .placeholder(R.drawable.holder_small)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }

}
