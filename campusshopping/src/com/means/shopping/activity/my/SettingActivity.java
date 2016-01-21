package com.means.shopping.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.means.shopping.AboutActivity;
import com.means.shopping.R;
import com.means.shopping.base.ShopBaseFragmentActivity;

/**
 * 设置
 * 
 * @author Administrator
 * 
 */
public class SettingActivity extends ShopBaseFragmentActivity implements
		OnClickListener {

	private LinearLayout addressLl, changepwdLl, aboutLl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		setTitle("设置");
		addressLl = (LinearLayout) findViewById(R.id.address);
		changepwdLl = (LinearLayout) findViewById(R.id.changepwd);
		aboutLl = (LinearLayout) findViewById(R.id.about);

		addressLl.setOnClickListener(this);
		changepwdLl.setOnClickListener(this);
		aboutLl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		// 收货地址
		case R.id.address:
			it = new Intent(self, ConsigneeAddressActivity.class);
			startActivity(it);
			break;
		// 修改密码
		case R.id.changepwd:
			it = new Intent(self, ChangePasswordActivity.class);
			startActivity(it);
			break;
		// 关于我们
		case R.id.about:
			it = new Intent(self, AboutActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}
}
