package com.mperfit.perfit.ui.imagechooser.imagechooser.preview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.model.bean.PhotoBackBean;
import com.mperfit.perfit.ui.comment.activity.CommentActivity;
import com.mperfit.perfit.ui.imagechooser.imagechooser.ChooserSetting;
import com.mperfit.perfit.ui.imagechooser.imagechooser.EntryActivity;
import com.mperfit.perfit.ui.imagechooser.imagechooser.IcFinal;
import com.mperfit.perfit.ui.imagechooser.imagechooser.album.AlbumEntry;
import com.mperfit.perfit.ui.imagechooser.imagechooser.album.ImageInfo;
import com.mperfit.perfit.ui.imagechooser.imagechooser.bean.PhotoImageInfo;
import com.mperfit.perfit.ui.photogallery.adapter.GalleryViewPagerAdapter;
import com.mperfit.perfit.utils.RxBusUtils;
import com.mperfit.perfit.utils.ToastUtil;
import com.mperfit.perfit.widget.ToastUtils;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预览
 * Created by zhangbing on 2016/12/9.
 */

public class AlbumImagePagerActivity extends SimpleActivity {


    @BindView(R.id.tv_back)
    TextView ibBack;
    @BindView(R.id.tv_right_text)
    TextView tvTitle;
    @BindView(R.id.iv_indicator)
    ImageView ivIndicator;
    @BindView(R.id.ib_next)
    ImageButton ibNext;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.vp_viewpager)
    ViewPager mViewPager;
    //记录选中的条目
    private ArrayList<String> resultlist;
    //所有条目
    private ArrayList<ImageInfo> mImageList;
    //已经选择的
    private ArrayList<ImageInfo> selectImgs;

    private AlbumPreviewAdapter previewAdapter;
    private List<View> mList;
    private int clickPosition;
    private int type;

    @Override
    protected int getLayout() {
        return R.layout.activity_image_page_activity;
    }

    @Override
    protected void initEventAndData() {
        resultlist = new ArrayList<>();
        ivIndicator.setVisibility(View.GONE);
        ibBack.setText("返回");
        Intent intent = mContext.getIntent();
        type = intent.getIntExtra(Constants.TYPE_PREVIEW, 0);
        if (type == 1) {
            //预览模式

            selectImgs = intent.getParcelableArrayListExtra("selectImgs");

            if (selectImgs != null && selectImgs.size() > 0) {
                tvTitle.setText(1 + "/" + selectImgs.size());
            } else {
                tvTitle.setText(0 + "/" + 0);
            }


        } else {
            mImageList = intent.getParcelableArrayListExtra("list");
            //移除第一个（相机条目）
            mImageList.remove(0);
            //已选择条目
            selectImgs = intent.getParcelableArrayListExtra("selectImgs");
            //点击位置
            clickPosition = intent.getIntExtra(Constants.PREVIEW_POSITION, 0);
            //设置标题栏
            if (mImageList != null && mImageList.size() > 0) {
                tvTitle.setText(clickPosition + "/" + mImageList.size());
            } else {
                tvTitle.setText(0 + "/" + 0);
            }
        }


        //设置小圆点
        if (selectImgs != null && selectImgs.size() != 0) {
            count = selectImgs.size();
            if (count <= 0) {
                tvCount.setVisibility(View.GONE);
            } else {
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(count + "");
            }
        }


        mList = new ArrayList<>();

        if (type == 1) {
            for (ImageInfo info : selectImgs) {
                info.setState(1);
            }

            previewAdapter = new AlbumPreviewAdapter(mContext, mList, selectImgs);
            mViewPager.setAdapter(previewAdapter);
        } else {
            //遍历 改变已选择的状态
            for (int i = 0; i < selectImgs.size(); i++) {
                String path = selectImgs.get(i).getPath();

                for (int j = 0; j < mImageList.size(); j++) {
                    String pathList = mImageList.get(j).getPath();
                    if (path.equals(pathList)) {
                        mImageList.get(j).setState(1);
                    }
                }
            }
            previewAdapter = new AlbumPreviewAdapter(mContext, mList, mImageList);
            mViewPager.setAdapter(previewAdapter);
            mViewPager.setCurrentItem(clickPosition - 1);
        }


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (type == 1) {
                    if (selectImgs != null)
                        tvTitle.setText((position + 1) + "/" + selectImgs.size());
                } else {
                    if (mImageList != null)
                        tvTitle.setText((position + 1) + "/" + mImageList.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        previewAdapter.setOnDeleteListener(new AlbumPreviewAdapter.OnDeleteListener() {
            @Override
            public void onCheckClickListener(int pos) {

                if (type != 1) {

                    if (mImageList.get(pos).getState() == 0) {
                        if (count == ChooserSetting.MAX_NUM) {
                            ToastUtils.showShrotMsg(mContext, "亲~已经选够啦");
                            return;
                        }
                        mImageList.get(pos).setState(1);
                        count++;

                    } else {
                        mImageList.get(pos).setState(0);
                        count--;
                    }

                    mViewPager.setAdapter(previewAdapter);
                    mViewPager.setCurrentItem(pos);
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvCount, "scaleX", 1f, 1.3f, 1f).setDuration(200);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvCount, "scaleY", 1f, 1.3f, 1f).setDuration(200);
                    animatorSet.play(scaleX).with(scaleY);
                    animatorSet.start();
                    if (mImageList != null && mImageList.size() > 0) {
                        if (count <= 0) {
                            tvCount.setVisibility(View.GONE);
                        } else {
                            tvCount.setVisibility(View.VISIBLE);
                            tvCount.setText(count + "");
                            tvTitle.setText(pos + 1 + "/" + mImageList.size());
                        }
                    }
                } else {

                    if (selectImgs.get(pos).getState() == 0) {

                        selectImgs.get(pos).setState(1);
                        count++;

                    } else {
                        selectImgs.get(pos).setState(0);
                        count--;
                    }

                    mViewPager.setAdapter(previewAdapter);
                    mViewPager.setCurrentItem(pos);
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvCount, "scaleX", 1f, 1.3f, 1f).setDuration(200);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvCount, "scaleY", 1f, 1.3f, 1f).setDuration(200);
                    animatorSet.play(scaleX).with(scaleY);
                    animatorSet.start();
                    if (selectImgs != null && selectImgs.size() > 0) {
                        if (count <= 0) {
                            tvCount.setVisibility(View.GONE);
                        } else {
                            tvCount.setVisibility(View.VISIBLE);
                            tvCount.setText(count + "");
                            tvTitle.setText(pos + 1 + "/" + selectImgs.size());
                        }
                    }

                }


            }
        });
        setBackTouch();
    }

    private int count;

    @OnClick(R.id.ib_next)
    void chooseFinish() {
        haveFinish();
    }

    private void haveFinish() {

        if (type != 1) {

            if (mImageList != null) {
                for (int i = 0; i < mImageList.size(); i++) {
                    if (mImageList.get(i).getState() == 1) {
                        resultlist.add(mImageList.get(i).getPath());
                    }
                }
            }
            if (resultlist != null && resultlist.size() > 0) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra(IcFinal.RESULT_DATA_IMG, resultlist);
                this.setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                finish();
            }
        } else {

            if (selectImgs != null) {
                for (int i = 0; i < selectImgs.size(); i++) {
                    if (selectImgs.get(i).getState() == 1) {
                        resultlist.add(selectImgs.get(i).getPath());
                    }
                }
            }
            if (resultlist != null && resultlist.size() > 0) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra(IcFinal.RESULT_DATA_IMG, resultlist);
                this.setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                finish();
            }


        }

    }


    private void setBackTouch() {
        Rect delegateArea = new Rect();
        // Hit rectangle in parent's coordinates
        ibBack.getHitRect(delegateArea);

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
        TouchDelegate expandedArea = new TouchDelegate(delegateArea, ibBack);
        if (View.class.isInstance(ibBack.getParent())) {
            // 设置视图扩大后的触摸区域
            ((View) ibBack.getParent()).setTouchDelegate(expandedArea);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (type != 1) {

                    if (mImageList != null) {
                        for (int i = 0; i < mImageList.size(); i++) {
                            if (mImageList.get(i).getState() == 1) {
                                resultlist.add(mImageList.get(i).getPath());
                            }
                        }
                    }
                    if (resultlist != null && resultlist.size() > 0) {
                        //发消息给列表刷新
                        RxBusUtils.getDefault().post(new PhotoBackBean(resultlist));
                        finish();
                        overridePendingTransition(0, R.anim.right_out);
                    } else {
                        finish();
                        overridePendingTransition(0, R.anim.right_out);
                    }


                } else {

                    if (selectImgs != null) {
                        for (int i = 0; i < selectImgs.size(); i++) {
                            if (selectImgs.get(i).getState() == 1) {
                                resultlist.add(selectImgs.get(i).getPath());
                            }
                        }
                    }
                    if (resultlist != null) {
                        RxBusUtils.getDefault().post(new PhotoBackBean(resultlist));
                        finish();
                        overridePendingTransition(0, R.anim.right_out);
                    } else {
                        finish();
                        overridePendingTransition(0, R.anim.right_out);

                    }


                }


            }
        });

    }


}
