package com.means.shopping.activity.my;

import android.os.Bundle;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseFragmentActivity;

/**
 * 设置
 * @author Administrator
 *
 */
public class SettingActivity extends ShopBaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		setTitle("设置");
	}
}
