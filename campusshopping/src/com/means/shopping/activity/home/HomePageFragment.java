package com.means.shopping.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.activity.cat.CatActivity;
import com.means.shopping.activity.market.MarketActivity;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.RedPacketDialog;

public class HomePageFragment extends Fragment implements OnClickListener {
	static HomePageFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	RefreshListViewAndMore listV;

	View headV;

	HomePageAdapter adapter;

	ListView contentListV;

	// 超市点击按钮
	View marketV, redpacketV;

	// 分类按钮
	View classifyV;

	public static HomePageFragment getInstance() {
		if (instance == null) {
			instance = new HomePageFragment();
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
		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		headV = mLayoutInflater.inflate(R.layout.head_home_page, null);
		listV.addHeadView(headV);
		contentListV = listV.getListView();
		adapter = new HomePageAdapter(API.test, getActivity(),
				R.layout.item_home_list);
		adapter.fromWhat("data");
		listV.setAdapter(adapter);
		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

			}
		});

		marketV = headV.findViewById(R.id.market);
		redpacketV = headV.findViewById(R.id.redpacket);
		marketV.setOnClickListener(this);

		classifyV = headV.findViewById(R.id.classify);
		classifyV.setOnClickListener(this);
		redpacketV.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		// 超市
		case R.id.market:
			it = new Intent(getActivity(), MarketActivity.class);
			startActivity(it);
			break;
		// 红包
		case R.id.redpacket:
			RedPacketDialog redDialog = new RedPacketDialog(getActivity());
			redDialog.show();
			break;

		case R.id.classify:
			it = new Intent(getActivity(), CatActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}
}
