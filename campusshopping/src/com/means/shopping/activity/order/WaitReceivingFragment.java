package com.means.shopping.activity.order;

import java.util.Date;

import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.utils.ShopUtils;
import com.means.shopping.views.RefreshListViewAndMore;

/**
 * 订单 -> 待收货
 * 
 * @author Administrator
 * 
 */
public class WaitReceivingFragment extends Fragment {
	static WaitReceivingFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	RefreshListViewAndMore listV;
	NetJSONAdapter adapter;
	ListView contentListV;

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
		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		getData();
	}

	private void getData() {

		adapter = new NetJSONAdapter(API.listall, getActivity(),
				R.layout.item_waitreceiving_order_list);
		adapter.addparam("type", 2);
		adapter.fromWhat("list");
		adapter.addField("payprice", R.id.payprice);
		adapter.addField("code", R.id.code);

		adapter.addField("buyphone", R.id.buyphone);
		adapter.addField(new FieldMap("adddateline", R.id.adddateline) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				return ShopUtils.dateToStrLong(new Date(Long.parseLong(o
						.toString()) * 1000));
			}
		});

		listV.setAdapter(adapter);
		contentListV = listV.getListView();
		contentListV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = adapter.getTItem(position - 1);
			}
		});
	}
	
	public void refresh() {
		listV.refresh();
	}
}
