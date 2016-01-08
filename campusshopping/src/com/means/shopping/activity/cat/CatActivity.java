package com.means.shopping.activity.cat;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.adapter.CatAdapter;
import com.means.shopping.adapter.SecondCatAdapter;
import com.means.shopping.base.ShopBaseActivity;

public class CatActivity extends ShopBaseActivity {

	ListView catListV;

	CatAdapter catAdapter;

	GridView gridView;

	SecondCatAdapter secondCatAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cat);
	}

	@Override
	public void initView() {
		setTitle("分类");
		catListV = (ListView) findViewById(R.id.listview_normal);
		catAdapter = new CatAdapter(self);
		catListV.setAdapter(catAdapter);
		gridView = (GridView) findViewById(R.id.gridView);
		secondCatAdapter = new SecondCatAdapter(self);
		gridView.setAdapter(secondCatAdapter);
	}
}
