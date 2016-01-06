package com.means.shopping.activity.order;

import com.means.shopping.R;
import com.means.shopping.activity.my.MyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 订单->近一月
 * @author Administrator
 *
 */
public class RecentFragment extends Fragment{
	static RecentFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	public static RecentFragment getInstance() {
		if (instance == null) {
			instance = new RecentFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_recent, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {

	}
}
