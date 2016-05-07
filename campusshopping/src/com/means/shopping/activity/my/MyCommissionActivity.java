package com.means.shopping.activity.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONObject;

import com.means.shopping.R;
import com.means.shopping.R.layout;
import com.means.shopping.activity.main.TiXianActivity;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.ReChargeEB;

import de.greenrobot.event.EventBus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 我的佣金
 * 
 * @author Administrator
 * 
 */
public class MyCommissionActivity extends ShopBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_commission);
	}

	@Override
	public void initView() {
		setTitle("我的佣金");
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
							JSONUtil.getString(jo, "gold"));

					ViewUtil.bindView(findViewById(R.id.money),
							JSONUtil.getString(jo, "gold"));

					findViewById(R.id.submit).setOnClickListener(
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									double money = JSONUtil.getDouble(jo,
											"gold");
									if (money == 0) {
										showToast("你没有可提现的佣金");
									} else {
										tixian(money + "");
										// Intent it = new Intent(self,
										// TiXianActivity.class);
										// it.putExtra("money", money + "");
										// startActivity(it);
									}
								}
							});

				}
			}
		});
	}

	private void tixian(String money) {

		DhNet net = new DhNet(API.yongjintixian);
		net.addParam("value", money);
		net.doPostInDialog("正在体现到余额...", new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					showToast("提现成功!");
					EventBus.getDefault().post(new ReChargeEB());
					finish();
				}
				// TODO Auto-generated method stub

			}
		});

	}
}
