package com.means.shopping.activity.cat;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.adapter.CatAdapter;
import com.means.shopping.adapter.SecondCatAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

public class CatActivity extends ShopBaseActivity {

	ListView catListV;

	CatAdapter catAdapter;

	GridView gridView;

	SecondCatAdapter secondCatAdapter;
	
	JSONArray jsa_cat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cat);
	}

	@Override
	public void initView() {
		setTitle("分类");
		catListV = (ListView) findViewById(R.id.listview_normal);
		catAdapter = new CatAdapter(self);
		catListV.setAdapter(catAdapter);
		gridView = (GridView) findViewById(R.id.gridView);
		secondCatAdapter = new SecondCatAdapter(self);
		gridView.setAdapter(secondCatAdapter);
		
		catListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				catAdapter.select(position);
				JSONObject cat_jo = catAdapter.getItem(position);
				JSONArray secondCat_jsa =  JSONUtil.getJSONArray(cat_jo, "_child");
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(self, CatDetailActivity.class);
				startActivity(it);
			}
		});
		
		getCatData();
	}
	
	private void getCatData(){
		DhNet net = new DhNet(API.shop_catlist);
		net.doGetInDialog(new NetTask(self) {
			
			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					jsa_cat = response.jSONArrayFromData();
					catAdapter.setData(jsa_cat);
				}
			}
		});
	}
}
