package com.mperfit.perfit.ui.splash;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.mperfit.perfit.R;
import com.mperfit.perfit.ui.main.activity.MainActivity;
import com.mperfit.perfit.utils.SharePreferenceUtils;
import com.mperfit.perfit.utils.SystemUtil;

/**
 * 新手引导页面
 * 
 * @author Zhang bing
 * 
 */
public class GuideActivity extends Activity implements OnClickListener {

	protected static final String TAG = GuideActivity.class.getSimpleName();
	private ViewPager mViewPager;
	private ArrayList<ImageView> mImageList;// 引导页的ImageView集合
	private LinearLayout llPointGroup;// 点的集合
	private View viewRedPoint;// 红点
	private int mPointWidth; // 两点间距
	private Button btnStart;// 开始体验

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题,
		setStateBar();								// 必须在setContentView之前调用
		setContentView(R.layout.activity_guide);
		initViews();
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





	/**
	 * 初始化界面
	 */
	private void initViews() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
		viewRedPoint = findViewById(R.id.view_red_point);
		btnStart = (Button) findViewById(R.id.btn_start);
		btnStart.setOnClickListener(this);

		initData();
		mViewPager.setAdapter(new GuideAdapter());

		// measure -> layout -> draw
		// 获得视图树观察者, 观察当整个布局的layout时的事件
		viewRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					// 完成布局后会回调改方法, 改方法可能会被回调多次
					@Override
					public void onGlobalLayout() {
						// 此方法只需要执行一次就可以: 把当前的监听事件从视图树中移除掉, 以后就不会在回调此事件了.
						viewRedPoint.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);

						mPointWidth = llPointGroup.getChildAt(1).getLeft()
								- llPointGroup.getChildAt(0).getLeft();

					}
				});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			// 页面选中
			@Override
			public void onPageSelected(int position) {
				// Log.d(TAG, "onPageSelected:" + position);
				if (position == mImageList.size() - 1) {
					btnStart.setVisibility(View.VISIBLE);
				} else {
					btnStart.setVisibility(View.GONE);
				}
			}

			/**
			 * 页面滑动监听
			 * 
			 * @params position 当前选中的位置
			 * @params positionOffset 偏移百分比
			 * @params positionOffsetPixels 页面偏移长度
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				int leftMargin = (int) (mPointWidth * (positionOffset + position));
				// Log.d(TAG, "当前位置:" + position + ";偏移比例:" + positionOffset
				// + ";点偏移:" + leftMargin);

				RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) viewRedPoint
						.getLayoutParams();
				lp.leftMargin = leftMargin;
				viewRedPoint.setLayoutParams(lp);
			}

			// 状态改变
			@Override
			public void onPageScrollStateChanged(int state) {
				// Log.d(TAG, "onPageScrollStateChanged:" + state);
			}
		});
	}

	// 初始化ViewPager的数据
	private void initData() {
		int[] imageResIDs = new int[]{R.drawable.yindao6_01, R.drawable.yindao6_02,
				R.drawable.yindao6_03};

		mImageList = new ArrayList<ImageView>();
		for (int i = 0; i < imageResIDs.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(imageResIDs[i]);// 注意设置背景, 才可以填充屏幕
			mImageList.add(image);

			View point = new View(this);
			point.setBackgroundResource(R.drawable.shape_guide_point_default);

			LayoutParams params = new LayoutParams(
					SystemUtil.dp2px(this, 10), SystemUtil.dp2px(this, 10));

			if (i != 0) {
				params.leftMargin = SystemUtil.dp2px(this, 10);
			}

			point.setLayoutParams(params);
			llPointGroup.addView(point);
		}
	}

	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mImageList.get(position));
			return mImageList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((ImageView) object);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			// 开始体验
			SharePreferenceUtils.putBoolean(this,
					SplashActivity.PREF_IS_USER_GUIDE_SHOWED, true);// 记录已经展现过了新手引导页
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);

			finish();

			break;

		default:
			break;
		}
	}
}
