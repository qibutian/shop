package com.means.shopping.activity.market;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.means.shopping.R;
import com.means.shopping.adapter.CatAdapter;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.views.RefreshListViewAndMore;

public class MarketActivity extends ShopBaseActivity {

	ListView catListV;

	CatAdapter catAdapter;

	RefreshListViewAndMore goodListV;

	HomePageAdapter goodAdater;

	ListView goodListContentV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);
	}

	@Override
	public void initView() {
		catListV = (ListView) findViewById(R.id.catlist);
		catAdapter = new CatAdapter(self);
		catListV.setAdapter(catAdapter);

		goodListV = (RefreshListViewAndMore) findViewById(R.id.goodlist);
		goodListContentV = goodListV.getListView();
		goodAdater = new HomePageAdapter(API.test, self,
				R.layout.item_home_list);
		goodAdater.fromWhat("data");
		goodListV.setAdapter(goodAdater);
		goodListContentV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

			}
		});
		goodListV.setAdapter(goodAdater);
	}
}
