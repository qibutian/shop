package com.means.shopping.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.means.shopping.R;

/**
 * 我的
 * 
 * @author Administrator
 * 
 */
public class MyFragment extends Fragment implements OnClickListener {
	static MyFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	private ImageView headI, settingI;

	public static MyFragment getInstance() {
		if (instance == null) {
			instance = new MyFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_my, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		headI = (ImageView) mainV.findViewById(R.id.head);
		settingI = (ImageView) mainV.findViewById(R.id.setting);

		headI.setOnClickListener(this);
		settingI.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.head:
			it = new Intent(getActivity(), LoginActivity.class);
			getActivity().startActivity(it);
			break;
		case R.id.setting:
			it = new Intent(getActivity(), SettingActivity.class);
			getActivity().startActivity(it);
			break;

		default:
			break;
		}
	}
}
