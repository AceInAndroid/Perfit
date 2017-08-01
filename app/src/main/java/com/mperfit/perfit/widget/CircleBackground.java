package com.mperfit.perfit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mperfit.perfit.R;
import com.mperfit.perfit.utils.SystemUtil;

/**
 * 课程详情的的页面指示器
 * Created by zhangbing on 2016/11/24.
 */

public class CircleBackground extends View {

    private Paint mPaintBackCircle;
    private Paint mPaintText;
    private float mStrokeWith =50;
    private float mHalfStrokeWith = mStrokeWith/2;
    private float mX = 200 + mHalfStrokeWith;
    private float mY = 200 + mHalfStrokeWith;
    private float mRadius = 50;
    private int mWith;
    private int mHeight;
    /**
     * 背景颜色
     */
    private int backgroundColor;
    private int textColor;
    private float textsize;
    /**
     * 第一个
     */
    private String firstText ="1";
    private String secondText="1";

    private Context mContext;
    public CircleBackground(Context context) {
        this(context,null);
        init();
    }

    public CircleBackground(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.mContext = context;
        init();
    }

    public CircleBackground(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.CircleBackground);
        backgroundColor = typedArray.getColor(R.styleable.CircleBackground_roundColor, context.getResources().getColor(R.color.half_backgroundclor));
        textColor = typedArray.getColor(R.styleable.CircleBackground_textColor, Color.WHITE);
        textsize = typedArray.getDimension(R.styleable.CircleBackground_textSize, 40);
        textsize = typedArray.getDimension(R.styleable.CircleBackground_textSize, 40);
        firstText = typedArray.getString(R.styleable.CircleBackground_frirst_text);
        secondText = typedArray.getString(R.styleable.CircleBackground_second_text);
        typedArray.recycle();
    }

    private void init(){

        mPaintBackCircle = new Paint();
        mPaintBackCircle.setColor(backgroundColor);
        mPaintBackCircle.setAntiAlias(true);
        mPaintBackCircle.setStyle(Paint.Style.FILL);
        mPaintBackCircle.setStrokeWidth(mStrokeWith);


        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(34);
        mPaintText.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWith = w;
        mHeight = h;
    }

    private int measure(int origin) {
        int result = 100;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获得文字长度
        float textWidth = mPaintText.measureText("1/5");
        canvas.drawCircle(mWith/2,mHeight/2,mWith/2,mPaintBackCircle);
        canvas.drawText(firstText+"/"+secondText,mWith/2 -textWidth/8-getPaddingLeft(), (SystemUtil.px2dp(mContext,mHeight/2+114 )) ,mPaintText);
    }


    public void setText(String firstText,String secondText){
        this.firstText = firstText;
        this.secondText = secondText;
        invalidate();
    }
}
