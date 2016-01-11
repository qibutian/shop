package com.means.shopping.activity.market;

import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.view.BadgeView;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.adapter.CatAdapter;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.Cart;
import com.means.shopping.bean.Good;
import com.means.shopping.bean.PriceEB;
import com.means.shopping.views.CartBottomView;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.CommodityDetailDialog;

import de.greenrobot.event.EventBus;

public class MarketActivity extends ShopBaseActivity {

	ListView catListV;

	CatAdapter catAdapter;

	RefreshListViewAndMore goodListV;

	HomePageAdapter goodAdater;

	ListView goodListContentV;

	CartBottomView cartBootmView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);
	}

	@Override
	public void initView() {
		setTitle("超市");
		catListV = (ListView) findViewById(R.id.listview_normal);
		cartBootmView = (CartBottomView) findViewById(R.id.cartBootmView);
		cartBootmView.setCartNum();
		catAdapter = new CatAdapter(self);
		catListV.setAdapter(catAdapter);

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
