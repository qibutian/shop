package com.means.shopping.activity.main;

import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.means.shopping.R;
import com.means.shopping.api.API;
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
			if (!TextUtils.isEmpty(per.getPhone()) && !TextUtils.isEmpty(per.getPswd())) {
				login();
			} else {
				notFirst();
			}
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

	private void login() {
		DhNet net = new DhNet(API.login);
		net.addParam("pswd", per.getPswd());
		net.addParam("phone", per.getPhone());
		net.doPostInDialog("登录中...", new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					per.setLogin(true);
					per.commit();
				}
				notFirst();
			}

			@Override
			public void onErray(Response response) {
				// TODO Auto-generated method stub
				super.onErray(response);
				notFirst();
			}
		});
	}

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
