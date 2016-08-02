package com.means.shopping.activity.order;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.adapter.OrderFragmentPageAdapter;
import com.means.shopping.bean.LoginEB;
import com.means.shopping.bean.PayEB;
import com.means.shopping.bean.ReChargeEB;
import com.means.shopping.bean.RefreshEB;
import com.means.shopping.bean.SchoolEB;

import de.greenrobot.event.EventBus;

/**
 * 订单
 * 
 * @author Administrator
 * 
 */
public class OrderFragment extends Fragment {
	static OrderFragment instance;

	private View mainV;

	private FragmentManager fm;
	private Fragment currentFragment;
	private LayoutInflater mLayoutInflater;

	private LinearLayout tabV;

	private ViewPager viewPager;

	public static OrderFragment getInstance() {
		if (instance == null) {
			instance = new OrderFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_order, null);
		mLayoutInflater = inflater;
		EventBus.getDefault().register(this);
		initView();
		initTab();
		setTab(0);
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		fm = getActivity().getSupportFragmentManager();
		tabV = (LinearLayout) mainV.findViewById(R.id.tab);

		viewPager = (ViewPager) mainV.findViewById(R.id.viewpager);
		List<Fragment> frags = new ArrayList<Fragment>();
		frags.add(RecentFragment.getInstance());
		frags.add(WaitReceivingFragment.getInstance());
		frags.add(WaitPaymentFragment.getInstance());

		OrderFragmentPageAdapter adapter = new OrderFragmentPageAdapter(
				getActivity().getSupportFragmentManager(), frags);
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
			View lineV = childV.getChildAt(1);
			if (i == index) {
				switch (i) {
				case 0: // 近一月
					// switchContent(RecentFragment.getInstance());
					textT.setTextColor(getActivity().getResources().getColor(
							R.color.white));
					lineV.setVisibility(View.VISIBLE);
					break;

				case 1: // 待收货
					// switchContent(WaitReceivingFragment.getInstance());
					textT.setTextColor(getActivity().getResources().getColor(
							R.color.white));
					lineV.setVisibility(View.VISIBLE);
					break;

				case 2: // 待支付
					// switchContent(WaitPaymentFragment.getInstance());
					textT.setTextColor(getActivity().getResources().getColor(
							R.color.white));
					lineV.setVisibility(View.VISIBLE);
					break;

				default:
					break;
				}
			} else {
				textT.setTextColor(getActivity().getResources().getColor(
						R.color.text_43_black));
				lineV.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void switchContent(Fragment fragment) {
		try {
			FragmentTransaction t = fm.beginTransaction();
			if (currentFragment != null) {
				t.hide(currentFragment);
			}
			if (!fragment.isAdded()) {
				t.add(R.id.content, fragment);
			}
			t.show(fragment);
			t.commitAllowingStateLoss();
			currentFragment = fragment;
		} catch (Exception e) {
		}
	}

	public void onEventMainThread(PayEB payEB) {
		refresh();
	}

	public void onEventMainThread(RefreshEB RefreshEB) {
		refresh();
	}

	public void onEventMainThread(ReChargeEB reChargeEB) {
		refresh();
	}

	public void onEventMainThread(LoginEB login) {
		refresh();
	}

	public void refresh() {
		RecentFragment.getInstance().refresh();
		WaitReceivingFragment.getInstance().refresh();
		WaitPaymentFragment.getInstance().refresh();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
