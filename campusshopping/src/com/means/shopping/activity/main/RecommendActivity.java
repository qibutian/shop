package com.means.shopping.activity.main;

import android.os.Bundle;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;

public class RecommendActivity extends ShopBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
	}

	@Override
	public void initView() {
		setTitle("推荐有奖");
	}

}