package com.means.shopping.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.home.HomePageFragment;

public class MainActivity extends FragmentActivity {

	private FragmentManager fm;
	private Fragment currentFragment;

	private LinearLayout tabV;
	private TextView titleT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initTab();
		setTab(0);
	}

	private void initView() {
		// TODO Auto-generated method stub
		fm = getSupportFragmentManager();
		tabV = (LinearLayout) findViewById(R.id.tab);
	}

	private void initTab() {
		for (int i = 0; i < tabV.getChildCount(); i++) {
			final int index = i;
			LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
			childV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setTab(index);
				}
			});
		}
	}

	public void setTab(final int index) {
		for (int i = 0; i < tabV.getChildCount(); i++) {
			LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
			RelativeLayout imgV = (RelativeLayout) childV.getChildAt(0);
			ImageView imgI = (ImageView) imgV.getChildAt(0);
			TextView textT = (TextView) childV.getChildAt(1);
			if (i == index) {
				switch (i) {
				case 0:
					switchContent(HomePageFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_home_s);
					textT.setTextColor(getResources().getColor(
							R.color.tab_index_bg));
					break;

				case 1:
					switchContent(HomePageFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_order_s);
					textT.setTextColor(getResources().getColor(
							R.color.tab_index_bg));
					break;

				case 2:
					switchContent(HomePageFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_my_s);
					textT.setTextColor(getResources().getColor(
							R.color.tab_index_bg));
					break;

				default:
					break;
				}
			} else {
				childV.setBackgroundColor(getResources().getColor(
						R.color.nothing));
				switch (i) {
				case 0:
					imgI.setImageResource(R.drawable.icon_home_n);
					textT.setTextColor(getResources().getColor(
							R.color.text_43_black));
					break;

				case 1:
					imgI.setImageResource(R.drawable.icon_order_n);
					textT.setTextColor(getResources().getColor(
							R.color.text_43_black));
					break;

				case 2:
					imgI.setImageResource(R.drawable.icon_my_n);
					textT.setTextColor(getResources().getColor(
							R.color.text_43_black));
					break;

				default:
					break;
				}
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
				t.add(R.id.main_content, fragment);
			}
			t.show(fragment);
			t.commitAllowingStateLoss();
			currentFragment = fragment;
		} catch (Exception e) {
		}
	}
}
