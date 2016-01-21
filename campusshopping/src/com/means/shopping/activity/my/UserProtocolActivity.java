package com.means.shopping.activity.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import com.means.shopping.R;
import com.means.shopping.R.layout;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 注册协议
 * @author Administrator
 *
 */
public class UserProtocolActivity extends ShopBaseActivity {

	TextView titleT, contentT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_protocol);
	}

	@Override
	public void initView() {
		setTitle("注册协议");
		titleT = (TextView) findViewById(R.id.title);
		contentT = (TextView) findViewById(R.id.content);
		
		DhNet net = new DhNet(API.aboutregist);
		net.doGet(new NetTask(self) {
			
			@Override
			public void doInUI(Response response, Integer transfer) {
				
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					titleT.setText(JSONUtil.getString(jo, "title"));
					contentT.setText(JSONUtil.getString(jo, "content"));
				}
				
			}
		});
	}
}
