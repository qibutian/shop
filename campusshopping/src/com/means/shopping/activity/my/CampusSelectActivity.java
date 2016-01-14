package com.means.shopping.activity.my;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;

import org.json.JSONObject;

import com.means.shopping.R;
import com.means.shopping.R.layout;
import com.means.shopping.adapter.CatAdapter;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.Good;
import com.means.shopping.views.CartBottomView;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.CommodityDetailDialog;

import android.app.Activity;
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
	ListView catListV;

	CatAdapter catAdapter;

	RefreshListViewAndMore goodListV;

	ListView goodListContentV;
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
		
		catListV = (ListView) findViewById(R.id.listview_normal);
		catAdapter = new CatAdapter(self);
		catListV.setAdapter(catAdapter);

		goodListV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		goodListContentV = goodListV.getListView();
		NetJSONAdapter adapter = new NetJSONAdapter(API.test, self,
				R.layout.item_recent_order_list);
		goodListV.setAdapter(adapter);
		goodListContentV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

			}
		});
		
	}
}
