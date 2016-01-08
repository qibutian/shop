package com.means.shopping.activity.my.redpacket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.means.shopping.R;

/**
 * 已过期的红包
 * @author Administrator
 *
 */
public class PastApplicableFragment extends Fragment{
	static PastApplicableFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	public static PastApplicableFragment getInstance() {
		if (instance == null) {
			instance = new PastApplicableFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_past_applicable, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {

	}
}
