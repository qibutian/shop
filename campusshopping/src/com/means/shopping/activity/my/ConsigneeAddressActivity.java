package com.means.shopping.activity.my;

import com.means.shopping.R;
import com.means.shopping.R.layout;
import com.means.shopping.base.ShopBaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

/**
 * 收货地址
 * @author Administrator
 *
 */
public class ConsigneeAddressActivity extends ShopBaseActivity {
	
	private RadioGroup sexGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consignee_address);
	}

	@Override
	public void initView() {
		setTitle("收货地址");
		sexGroup = (RadioGroup) findViewById(R.id.sexgroup);
		
		sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
