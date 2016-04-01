package com.means.shopping.activity.order;

import java.util.Date;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.utils.ShopUtils;
import com.means.shopping.views.RefreshListViewAndMore;

/**
 * 订单->近一月
 * @author Administrator
 *
 */
public class RecentFragment extends Fragment{
	static RecentFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;
	
	RefreshListViewAndMore listV;
	NetJSONAdapter adapter;
	ListView contentListV;

	public static RecentFragment getInstance() {
		if (instance == null) {
			instance = new RecentFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_recent, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		getData();
	}
	
	private void getData(){
		
		adapter = new NetJSONAdapter(API.listall, getActivity(),
				R.layout.item_recent_order_list);
		adapter.addparam("type", 1);
		adapter.fromWhat("list");
		adapter.addField("payprice", R.id.payprice);
		adapter.addField("code", R.id.code);
		adapter.addField("buyphone", R.id.buyphone);
		adapter.addField(new FieldMap("adddateline", R.id.adddateline) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				return ShopUtils.dateToStrLong(new Date(
						Long.parseLong(o.toString()) * 1000));
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
