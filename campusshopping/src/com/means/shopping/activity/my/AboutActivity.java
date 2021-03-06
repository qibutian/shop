package com.means.shopping.activity.my;

import org.json.JSONObject;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.DhUtil;

import com.means.shopping.R;
import com.means.shopping.R.id;
import com.means.shopping.R.layout;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutActivity extends ShopBaseActivity {

	TextView titleT, contentT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	@Override
	public void initView() {
		titleT = (TextView) findViewById(R.id.top_title);
		contentT = (TextView) findViewById(R.id.content);
		setTitle("关于我们");
		DhNet net = new DhNet(API.about);
		net.doGet(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					titleT.setText(JSONUtil.getString(jo, "title"));
					contentT.setText(Html.fromHtml(JSONUtil.getString(jo,
							"content")));
				}

			}
		});
	}
}
