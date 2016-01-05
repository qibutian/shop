package com.means.shopping.activity.order;

import com.means.shopping.R;
import com.means.shopping.activity.my.MyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OrderFragment extends Fragment{
	static OrderFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

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
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {

	}
}
