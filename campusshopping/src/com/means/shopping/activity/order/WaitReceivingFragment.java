package com.means.shopping.activity.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.means.shopping.R;

/**
 * 订单 -> 待收货
 * @author Administrator
 *
 */
public class WaitReceivingFragment extends Fragment{
	static WaitReceivingFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	public static WaitReceivingFragment getInstance() {
		if (instance == null) {
			instance = new WaitReceivingFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_wait_receiving, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {

	}
}
