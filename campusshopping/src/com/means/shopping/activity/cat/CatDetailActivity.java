package com.means.shopping.activity.cat;

import net.duohuo.dhroid.net.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.means.shopping.R;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.Good;
import com.means.shopping.views.CartBottomView;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.CommodityDetailDialog;
import com.means.shopping.views.dialog.CommodityDetailDialog.OnResultListener;

public class CatDetailActivity extends ShopBaseActivity {
	RefreshListViewAndMore goodListV;

	HomePageAdapter goodAdater;
	ListView goodListContentV;
	CartBottomView cartBootmView;

	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cat_detail);
	}

	@Override
	public void initView() {
		id = getIntent().getStringExtra("id");
		// setTitle("商品");
		cartBootmView = (CartBottomView) findViewById(R.id.cartBootmView);
		goodListV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		goodListContentV = goodListV.getListView();
		goodAdater = new HomePageAdapter(API.shop_contentlist, self,
				R.layout.item_home_list, 1);
		goodAdater.addparam("catid", id);
		goodAdater.fromWhat("list");
		goodAdater.setTargetView(cartBootmView.getCartImageView());
		goodListV.setAdapter(goodAdater);
		goodListContentV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				final JSONObject jo = goodAdater.getTItem(position
						- goodListContentV.getHeaderViewsCount());
				Long goodId = JSONUtil.getLong(jo, "id");
				Good good = new Good();
				good.setCount(JSONUtil.getInt(jo, "cartcount"));
				good.setGoodId(goodId);
				good.setGoodType(1);
				CommodityDetailDialog dialog = new CommodityDetailDialog(self,
						good, jo);
				dialog.setOnResultListener(new OnResultListener() {

					@Override
					public void onResult(int cartcount) {
						try {
							jo.put("cartcount", cartcount);
							goodAdater.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				dialog.show();

			}
		});
		goodListV.setAdapter(goodAdater);
	}

}
