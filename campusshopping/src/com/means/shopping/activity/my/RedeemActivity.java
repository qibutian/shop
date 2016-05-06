package com.means.shopping.activity.my;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import android.os.Bundle;
import android.widget.GridView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

public class RedeemActivity extends ShopBaseActivity {

	GridView gridView;

	NetJSONAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redeem);
	}

	@Override
	public void initView() {
		setTitle("积分兑换");
		gridView = (GridView) findViewById(R.id.gridView);
		adapter = new NetJSONAdapter(API.shop_contentlist, self,
				R.layout.item_redeem);
		adapter.fromWhat("list");
		gridView.setAdapter(adapter);
		adapter.refreshDialog();
	}

}
