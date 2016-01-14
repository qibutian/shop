package com.means.shopping.activity.my;

import com.means.shopping.R;
import com.means.shopping.R.layout;
import com.means.shopping.base.ShopBaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
	}
}
