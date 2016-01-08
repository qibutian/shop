package com.means.shopping.activity.my.redpacket;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.order.RecentFragment;
import com.means.shopping.activity.order.WaitPaymentFragment;
import com.means.shopping.activity.order.WaitReceivingFragment;
import com.means.shopping.adapter.OrderFragmentPageAdapter;
import com.means.shopping.base.ShopBaseActivity;

/**
 * 我的红包
 * 
 * @author Administrator
 * 
 */
public class MyRedPacketActivity extends ShopBaseActivity {

	private LinearLayout tabV;

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_red_packet);
	}

	@Override
	public void initView() {
		setTitle("我的红包");
		
		tabV = (LinearLayout) findViewById(R.id.tab);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		List<Fragment> frags = new ArrayList<Fragment>();
		frags.add(CanApplicableFragment.getInstance());
		frags.add(AlreadyApplicableFragment.getInstance());
		frags.add(PastApplicableFragment.getInstance());
		
		OrderFragmentPageAdapter adapter = new OrderFragmentPageAdapter(getSupportFragmentManager(), frags);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				setTab(arg0);
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
		initTab();
		setTab(0);
	}
	
	private void initTab() {
		for (int i = 0; i < tabV.getChildCount(); i++) {
			final int index = i;
			RelativeLayout childV = (RelativeLayout) tabV.getChildAt(i);
			childV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					viewPager.setCurrentItem(index);
					setTab(index);
				}
			});
		}
	}
	
	public void setTab(final int index) {
		for (int i = 0; i < tabV.getChildCount(); i++) {
			RelativeLayout childV = (RelativeLayout) tabV.getChildAt(i);
			TextView textT = (TextView) childV.getChildAt(0);
			View lineV =  childV.getChildAt(1);
			if (i == index) {
				switch (i) {
				case 0:		//近一月
//					switchContent(RecentFragment.getInstance());
					textT.setTextColor(getResources().getColor(R.color.text_ff9_yellow));
					lineV.setVisibility(View.VISIBLE);
					break;

				case 1:		//待收货
//					switchContent(WaitReceivingFragment.getInstance());
					textT.setTextColor(getResources().getColor(R.color.text_ff9_yellow));
					lineV.setVisibility(View.VISIBLE);
					break;

				case 2:		//待支付
//					switchContent(WaitPaymentFragment.getInstance());
					textT.setTextColor(getResources().getColor(R.color.text_ff9_yellow));
					lineV.setVisibility(View.VISIBLE);
					break;

				default:
					break;
				}
			} else {
				textT.setTextColor(getResources().getColor(R.color.text_99_grey));
				lineV.setVisibility(View.INVISIBLE);
			}
		}
	}
	
}
