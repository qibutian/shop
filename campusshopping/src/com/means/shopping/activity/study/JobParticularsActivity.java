package com.means.shopping.activity.study;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.views.RefreshListViewAndMore;

/**
 * 
 * 职业细化
 * 
 * @author Administrator
 * 
 */
public class JobParticularsActivity extends ShopBaseActivity {

	RefreshListViewAndMore listV;
	NetJSONAdapter adapter;
	ListView contentListV;
	
	EditText search_content;
	Button search_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_particulars);
	}

	@Override
	public void initView() {
		String title = getIntent().getStringExtra("title");
		setLeftAction(-1, title, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		search_content = (EditText) findViewById(R.id.search_content);
		search_btn = (Button) findViewById(R.id.search_btn);
		
		search_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//查找
				find();
			}
		});
		
		// TODO Auto-generated method stub
		listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		getData();
	}

	private void getData() {
		adapter = new NetJSONAdapter(API.listall, self,
				R.layout.item_job_particulars);
		adapter.fromWhat("list");
		adapter.addField("jobname", R.id.jobname);

		listV.setAdapter(adapter);
		contentListV = listV.getListView();

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent it = new Intent(self,StudyScoreActivity.class);
				startActivity(it);
				
			}
		});
	}
	

	//搜素
	private void find() {
		String content = search_content.getText().toString();
		if (TextUtils.isEmpty(content)) {
			showToast("请输入查找内容");
			return;
		}
		adapter.refresh();
		
	}
}
