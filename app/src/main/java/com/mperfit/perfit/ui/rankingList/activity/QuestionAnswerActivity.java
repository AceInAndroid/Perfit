package com.mperfit.perfit.ui.rankingList.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.SimpleActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * Created by zhangbing on 2017/3/24.
 */

public class QuestionAnswerActivity extends SimpleActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_tb_title)
    TextView tvTbTitle;

    @BindView(R.id.wb_webview)
    WebView wbWebview;

    @Override
    protected int getLayout() {
        return R.layout.activity_question_ansewer;
    }

    @Override
    protected void initEventAndData() {

        //屏蔽掉长按
        wbWebview.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        WebSettings webSettings = wbWebview.getSettings();
        webSettings.setSupportZoom(false);

        wbWebview.setVerticalScrollBarEnabled(false); //垂直不显示
        wbWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        Intent intent = getIntent();
        int type = intent.getIntExtra(Constants.TYPE, Constants.QATYPE_STAR);
        switch (type){
            case Constants.QATYPE_STAR:
                wbWebview.loadUrl("http://wap.mperfit.com/explain/huoyuedu");
                tvTbTitle.setText("红人榜说明");
                break;
            case Constants.QATYPE_SCORE:
                wbWebview.loadUrl("http://wap.mperfit.com/explain/jifeng");
                tvTbTitle.setText("积分榜说明");
                break;
            case Constants.QATYPE_GAME:
                tvTbTitle.setText("赛事说明");
                wbWebview.loadUrl("http://wap.mperfit.com/explain/saishi");
                break;
        }

        setBackTouch(ibBack);

    }


}
