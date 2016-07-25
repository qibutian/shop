package com.means.shopping.activity.main;

import org.json.JSONArray;
import org.json.JSONObject;

import net.duohuo.dhroid.adapter.PSAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.ReChargeEB;

import de.greenrobot.event.EventBus;

public class SignActivity extends ShopBaseActivity {

	ListView listV;

	PSAdapter adapter;

	TextView des_oneT, des_twoT;

	Button signB;

	int isday;

	int is_condays;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
	}

	@Override
	public void initView() {
		setTitle("签到有礼");
		listV = (ListView) findViewById(R.id.listview_normal);
		adapter = new PSAdapter(self, R.layout.item_sign);
		adapter.addField("des", R.id.des);
		listV.setAdapter(adapter);

		des_oneT = (TextView) findViewById(R.id.des_one);
		des_twoT = (TextView) findViewById(R.id.des_two);
		signB = (Button) findViewById(R.id.sign);

		signB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isday != 1) {
					sign();
				}
			}
		});
		getData();
	}

	private void getData() {
		DhNet net = new DhNet(API.jifenlist);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONArray jsa = response.jSONArrayFrom("list");
					adapter.addAll(jsa);

					JSONObject jo = response.jSON();
					isday = JSONUtil.getInt(jo, "is_days");
					des_oneT.setText(isday == 1 ? "今日已签到" : "今日未签到");
					signB.setText(isday == 1 ? "已签到" : "签到");

					ViewUtil.bindView(findViewById(R.id.des_two), "已连续签到"
							+ is_condays + "天");
				}

			}
		});
	}

	private void sign() {
		DhNet net = new DhNet(API.jifen);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					showToast("签到成功!");
					is_condays = is_condays + 1;
					isday = 1;
					des_oneT.setText(isday == 1 ? "今日已签到" : "今日未签到");
					signB.setText(isday == 1 ? "已签到" : "签到");
					ViewUtil.bindView(findViewById(R.id.des_two), "已连续签到"
							+ is_condays + "天");
					EventBus.getDefault().post(new ReChargeEB());
				}

			}
		});
	}
}
