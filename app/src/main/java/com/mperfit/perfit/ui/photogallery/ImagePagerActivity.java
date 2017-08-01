package com.mperfit.perfit.ui.photogallery;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.ui.photogallery.adapter.GalleryViewPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangbing on 2016/12/9.
 */

public class ImagePagerActivity extends SimpleActivity {

    @BindView(R.id.vp_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @Override
    protected int getLayout() {
        return R.layout.activity_image_page;
    }

    @Override
    protected void initEventAndData() {


        Intent intent = mContext.getIntent();
        final List<String> list = (List<String>) intent.getSerializableExtra("imagelist");
        //获取位置
        int position = intent.getIntExtra("position", 0);

        if (list !=null&& list.size()>0){
            tvTitle.setText(1 +"/" + list.size());
        } else {
            tvTitle.setText(0+"/"+0);
        }
        List<View> mList = new ArrayList<>();
        mViewPager.setAdapter(new GalleryViewPagerAdapter(mContext,mList,list));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText((position+1) +"/" + list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(position);
        setBackTouch();
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
        if(View.class.isInstance(ibBack.getParent())){
            // 设置视图扩大后的触摸区域
            ((View)ibBack.getParent()).setTouchDelegate(expandedArea);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,R.anim.right_out);
            }
        });
    }



}
