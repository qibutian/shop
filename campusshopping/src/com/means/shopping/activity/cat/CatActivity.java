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
import android.widget.TextView;

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

	TextView child_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cat);
	}

	@Override
	public void initView() {
		setTitle("分类");
		child_title = (TextView) findViewById(R.id.child_title);
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
				JSONArray secondCat_jsa = JSONUtil.getJSONArray(cat_jo,
						"_child");
				secondCatAdapter.setDate(secondCat_jsa);
				child_title.setText(JSONUtil.getString(cat_jo, "name"));
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				JSONObject jo = secondCatAdapter.getItem(position);
				Intent it = new Intent(self, CatDetailActivity.class);
				it.putExtra("id", JSONUtil.getString(jo, "id"));
				startActivity(it);
			}
		});

		getCatData();
	}

	private void getCatData() {
		DhNet net = new DhNet(API.shop_catlist);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					jsa_cat = response.jSONArrayFromData();
					catAdapter.setData(jsa_cat);

					if (jsa_cat != null && jsa_cat.length() > 0) {
						JSONObject jo = JSONUtil.getJSONObjectAt(jsa_cat, 0);
						secondCatAdapter.setDate(JSONUtil.getJSONArray(jo,
								"_child"));
						child_title.setText(JSONUtil.getString(jo, "name"));
					}
				}
			}
		});
	}
}
