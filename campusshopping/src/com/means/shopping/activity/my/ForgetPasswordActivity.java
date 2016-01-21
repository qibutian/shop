package com.means.shopping.activity.my;

import com.means.shopping.R;
import com.means.shopping.R.layout;
import com.means.shopping.base.ShopBaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 忘记密码
 * @author Administrator
 *
 */
public class ForgetPasswordActivity extends ShopBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setTitle("找回密码");
	}
}
