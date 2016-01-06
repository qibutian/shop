package com.means.shopping.adapter;

import org.json.JSONObject;

import com.means.shopping.R;
import com.means.shopping.bean.Cart;
import com.means.shopping.bean.Good;
import com.means.shopping.views.CartView;
import com.means.shopping.views.CartView.OnCartViewClickListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.DhUtil;

public class HomePageAdapter extends NetJSONAdapter {

	Cart cart;
	int type = 0;

	public HomePageAdapter(String api, Context context, int mResource) {
		super(api, context, mResource);
		cart = Cart.getInstance();
	}

	public HomePageAdapter(String api, Context context, int mResource, int type) {
		super(api, context, mResource);
		cart = Cart.getInstance();
		this.type = type;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_home_list, null);
		}

		JSONObject jo = getTItem(position);
		Long goodId = JSONUtil.getLong(jo, "area_id");

		Good good = new Good();
		good.setGoodId(goodId);
		CartView cartView = (CartView) convertView.findViewById(R.id.cartView);
		cartView.setGood(good);
		cartView.setCartNumTextView();
		cartView.setOnCartViewClickListener(new OnCartViewClickListener() {

			@Override
			public void onClick() {
				notifyDataSetChanged();
			}
		});

		View lineV = convertView.findViewById(R.id.line);
		if (type != 0) {
			lineV.setLeft(DhUtil.dip2px(mContext, 14));
		}

		// TODO Auto-generated method stub
		return convertView;
	}
}
