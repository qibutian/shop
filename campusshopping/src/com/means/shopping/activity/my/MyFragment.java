package com.means.shopping.activity.my;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.means.shopping.R;

public class MyFragment extends Fragment{
	static MyFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	public static MyFragment getInstance() {
		if (instance == null) {
			instance = new MyFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_home_page, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {

	}
}
