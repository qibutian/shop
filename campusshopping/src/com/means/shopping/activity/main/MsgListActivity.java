package com.means.shopping.activity.main;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import android.os.Bundle;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.views.RefreshListViewAndMore;

public class MsgListActivity extends ShopBaseActivity {

	RefreshListViewAndMore listV;

	NetJSONAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_list);
	}

	@Override
	public void initView() {
		setTitle("消息列表");
		listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		adapter = new NetJSONAdapter(API.test, self, R.layout.item_msg_list);
		listV.setAdapter(adapter);
		adapter.showNextInDialog();

	}

}
