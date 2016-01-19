package com.means.shopping.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.means.shopping.R;
import com.means.shopping.adapter.SimplePageAdapter;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.views.CirclePageIndicator;

/**
 * 引导页
 * @author Administrator
 *
 */
public class GuidanceActivity extends ShopBaseActivity {
	SimplePageAdapter pagerAdapter;
	ViewPager pager;
	View firstView, secondView;
	LayoutInflater mLayoutInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guidance);
	}

	@Override
	public void initView() {
		mLayoutInflater = LayoutInflater.from(self);
		firstView = mLayoutInflater.inflate(R.layout.item_guidance_page_first,
				null);
		secondView = mLayoutInflater.inflate(
				R.layout.item_guidance_page_second, null);
		pager = (ViewPager) findViewById(R.id.viewpager);
		pagerAdapter = new SimplePageAdapter(firstView, secondView);

		pager.setAdapter(pagerAdapter);

		CirclePageIndicator mIndicator  = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
		secondView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(self, MainActivity.class);
				startActivity(intent);
				finishWithoutAnim();
			}
		});
	}
}
