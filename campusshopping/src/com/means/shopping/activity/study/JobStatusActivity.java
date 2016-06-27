package com.means.shopping.activity.study;

import org.json.JSONArray;
import org.json.JSONObject;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

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
		setTitle("职业资格");
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		adapter = new JobStatusAdapter(self);
		expandableListView.setAdapter(adapter);
		expandableListView.setGroupIndicator(null);
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
					int groupPosition, long arg3) {
				if (adapter.getChildrenCount(groupPosition) == 0) {
					JSONObject jo = adapter.getGroup(groupPosition);
					Intent it = new Intent(self, JobParticularsActivity.class);
					it.putExtra("catid", JSONUtil.getString(jo, "id"));
					it.putExtra("title", JSONUtil.getString(jo, "name"));
					startActivity(it);
				}

				return false;
			}
		});
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
