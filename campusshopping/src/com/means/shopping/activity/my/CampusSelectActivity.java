package com.means.shopping.activity.my;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.means.shopping.R;
import com.means.shopping.R.layout;
import com.means.shopping.adapter.CampusCityAdapter;
import com.means.shopping.adapter.CampusSchoolAdapter;
import com.means.shopping.adapter.CatAdapter;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.Good;
import com.means.shopping.views.CartBottomView;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.CommodityDetailDialog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * 校区选择
 * @author Administrator
 *
 */
public class CampusSelectActivity extends ShopBaseActivity {
	
	ListView lv_city,lv_school;
	CampusCityAdapter city_adapter;
	CampusSchoolAdapter school_adapter;
	
	JSONArray city_jsa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_campus_select);
	}

	@Override
	public void initView() {
		setTitle("校区选择");
		setRightAction("", R.drawable.refresh, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		lv_city = (ListView) findViewById(R.id.listview_city);
		city_adapter = new CampusCityAdapter(self);
		lv_city.setAdapter(city_adapter);
		
		lv_school = (ListView) findViewById(R.id.listview_school);
		school_adapter = new CampusSchoolAdapter(self);
		lv_school.setAdapter(school_adapter);
		
		lv_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				JSONObject jo = city_adapter.getItem(position);
				JSONArray jsa = JSONUtil.getJSONArray(jo, "_item");
				school_adapter.setDate(jsa);
				city_adapter.select(position);
			}
		});
		
		getDate();
	}

	private void getDate() {
		DhNet net = new DhNet(API.schooldata);
		net.doGetInDialog(new NetTask(self) {
			
			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					city_jsa = response.jSONArrayFrom("list");
					city_adapter.setDate(city_jsa);
					if (city_jsa!=null&&city_jsa.length()>0) {
						JSONObject jo = JSONUtil.getJSONObjectAt(city_jsa, 0);
						school_adapter.setDate(JSONUtil.getJSONArray(jo, "_item"));
					}
					
				}
			}
		});
	}
}
