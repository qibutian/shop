package com.means.shopping.activity.cat;

import net.duohuo.dhroid.net.JSONUtil;

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

public class CatDetailActivity extends ShopBaseActivity {
	RefreshListViewAndMore goodListV;

	HomePageAdapter goodAdater;
	ListView goodListContentV;
	CartBottomView cartBootmView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cat_detail);
	}

	@Override
	public void initView() {
//		setTitle("商品");
		cartBootmView = (CartBottomView) findViewById(R.id.cartBootmView);
		goodListV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		goodListContentV = goodListV.getListView();
		goodAdater = new HomePageAdapter(API.test, self,
				R.layout.item_home_list, 1);
		goodAdater.fromWhat("data");
		goodAdater.setTargetView(cartBootmView.getCartImageView());
		goodListV.setAdapter(goodAdater);
		goodListContentV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = goodAdater.getTItem(position);
				Long goodId = JSONUtil.getLong(jo, "area_id");
				Good good = new Good();
				good.setGoodId(goodId);
				CommodityDetailDialog dialog = new CommodityDetailDialog(self,
						good);
				dialog.show();

			}
		});
		goodListV.setAdapter(goodAdater);
	}

}
