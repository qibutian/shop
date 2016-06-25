package com.means.shopping.activity.study;

import java.util.Date;

import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;

import com.means.shopping.R;
import com.means.shopping.R.layout;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.utils.ShopUtils;
import com.means.shopping.views.RefreshListViewAndMore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * 职业资格
 * 
 * @author Administrator
 * 
 */
public class JobStatusActivity extends ShopBaseActivity {

	RefreshListViewAndMore listV;
	NetJSONAdapter adapter;
	ListView contentListV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_status);
	}

	@Override
	public void initView() {
		setLeftAction(-1, "职业资格", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		getData();
	}

	private void getData() {
		adapter = new NetJSONAdapter(API.paperlist, self,
				R.layout.item_job_status);
		adapter.fromWhat("list");
		adapter.addField("title", R.id.jobname);

		listV.setAdapter(adapter);
		contentListV = listV.getListView();

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				Intent it = new Intent(self,JobParticularsActivity.class);
				it.putExtra("title", "小学教师");
				startActivity(it);

			}
		});
	}
}
