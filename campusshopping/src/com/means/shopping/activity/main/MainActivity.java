package com.means.shopping.activity.main;

import net.duohuo.dhroid.activity.ActivityTack;
import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.home.HomePageFragment;
import com.means.shopping.activity.my.MyFragment;
import com.means.shopping.activity.order.OrderFragment;
import com.means.shopping.base.ShopBaseFragmentActivity;
import com.means.shopping.bean.LogoutEB;
import com.means.shopping.bean.User;
import com.means.shopping.manage.UserInfoManage;
import com.means.shopping.manage.UserInfoManage.LoginCallBack;
import com.means.shopping.utils.BaseUiListener;
import com.means.shopping.utils.ShopPerference;
import com.tencent.tauth.Tencent;

import de.greenrobot.event.EventBus;

public class MainActivity extends ShopBaseFragmentActivity {

	private FragmentManager fm;
	private Fragment currentFragment;

	private LinearLayout tabV;
	ShopPerference per;

	static boolean isExit = false;

	Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActivityTack.getInstanse().addActivity(this);
		EventBus.getDefault().register(this);
		per = IocContainer.getShare().get(ShopPerference.class);
		per.load();
		initView();
		initTab();
		setTab(0);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		String type = intent.getStringExtra("type");
		if ("pay".equals(type)) {
			setTab(1);
		}
	}

	private void initView() {
		mHandler = new Handler();
		// TODO Auto-generated method stub
		fm = getSupportFragmentManager();
		tabV = (LinearLayout) findViewById(R.id.tab);
	}

	private void initTab() {
		for (int i = 0; i < tabV.getChildCount(); i++) {
			final int index = i;
			LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
			childV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setTab(index);
				}
			});
		}
	}

	public void setTab(final int index) {
		for (int i = 0; i < tabV.getChildCount(); i++) {
			LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
			RelativeLayout imgV = (RelativeLayout) childV.getChildAt(0);
			final ImageView imgI = (ImageView) imgV.getChildAt(0);
			final TextView textT = (TextView) childV.getChildAt(1);

			if (index == 2 || index == 1) {
				if (!User.getInstance().isLogin()) {
					UserInfoManage.getInstance().checkLogin(MainActivity.this,
							new LoginCallBack() {

								@Override
								public void onisLogin() {

								}

								@Override
								public void onLoginFail() {
									// TODO Auto-generated method stub

								}
							});
					return;
				}
			}
			if (i == index) {
				switch (i) {
				case 0: // 首页
					switchContent(HomePageFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_home_s);
					textT.setTextColor(getResources().getColor(
							R.color.tab_index_bg));
					break;

				case 1: // 订单
					switchContent(OrderFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_order_s);
					textT.setTextColor(getResources().getColor(
							R.color.tab_index_bg));
					break;

				case 2: // 我的
					switchContent(MyFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_my_s);
					textT.setTextColor(getResources().getColor(
							R.color.tab_index_bg));

					break;

				default:
					break;
				}
			} else {
				childV.setBackgroundColor(getResources().getColor(
						R.color.nothing));
				switch (i) {
				case 0:
					imgI.setImageResource(R.drawable.icon_home_n);
					textT.setTextColor(getResources().getColor(
							R.color.text_43_black));
					break;

				case 1:
					imgI.setImageResource(R.drawable.icon_order_n);
					textT.setTextColor(getResources().getColor(
							R.color.text_43_black));
					break;

				case 2:
					imgI.setImageResource(R.drawable.icon_my_n);
					textT.setTextColor(getResources().getColor(
							R.color.text_43_black));
					break;

				default:
					break;
				}
			}
		}
	}

	public void switchContent(Fragment fragment) {
		try {
			FragmentTransaction t = fm.beginTransaction();
			if (currentFragment != null) {
				t.hide(currentFragment);
			}
			if (!fragment.isAdded()) {
				t.add(R.id.main_content, fragment);
			}
			t.show(fragment);
			t.commitAllowingStateLoss();
			currentFragment = fragment;
		} catch (Exception e) {
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		BaseUiListener myListener = new BaseUiListener(self);
		showToast("主页");
		Tencent.onActivityResultData(requestCode, resultCode, data, myListener);
	}

	public void onEventMainThread(LogoutEB out) {

		setTab(0);
	}

	@Override
	public void finish() {
		ActivityTack.getInstanse().removeActivity(this);
		super.finish();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	static public class ExitRunnable implements Runnable {
		@Override
		public void run() {
			isExit = false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isExit) {
				isExit = true;
				IocContainer.getShare().get(IDialog.class)
						.showToastShort(getApplicationContext(), "再按一次退出程序");
				mHandler.postDelayed(new ExitRunnable(), 2000);
			} else {
				// Intent it = new Intent(self, MsgService.class);
				// stopService(it);
				ActivityTack.getInstanse().exit(MainActivity.this);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
