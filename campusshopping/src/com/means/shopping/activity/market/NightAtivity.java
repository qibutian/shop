package com.means.shopping.activity.market;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.means.shopping.R;
import com.means.shopping.activity.cat.CatDetailActivity;
import com.means.shopping.adapter.CatAdapter;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.Good;
import com.means.shopping.views.CartBottomView;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.CommodityDetailDialog;

public class NightAtivity extends ShopBaseActivity {

	ListView catListV;

	CatAdapter catAdapter;

	RefreshListViewAndMore goodListV;

	HomePageAdapter goodAdater;

	ListView goodListContentV;

	CartBottomView cartBootmView;

	View catHeadV;

	// 每日爆款和猜你喜欢按钮
	View hotV, likeV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);
	}

	@Override
	public void initView() {
		setTitle("夜市");
		setRightAction("", R.drawable.search_icon, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, CatDetailActivity.class);
				it.putExtra("type", "夜市");
				startActivity(it);
			}
		});
		catHeadV = LayoutInflater.from(self).inflate(R.layout.head_market_cat,
				null);
		hotV = catHeadV.findViewById(R.id.hot);
		hotV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goodAdater.addparam("catid", "");
				goodAdater.addparam("type", 1);
				goodAdater.refreshDialog();
				setBgColor(-2);
			}
		});
		likeV = catHeadV.findViewById(R.id.like);
		likeV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setBgColor(-1);
				goodAdater.addparam("catid", "");
				goodAdater.addparam("type", 2);
				goodAdater.refreshDialog();
			}
		});
		catListV = (ListView) findViewById(R.id.listview_normal);
		catListV.addHeaderView(catHeadV);
		cartBootmView = (CartBottomView) findViewById(R.id.cartBootmView);
		// cartBootmView.setCartNum();
		catAdapter = new CatAdapter(self);
		catListV.setAdapter(catAdapter);
		catListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				setBgColor(position - 1);
				JSONObject jo = catAdapter.getItem(position);
				String catid = JSONUtil.getString(jo, "id");
				goodAdater.addparam("catid", catid);
				goodAdater.addparam("type", "");
				goodAdater.refreshDialog();
			}
		});

		goodListV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		goodListContentV = goodListV.getListView();
		goodAdater = new HomePageAdapter(API.nightGoodList, self,
				R.layout.item_home_list, 4);
		goodAdater.fromWhat("list");
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
						good, jo);
				dialog.show();

			}
		});
		getCatData();
	}

	private void getCatData() {
		DhNet net = new DhNet(API.nightCat);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONArray jsa = response.jSONArrayFrom("list");
					catAdapter.setData(jsa);
				}

			}
		});
	}

	private void setBgColor(int position) {

		if (position == -2) {
			likeV.setBackgroundColor(getResources().getColor(
					R.color.campus_grey));
			hotV.setBackgroundColor(getResources().getColor(R.color.white));
			catAdapter.select(position);
		} else if (position == -1) {
			likeV.setBackgroundColor(getResources().getColor(R.color.white));
			hotV.setBackgroundColor(getResources()
					.getColor(R.color.campus_grey));
			catAdapter.select(position);
		} else {
			likeV.setBackgroundColor(getResources().getColor(
					R.color.campus_grey));
			hotV.setBackgroundColor(getResources()
					.getColor(R.color.campus_grey));
			catAdapter.select(position);
		}
	}

}
