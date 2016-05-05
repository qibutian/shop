package com.means.shopping.activity.my;

import org.json.JSONObject;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.adapter.PSAdapter;
import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.GlobalParams;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.SchoolEB;
import com.means.shopping.utils.ShopPerference;

import de.greenrobot.event.EventBus;

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
		adapter.fromWhat("list");
		adapter.addparam("keywords", getIntent().getStringExtra("keywords"));
		adapter.addparam("areaid", "");
		adapter.addField("name", R.id.school_name);
		adapter.showNextInDialog();
		listV.setAdapter(adapter);
		listV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				JSONObject jo = adapter.getTItem(position);

				GlobalParams globalParams = IocContainer.getShare().get(
						GlobalParams.class);

				ShopPerference per = IocContainer.getShare().get(
						ShopPerference.class);
				per.load();
				globalParams.setGlobalParam("schoolid", per.schoolId);
				per.schoolId = JSONUtil.getString(jo, "id");
				per.schoolName = JSONUtil.getString(jo, "name");
				per.commit();

				SchoolEB school = new SchoolEB();
				school.setId(JSONUtil.getString(jo, "id"));
				school.setName(JSONUtil.getString(jo, "name"));
				EventBus.getDefault().post(school);
				finish();
			}
		});
	}
}
