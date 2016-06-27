package com.means.shopping.activity.study;

import org.json.JSONObject;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
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
		setTitle(title);

		search_content = (EditText) findViewById(R.id.search_content);
		search_btn = (Button) findViewById(R.id.search_btn);

		search_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 查找
				find();
			}
		});

		// TODO Auto-generated method stub
		listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		getData();
	}

	private void getData() {
		adapter = new NetJSONAdapter(API.paperlist, self,
				R.layout.item_job_particulars);
		adapter.fromWhat("list");
		adapter.addparam("catid", getIntent().getStringExtra("catid"));
		adapter.addField("title", R.id.jobname);

		listV.setAdapter(adapter);
		contentListV = listV.getListView();

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = adapter.getTItem(position);
				int is_answer = JSONUtil.getInt(jo, "is_answer");
				Intent it;
				if (is_answer == 1) { // 未答过该题库
					it = new Intent(self, QuestionbankActivity.class);
					it.putExtra("title", JSONUtil.getString(jo, "title"));
					it.putExtra("contentid", JSONUtil.getString(jo, "catid"));
					it.putExtra("type", "1");
					startActivity(it);
				} else {
					it = new Intent(self, StudyScoreActivity.class);
					it.putExtra("title", JSONUtil.getString(jo, "title"));
					it.putExtra("catid", JSONUtil.getString(jo, "catid"));
					startActivity(it);
				}
			}
		});
	}

	// 搜素
	private void find() {
		String content = search_content.getText().toString();
		// if (TextUtils.isEmpty(content)) {
		// showToast("请输入查找内容");
		// return;
		// }
		adapter.addparam("keywords", content);
		adapter.refresh();

	}
}
