package com.means.shopping.activity.my.redpacket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.means.shopping.R;

/**
 * 已经使用的红包
 * @author Administrator
 *
 */
public class AlreadyApplicableFragment extends Fragment{
	static AlreadyApplicableFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	public static AlreadyApplicableFragment getInstance() {
		if (instance == null) {
			instance = new AlreadyApplicableFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_already_applicable, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {

	}
}
