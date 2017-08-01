package com.mperfit.perfit.widget;

/**
 * Created by zhangbing on 2016/11/29.
 * <p>
 * SharePopwindow
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.utils.SharedPreferenceUtil;


public class SharePopwindow extends PopupWindow {

    private Activity mActivity;

    private View view;
    private final LinearLayout popLayout;
    private int mPopHeight;
    private Context mContext;
    private ImageButton ibDelete;
    private LinearLayout llDeleteContainer;


    public SharePopwindow(Context mContext, final int position) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_share, null);

        ImageButton ibWeiXin = (ImageButton) view.findViewById(R.id.ib_weixin);
        ImageButton ibWeiXinquan = (ImageButton) view.findViewById(R.id.weixin_quan);
        ImageButton ibWeibo = (ImageButton) view.findViewById(R.id.ib_weibo);
        ibDelete = (ImageButton) view.findViewById(R.id.iv_delete);
        TextView tvShare = (TextView) view.findViewById(R.id.tv_sharetext);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        llDeleteContainer = (LinearLayout) view.findViewById(R.id.ll_delete_container);
        popLayout = (LinearLayout) view.findViewById(R.id.pop_layout);

        //删除按钮
        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDeleteItemClick(position);
                }
            }
        });
        // 取消按钮
        tvCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        // 设置按钮监听
        ibWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onItemClickListener != null) {
                    onItemClickListener.onWeixinItemClick(position);
                }

            }
        });
        ibWeiXinquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onQuanItemClick(position);
                }

            }
        });
        ibWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onItemClickListener != null) {
                    onItemClickListener.onWeiboItemClick(position);
                }


            }
        });

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mPopHeight = view.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < mPopHeight) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);
        //设置关闭监听
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        // 实例化一个ColorDrawable颜色为透明 必须有这一步
//        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.popwindow_bg));
        ColorDrawable dw = new ColorDrawable(0x00000000);
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出(改成)
        this.setAnimationStyle(R.style.share_anim);

        initType();
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

    }

    public SharePopwindow(Context mContext, View.OnClickListener onClickListener) {


        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_share, null);

        ImageButton ibWeiXin = (ImageButton) view.findViewById(R.id.ib_weixin);
        ImageButton ibWeiXinquan = (ImageButton) view.findViewById(R.id.weixin_quan);
        ImageButton ibWeibo = (ImageButton) view.findViewById(R.id.ib_weibo);
        TextView tvShare = (TextView) view.findViewById(R.id.tv_sharetext);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        ibDelete = (ImageButton) view.findViewById(R.id.iv_delete);
        popLayout = (LinearLayout) view.findViewById(R.id.pop_layout);
        llDeleteContainer = (LinearLayout) view.findViewById(R.id.ll_delete_container);
        // 取消按钮
        tvCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        // 设置按钮监听
        ibWeiXin.setOnClickListener(onClickListener);
        ibWeiXinquan.setOnClickListener(onClickListener);
        ibWeibo.setOnClickListener(onClickListener);
        ibDelete.setOnClickListener(onClickListener);
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mPopHeight = view.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < mPopHeight) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);
        //设置关闭监听
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        // 实例化一个ColorDrawable颜色为透明 必须有这一步
//        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.popwindow_bg));
        ColorDrawable dw = new ColorDrawable(0x00000000);
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出(改成)
        this.setAnimationStyle(R.style.share_anim);

        initType();
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);


    }


    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        addMaskView(parent.getWindowToken());
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        addMaskView(anchor.getWindowToken());
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void dismiss() {
        removeMaskView();
        super.dismiss();
    }


    /**
     * 显示在界面的底部
     */
    public void show(Activity activity) {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    private SharePopwindow.OnClickListener onItemClickListener;


    public void setOnClickListener(SharePopwindow.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnClickListener {
        void onWeixinItemClick(int position);

        void onQuanItemClick(int position);

        void onWeiboItemClick(int position);

        void onDeleteItemClick(int position);
    }


    @TargetApi(23)
    private void initType() {
        // 解决华为手机在home建进入后台后，在进入应用，蒙层出现在popupWindow上层的bug。
        // android4.0及以上版本都有这个hide方法，根据jvm原理，可以直接调用，选择android6.0版本进行编译即可。
        setWindowLayoutType(WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL);
    }

    private View maskView;
    private WindowManager wm;

    private void addMaskView(IBinder token) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.MATCH_PARENT;
        p.format = PixelFormat.TRANSLUCENT;
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.token = token;
        p.windowAnimations = android.R.style.Animation_Toast;
        maskView = new View(mContext);
        maskView.setBackgroundColor(0x7f000000);
        maskView.setFitsSystemWindows(false);
        // 华为手机在home建进入后台后，在进入应用，蒙层出现在popupWindow上层，导致界面卡死，
        // 这里新增加按bug返回。
        // initType方法已经解决该问题，但是还是留着这个按back返回功能，防止其他手机出现华为手机类似问题。
        maskView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    removeMaskView();
                    return true;
                }
                return false;
            }
        });
        wm.addView(maskView, p);
    }


    private void removeMaskView() {
        if (maskView != null) {
            wm.removeViewImmediate(maskView);
            maskView = null;
        }
    }

    /**
     * 判断是不是自己 如果是 隐藏掉删除按钮 隐藏掉按钮所在行
     *
     * @param userId
     */
    public void isMe(long userId) {
        if (ibDelete != null) {
            if (!SharedPreferenceUtil.isMe(userId)) {

                if (llDeleteContainer != null) {
                    llDeleteContainer.setVisibility(View.GONE);
                }

            } else {
                if (llDeleteContainer != null) {
                    llDeleteContainer.setVisibility(View.VISIBLE);
                }

            }
        }

    }


}