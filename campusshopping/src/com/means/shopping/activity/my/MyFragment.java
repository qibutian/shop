package com.means.shopping.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.means.shopping.R;
import com.means.shopping.activity.main.RecommendActivity;
import com.means.shopping.activity.my.redpacket.MyRedPacketActivity;
import com.means.shopping.activity.pay.PaymentActivity;

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

	private ImageView headI;
	private ImageView settingI;
	private LinearLayout myredpacketLl,changepwdLl;

	View recommendV;

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
		return mainV;
	}

	private void initView() {
		headI = (ImageView) mainV.findViewById(R.id.head);
		settingI = (ImageView) mainV.findViewById(R.id.setting);
		myredpacketLl = (LinearLayout) mainV.findViewById(R.id.myredpacket);
		recommendV = mainV.findViewById(R.id.recommend);
		changepwdLl = (LinearLayout) mainV.findViewById(R.id.changepwd);
		headI.setOnClickListener(this);
		settingI.setOnClickListener(this);
		myredpacketLl.setOnClickListener(this);
		recommendV.setOnClickListener(this);
		changepwdLl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		// 头像
		case R.id.head:
//			it = new Intent(getActivity(), ChangePasswordActivity.class);
//			it = new Intent(getActivity(), RegisterActivity.class);
			it = new Intent(getActivity(), LoginActivity.class);
			// it = new Intent(getActivity(), PaymentActivity.class);
			// it = new Intent(getActivity(), CampusSelectActivity.class);
			getActivity().startActivity(it);
			break;
		// 设置
		case R.id.setting:
			it = new Intent(getActivity(), SettingActivity.class);
			getActivity().startActivity(it);
			break;
		// 我的红包
		case R.id.myredpacket:
			it = new Intent(getActivity(), MyRedPacketActivity.class);
			getActivity().startActivity(it);
			break;

		case R.id.recommend:
			it = new Intent(getActivity(), RecommendActivity.class);
			getActivity().startActivity(it);
			break;
		//修改密码
		case R.id.changepwd:
			it = new Intent(getActivity(), ChangePasswordActivity.class);
			getActivity().startActivity(it);
			break;
		default:
			break;
		}
	}
}
