package com.means.shopping.activity.cat;

import net.duohuo.dhroid.net.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.Good;
import com.means.shopping.views.CartBottomView;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.CommodityDetailDialog;
import com.means.shopping.views.dialog.CommodityDetailDialog.OnResultListener;
import com.means.shopping.views.dialog.SelectTypeDialog;

public class CatDetailActivity extends ShopBaseActivity implements
		OnClickListener {
	RefreshListViewAndMore goodListV;

	HomePageAdapter goodAdater;
	ListView goodListContentV;
	CartBottomView cartBootmView;

	String id;

	ImageView priceI, countI;

	View priceV, countV;

	EditText contentE;

	TextView typeName;

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

		priceV = findViewById(R.id.price_tab);
		priceV.setOnClickListener(this);
		countV = findViewById(R.id.count_tab);
		countV.setOnClickListener(this);

		priceI = (ImageView) findViewById(R.id.img_price);
		countI = (ImageView) findViewById(R.id.img_count);

		goodListV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		goodListContentV = goodListV.getListView();
		String type = getIntent().getStringExtra("type");
		setAdapter(type);
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
				good.setGoodType(JSONUtil.getInt(jo, "carttype"));
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
		contentE = (EditText) findViewById(R.id.content);
		findViewById(R.id.search).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goodAdater.addparam("keywords", contentE.getText().toString());
				goodAdater.refreshDialog();
			}
		});
		typeName = (TextView) findViewById(R.id.type_name);
		typeName.setText(type);
		typeName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SelectTypeDialog dialog = new SelectTypeDialog(self);
				dialog.setOnResultListener(new SelectTypeDialog.OnResultListener() {

					@Override
					public void onResult(String type) {
						contentE.setText("");
						typeName.setText(type);
						setAdapter(type);
					}
				});
				dialog.show();
			}
		});
	}

	private void setAdapter(String type) {
		if (type.equals("商品")) {
			goodAdater = new HomePageAdapter(API.shop_contentlist, self,
					R.layout.item_home_list, 2);
			goodAdater.addparam("catid", id);
			// goodAdater.addparam("ordercount", "asc");
		} else if (type.equals("超市")) {
			goodAdater = new HomePageAdapter(API.marketGoodList, self,
					R.layout.item_home_list, 1);

		} else {
			goodAdater = new HomePageAdapter(API.nightGoodList, self,
					R.layout.item_home_list, 4);
		}
		goodAdater.addparam("price", "asc");
		// goodAdater.addparam("ordercount", "asc");
		goodAdater.fromWhat("list");
		goodAdater.setTargetView(cartBootmView.getCartImageView());
		goodListV.setAdapter(goodAdater);
		goodAdater.refreshDialog();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.count_tab:

			if (countI.getTag().toString().equals("0")) {
				countI.setImageResource(R.drawable.paixu_down);
				countI.setTag("1");
				goodAdater.addparam("ordercount", "desc");
				goodAdater.addparam("price", "");
			} else if (countI.getTag().toString().equals("1")) {
				countI.setImageResource(R.drawable.paixu_up);
				countI.setTag("0");
				goodAdater.addparam("ordercount", "asc");
				goodAdater.addparam("price", "");
			}
			goodAdater.refreshDialog();
			break;
		case R.id.price_tab:
			if (priceI.getTag().toString().equals("0")) {
				priceI.setImageResource(R.drawable.paixu_down);
				priceI.setTag("1");
				goodAdater.addparam("price", "desc");
				goodAdater.addparam("ordercount", "");
			} else if (countI.getTag().toString().equals("1")) {
				priceI.setImageResource(R.drawable.paixu_up);
				priceI.setTag("0");
				goodAdater.addparam("price", "asc");
				goodAdater.addparam("ordercount", "");
			}
			goodAdater.refreshDialog();
			break;
		default:
			break;
		}

	}

}
