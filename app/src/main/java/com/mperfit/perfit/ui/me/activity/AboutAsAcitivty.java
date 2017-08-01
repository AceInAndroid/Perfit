package com.mperfit.perfit.ui.me.activity;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangbing on 16/11/3.
 */

public class AboutAsAcitivty extends SimpleActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @Override
    protected int getLayout() {
        return R.layout.activity_aboutas;
    }

    @Override
    protected void initEventAndData() {
        tvTitle.setText(R.string.adboutas);
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
