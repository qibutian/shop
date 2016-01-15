package com.means.shopping.activity.main;

import net.duohuo.dhroid.ioc.IocContainer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.utils.ShopPerference;

/**
 * 欢迎页
 * @author Administrator
 *
 */
public class SplashActivity extends ShopBaseActivity {
	ShopPerference per;

	private final Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		per = IocContainer.getShare().get(ShopPerference.class);
		per.load();
		if (per.isFirst == 0) {
			first();
		} else {
//
//			if (!TextUtils.isEmpty(per.phone) && !TextUtils.isEmpty(per.pswd)) {
//				login();
//			} else {
				notFirst();
//			}
		}
	}

	private void first() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(self, GuidanceActivity.class);
				startActivity(intent);
				per.isFirst = 1;
				per.commit();
				finishWithoutAnim();
			}
		}, 2000);
	}

//	private void login() {
//
//		DhNet net = new DhNet(API.login);
//		net.addParam("phone", per.phone);
//		net.addParam("password", per.pswd);
//		// net.addParam("phone", "13852286536");
//		// net.addParam("password", "123");
//		net.doPostInDialog("登录中...", new NetTask(self) {
//
//			@Override
//			public void doInUI(Response response, Integer transfer) {
//				if (response.isSuccess()) {
//					JSONObject jo = response.jSONFromData();
//					User user = User.getInstance();
//					user.setUid(JSONUtil.getString(jo, "uid"));
//					user.setToken(JSONUtil.getString(jo, "pwd"));
//					user.setLogin(true);
//
//					// showToast("登录成功");
//
//					// Intent it = new Intent(self, MainActivity.class);
//					// startActivity(it);
//					// finishWithoutAnim();
//					// 登录成功后发送事件,关闭之前的页面
//				}else {
//					notFirst();
//				}
//
//				notFirst();
//			}
//
//			@Override
//			public void onErray(Response response) {
//				// TODO Auto-generated method stub
//				super.onErray(response);
//				notFirst();
//			}
//		});
//	}

	private void notFirst() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(self, MainActivity.class);
				startActivity(intent);
				finishWithoutAnim();
			}
		}, 1000);
	}
}
