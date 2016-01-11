package com.means.shopping.activity.market;

import android.os.Bundle;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;

public class RechargeActivity extends ShopBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
	}

	@Override
	public void initView() {
		setTitle("充值");
	}

}
