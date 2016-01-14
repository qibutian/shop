package com.means.shopping.activity.order;

import org.json.JSONObject;

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
import com.means.shopping.views.RefreshListViewAndMore;

/**
 * 订单 -> 待支付
 * 
 * @author Administrator
 * 
 */
public class WaitPaymentFragment extends Fragment {
	static WaitPaymentFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	RefreshListViewAndMore listV;
	NetJSONAdapter adapter;
	ListView contentListV;

	public static WaitPaymentFragment getInstance() {
		if (instance == null) {
			instance = new WaitPaymentFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_wait_payment, null);
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

		adapter = new NetJSONAdapter(API.test, getActivity(),
				R.layout.item_waitpayment_order_list);
		adapter.fromWhat("data");
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
}
