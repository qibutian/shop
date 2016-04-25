package com.means.shopping.activity.my;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.adapter.PSAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.os.Bundle;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

public class SearchSchoolActivity extends ShopBaseActivity {

	ListView listV;

	NetJSONAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_school);
	}

	@Override
	public void initView() {
		setTitle("搜索学校");
		listV = (ListView) findViewById(R.id.listview_school);
		adapter = new NetJSONAdapter(API.search_school, self,
				R.layout.item_campus_school);
		adapter.addparam("keywords", getIntent().getStringExtra("keywords"));
		adapter.addparam("areaid", "");
		adapter.addField("name", R.id.school_name);
		adapter.showNextInDialog();
	}

}
