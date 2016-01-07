package com.means.shopping.activity.my;

import android.os.Bundle;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseFragmentActivity;

/**
 * 注册页面
 * @author Administrator
 *
 */
public class RegisterActivity extends ShopBaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		setTitle("注册");
	}
}
