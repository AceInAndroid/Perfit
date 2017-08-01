package com.mperfit.perfit.ui.article.activity;

import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.ui.article.adapter.ArticleAdapter;
import com.mperfit.perfit.ui.article.fragment.FitnessFragment;
import com.mperfit.perfit.ui.article.fragment.SportsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文章首页
 * Created by zhangbing on 16/10/18.
 */

public class ArticleActivity extends SimpleActivity {
    @BindView(R.id.vp_article_viewpager)
    ViewPager mContentViewPager;
    @BindView(R.id.tl_article_tab)
    TabLayout mTabLayout;
    @BindView(R.id.article_toolbar)
    Toolbar mToolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    String[] tabTitle = new String[]{"玩","美"};
    List<Fragment> fragments = new ArrayList<>();
    ArticleAdapter articleAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_article;
    }

    @Override
    protected void initEventAndData() {
        tvTitle.setText("文章");
        FitnessFragment fitnessFragment = new FitnessFragment();
        SportsFragment sportsFragment = new SportsFragment();
        fragments.add(fitnessFragment);
        fragments.add(sportsFragment);
        articleAdapter=new ArticleAdapter(getSupportFragmentManager(),fragments);
        mContentViewPager.setAdapter(articleAdapter);
        //TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
        mTabLayout.setupWithViewPager(mContentViewPager);
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);

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
                overridePendingTransition(0,R.anim.slide_right_out);
            }
        });
    }


}
