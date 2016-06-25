package com.means.shopping.activity.study;

import org.json.JSONArray;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

import com.means.shopping.R;
import com.means.shopping.adapter.JobStatusAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

/**
 * 
 * 职业资格
 * 
 * @author Administrator
 * 
 */
public class JobStatusActivity extends ShopBaseActivity {
	ExpandableListView expandableListView;
	JobStatusAdapter adapter;
	
	
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
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		adapter = new JobStatusAdapter(self);
		expandableListView.setAdapter(adapter);
		expandableListView.setGroupIndicator(null);  
		getData();
	}

	private void getData() {
		DhNet net = new DhNet(API.papercat);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONArray jsa = response.jSONArrayFromData();
					adapter.setData(jsa);
				}
			}
		});

	}


}
