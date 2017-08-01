package com.mperfit.perfit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.orhanobut.logger.Logger;

/**
 * 购买数量，带减少增加按钮 后期添加什么属性再说吧
 * Created by zhangbing on 2016/11/16.
 */
public class AmountView extends LinearLayout implements  TextWatcher {

    private static final String TAG = "AmountView";
    private int amount = 1; //购买数量
    private int goods_storage = 100; //商品库存

    private OnAmountChangeListener mListener;

    private TextView etAmount;
    private FrameLayout btnDecrease;
    private FrameLayout btnIncrease;
    private ImageButton btnDecrease2;
    private ImageButton btnIncrease2;


    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        etAmount = (TextView) view.findViewById(R.id.etAmount);
        btnDecrease = (FrameLayout) view.findViewById(R.id.fl_Decrease);
        btnIncrease = (FrameLayout) view.findViewById(R.id.fl_Increase);

        etAmount.addTextChangedListener(this);
        btnDecrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (amount > 1) {
                    amount--;
                    etAmount.setText(amount + "");

                }

            }
        });

        btnIncrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount < goods_storage) {
                    amount++;
                    etAmount.setText(amount + "");
                }
            }
        });


        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, 10);
        int btnHight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnHight, 10);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        int textSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 12);
        obtainStyledAttributes.recycle();
        LayoutParams btnParams = new LayoutParams(btnWidth, btnHight);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (textSize != 12) {
            etAmount.setTextSize(textSize);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int mCount = getChildCount();
        int width = 0;
        int height = 0;
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        if (mCount == 0){
            return;
        }
        for (int i = 0 ; i < mCount;i++){
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            width +=cWidth+cParams.leftMargin+cParams.rightMargin;

        }

        height=Math.max(sizeHeight,cHeight);

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);

    }




    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void setText(String count) {
        etAmount.setText(count);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty())
            return;
        amount = Integer.valueOf(s.toString());
        if (amount > goods_storage) {
            etAmount.setText(goods_storage + "");
            return;
        }
        if (amount >1){
            Drawable dw = getResources().getDrawable(R.drawable.submit_order_min_border);
            btnDecrease.setBackgroundDrawable(dw);
        } else {
            Drawable dw = getResources().getDrawable(R.drawable.submit_order_border);
            btnDecrease.setBackgroundDrawable(dw);
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }


}