package com.means.shopping.activity.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.shopping.R;
import com.means.shopping.activity.main.TiXianActivity;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

public class MyBlanceActivity extends ShopBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myblance);
	}

	@Override
	public void initView() {
		setTitle("我的余额");
		getUserInfo();
	}

	private void getUserInfo() {
		DhNet net = new DhNet(API.userInfo);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					final JSONObject jo = response.jSONFromData();
					ViewUtil.bindView(findViewById(R.id.money_one),
							JSONUtil.getString(jo, "blance"));

					ViewUtil.bindView(findViewById(R.id.money),
							JSONUtil.getString(jo, "blance"));

					findViewById(R.id.submit).setOnClickListener(
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									double money = JSONUtil.getDouble(jo,
											"balance");
									if (money == 0) {
										showToast("你没有可提现的余额");
									} else {
										Intent it = new Intent(self,
												TiXianActivity.class);
										it.putExtra("money", money + "");
										startActivity(it);
									}
								}
							});

				}
			}
		});
	}
}
