package com.means.shopping.activity.study;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.shopping.R;
import com.means.shopping.adapter.SimpleFragmentPageAdapter;
import com.means.shopping.base.ShopBaseActivity;

/**
 * 
 * 题库
 * 
 * @author Administrator
 * 
 */
public class QuestionbankActivity extends ShopBaseActivity {

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionbank);
	}

	@Override
	public void initView() {
		String title = getIntent().getStringExtra("title");
		setLeftAction(-1, title, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		
		getData();
	}

	private void getData() {
		List<Fragment> frags = new ArrayList<Fragment>();
		for (int i = 0; i < 10; i++) {
			frags.add(new QuestionbankFragment());
		}
		SimpleFragmentPageAdapter adapter = new SimpleFragmentPageAdapter(getSupportFragmentManager(), frags);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(10);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
