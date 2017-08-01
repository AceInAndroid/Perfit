package com.mperfit.perfit.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;

/**
 * loadingdialog
 * Created by zhangbing on 2016/12/5.
 */

public class LoadingDialog extends Dialog {

    Dialog mLoadingDialog;
    private LoadingView mLoadingView;

    boolean cancelable;

    public LoadingDialog(Context context) {
        super(context);
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_loading, null);
        // 页面中的LoadingView
        mLoadingView = (LoadingView) view.findViewById(R.id.image);
        // 页面中显示文本
        TextView loadingText = (TextView) view.findViewById(R.id.tv_loadingtext);
//        // 显示文本
//        loadingText.setText(msg);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        if (mLoadingDialog !=null)
            mLoadingDialog.setCancelable(cancelable);

//        mLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context,themeResId);

// 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                themeResId, null);

        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

    }

    public void setCancelable(boolean cancelable){
        if (mLoadingDialog !=null)
            mLoadingDialog.setCancelable(cancelable);
    }

    public void show(){
        if (mLoadingDialog!=null)
        mLoadingDialog.show();
        if (mLoadingView!=null)
        mLoadingView.startAnimator();
    }

    public void close(){
        if (mLoadingView!=null){
            mLoadingView.stopAnimator();
        }
        if (mLoadingDialog!=null) {
            mLoadingDialog.dismiss();
            mLoadingDialog=null;
        }
    }
}
