package com.mperfit.perfit.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 垂直自适应布局
 * Created by zhangbing on 2017/1/10.
 */

public class AutofitRecyclerView extends RecyclerView {
    private GridLayoutManager manager;
    // 默认为-1
    private int mColumnWidth = -1;

    public AutofitRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        manager = new GridLayoutManager(context, 1, VERTICAL, false);
        setLayoutManager(manager);
    }

    public void setColumnWidth(int columnWidth) {
        mColumnWidth = columnWidth;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (mColumnWidth > 0) {
            int spanCount = Math.max(1, getMeasuredWidth() / mColumnWidth);
            manager.setSpanCount(spanCount);
        }
    }
}


