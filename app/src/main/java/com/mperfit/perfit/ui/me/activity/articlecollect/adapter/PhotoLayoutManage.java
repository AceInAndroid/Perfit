package com.mperfit.perfit.ui.me.activity.articlecollect.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhangbing on 2016/11/28.
 */

public class PhotoLayoutManage extends GridLayoutManager {
    // RecyclerView高度随Item自适应
    public PhotoLayoutManage(Context context, int spanCount) {
        super(context,spanCount);
    }
    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, final int widthSpec, final int heightSpec) {

        int height = 0;
        int childCount = getItemCount();
        for (int i = 0; i < childCount; i++) {
            View child = recycler.getViewForPosition(i);
            measureChild(child, widthSpec, heightSpec);
            if (i % getSpanCount() == 0) {
                int measuredHeight = child.getMeasuredHeight() + getDecoratedBottom(child);
                height += measuredHeight;
            }
        }
        setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), height);



    }
}