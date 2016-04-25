package com.means.shopping.activity.my.redpacket;

import java.util.Date;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.utils.ShopUtils;
import com.means.shopping.views.RefreshListViewAndMore;

/**
 * 可使用的红包
 * 
 * @author Administrator
 * 
 */
public class CanApplicableFragment extends Fragment {
	static CanApplicableFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	RefreshListViewAndMore listV;
	NetJSONAdapter adapter;
	ListView contentListV;

	public static CanApplicableFragment getInstance() {
		if (instance == null) {
			instance = new CanApplicableFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_can_applicable, null);
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
		adapter = new NetJSONAdapter(API.walletlist, getActivity(),
				R.layout.item_my_redpacket_list);
		adapter.addparam("status", 1);
		adapter.fromWhat("list");
		adapter.addField(new FieldMap("minamount", R.id.minamount) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				JSONObject jso = (JSONObject) jo;
				String amount = JSONUtil.getString(jso, "amount");
				// TODO Auto-generated method stub
				return "购物满" + o.toString() + "减" + amount;
			}
		});
		adapter.addField("amount", R.id.amount);
		adapter.addField(new FieldMap("startdate", R.id.startdate) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				return o;
			}
		});
		adapter.addField(new FieldMap("enddate", R.id.enddate) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				return o;
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
}
