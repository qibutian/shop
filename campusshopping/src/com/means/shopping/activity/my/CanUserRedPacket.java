package com.means.shopping.activity.my;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.PSAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;

public class CanUserRedPacket extends ShopBaseActivity {

	ListView listV;

	PSAdapter adapter;

	JSONArray jsa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canuse_redpacket);

	}

	@Override
	public void initView() {
		setTitle("选择红包");

		String data = getIntent().getStringExtra("data");
		try {
			jsa = new JSONArray(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		listV = (ListView) findViewById(R.id.listview_normal);
		adapter = new PSAdapter(self, R.layout.item_my_redpacket_list);
		adapter.addAll(jsa);
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
		listV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				JSONObject jo = (JSONObject) adapter.getTItem(position);
				Intent it = getIntent();
				it.putExtra("jo", jo.toString());
				setResult(Activity.RESULT_OK, it);
				finish();
			}
		});
	}

}
