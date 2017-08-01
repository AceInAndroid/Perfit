package com.mperfit.perfit.ui.imagechooser.imagechooser.crop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mperfit.perfit.R;
import com.mperfit.perfit.ui.imagechooser.imagechooser.IcFinal;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 */
public class CropActivity extends FragmentActivity {


    @BindView(R.id.mTitle)
    Toolbar toolbar;


    private CropFragment cropFragment;
    private int shape;
    private int width;
    private int height;
    private String drawableName;
    private int drawableParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_chooser_activity_crop);
        ButterKnife.bind(this);
        setTitle();
        initByIntent();
        setStateBar();
    }


    private void setStateBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    public void initByIntent() {
        Intent intent = getIntent();
        String data = intent.getStringExtra(IcFinal.INTENT_CROP_DATA);
        shape = intent.getIntExtra(IcFinal.INTENT_CROP_SHAPE, 0);
        if (shape == 0) {
            drawableName = intent.getStringExtra(IcFinal.INTENT_CROP_COVER);
            drawableParam = intent.getIntExtra(IcFinal.INTENT_CROP_PARAM, 0);
            cropFragment = CropFragment.newFragment(data, drawableName, drawableParam);
        } else if (shape == CropPath.SHAPE_CIRCLE || shape == CropPath.SHAPE_RECT) {
            width = intent.getIntExtra(IcFinal.INTENT_CROP_WIDTH, 400);
            height = intent.getIntExtra(IcFinal.INTENT_CROP_HEIGHT, 400);
            cropFragment = CropFragment.newFragment(data, shape, width, height);
        }
        cropFragment.setOnReadyRunnable(new Runnable() {
            @Override
            public void run() {
//                toolbar.getMenu().getItem(0).setEnabled(true);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mContainer, cropFragment)
                .commit();
    }

    @OnClick(R.id.tv_back)
    void back() {
        finish();
        overridePendingTransition(0, R.anim.right_out);
    }

    @OnClick(R.id.tv_confirm)
    void confirm() {
        String file = getFilesDir().getAbsolutePath() + "/temp.jpg";
        try {
            cropFragment.crop(file);
            Intent intent = new Intent();
            intent.putExtra(IcFinal.RESULT_DATA_IMG, file);
            setResult(RESULT_OK, intent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTitle() {
        toolbar.setContentInsetStartWithNavigation(0);
//        toolbar.inflateMenu(R.menu.menu_crop);
//        toolbar.getMenu().getItem(0).setEnabled(false);

//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.mSure) {
//                    String file = getFilesDir().getAbsolutePath() + "/temp.jpg";
//                    try {
//                        cropFragment.crop(file);
//                        Intent intent = new Intent();
//                        intent.putExtra(IcFinal.RESULT_DATA_IMG, file);
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return false;
//            }
//        });
    }
}
