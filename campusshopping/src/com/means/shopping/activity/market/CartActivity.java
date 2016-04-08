package com.means.shopping.activity.market;

import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.pay.PaymentActivity;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.CartBottomNumEB;
import com.means.shopping.bean.Good;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.CommodityDetailDialog;

import de.greenrobot.event.EventBus;

public class CartActivity extends ShopBaseActivity {

	RefreshListViewAndMore goodListV;

	HomePageAdapter goodAdater;

	ListView goodListContentV;

	TextView priceT;

	JSONArray jsa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);

		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		setTitle("购物车");
		priceT = (TextView) findViewById(R.id.price);
		goodListV = (RefreshListViewAndMore) findViewById(R.id.my_listview);

		goodListContentV = goodListV.getListView();

		goodAdater = new HomePageAdapter(API.cartList, self,
				R.layout.item_home_list, 3);

		goodAdater.fromWhat("list");
		goodListV.setOnLoadSuccess(new RefreshListViewAndMore.OnLoadSuccess() {

			@Override
			public void loadSuccess(Response response) {

				if (!response.isCache) {
					JSONObject jo = response.jSON();
					jsa = response.jSONArrayFrom("list");
					priceT.setText(JSONUtil.getString(jo, "price"));
				}

			}

		});
		goodAdater.setTargetView(priceT);

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

		Button payB = (Button) findViewById(R.id.pay);
		payB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(self, PaymentActivity.class);
				it.putExtra("data", jsa.toString());
				startActivity(it);

			}
		});
	}

	public void onEventMainThread(CartBottomNumEB cartBottomNumEB) {
		ViewUtil.bindView(priceT, cartBottomNumEB.getPrice() + "");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
