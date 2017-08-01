package com.mperfit.perfit.ui.me.activity;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.model.bean.SuggestionBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.utils.RxUtil;
import com.mperfit.perfit.widget.ToastUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by zhangbing on 16/11/3.
 */

public class SuggestionActivity extends SimpleActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.met_content)
    MaterialEditText materialEditText;
    @BindView(R.id.ib_back)
    ImageButton ibBack;

    @Override
    protected int getLayout() {
        return R.layout.activity_suggestion;
    }

    @Override
    protected void initEventAndData() {
        tvTitle.setText(R.string.suggerstion);
        sendSuggestion();
        setBackTouch();

    }

    private void sendSuggestion() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitHelper retrofitHelper = new RetrofitHelper(SuggestionActivity.this);
                retrofitHelper.fetchSuggestion(materialEditText.getText().toString())
                        .compose(RxUtil.<SuggestionBean>rxSchedulerHelper())
                        .subscribe(new Action1<SuggestionBean>() {
                            @Override
                            public void call(SuggestionBean suggestionBean) {
                                ToastUtils.showShrotMsg(mContext,suggestionBean.getMessage());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
            }
        });
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
