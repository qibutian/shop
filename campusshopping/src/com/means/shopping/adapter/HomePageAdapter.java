package com.means.shopping.adapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.means.shopping.R;
import com.means.shopping.bean.Cart;
import com.means.shopping.bean.Good;
import com.means.shopping.utils.CartAnimUtil;
import com.means.shopping.utils.CartAnimUtil.OnAnimationEndListener;
import com.means.shopping.views.CartView;
import com.means.shopping.views.CartView.OnCartViewClickListener;

import de.greenrobot.event.EventBus;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.DhUtil;
import net.duohuo.dhroid.util.ViewUtil;

public class HomePageAdapter extends NetJSONAdapter {

	Cart cart;

	// 1是超市,2是商城,3是购物车
	int type = 0;

	View targetV;

	public HomePageAdapter(String api, Context context, int mResource) {
		super(api, context, mResource);
		cart = Cart.getInstance();
	}

	public HomePageAdapter(String api, Context context, int mResource, int type) {
		super(api, context, mResource);
		cart = Cart.getInstance();
		this.type = type;
	}

	public void setTargetView(View targetV) {
		this.targetV = targetV;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_home_list, null);
		}

		final JSONObject jo = getTItem(position);
		ViewUtil.bindNetImage((ImageView) convertView.findViewById(R.id.pic),
				JSONUtil.getString(jo, "pic"), "default");

		ViewUtil.bindView(convertView.findViewById(R.id.name),
				JSONUtil.getString(jo, "title"));
		ViewUtil.bindView(convertView.findViewById(R.id.price),
				JSONUtil.getString(jo, "price"));
		ViewUtil.bindView(convertView.findViewById(R.id.des),
				JSONUtil.getString(jo, "stitle"));

		Good good = new Good();
		good.setGoodId(JSONUtil.getLong(jo, "id"));

		// 购物车那边
		if (type == 3) {
			good.setCount(JSONUtil.getInt(jo, "count"));
			good.setGoodType(JSONUtil.getInt(jo, "type"));
			// 商城
		} else if (type == 2) {
			good.setCount(JSONUtil.getInt(jo, "cartcount"));
			good.setGoodType(2);
		} else {
			good.setCount(JSONUtil.getInt(jo, "cartcount"));
			good.setGoodType(JSONUtil.getInt(jo, "carttype"));
		}

		final CartView cartView = (CartView) convertView
				.findViewById(R.id.cartView);
		cartView.setGood(good);
		cartView.setCartNumTextView();
		cartView.setOnCartViewClickListener(new OnCartViewClickListener() {

			@Override
			public void onMinusClick(int count, int cartcount, double price) {
				try {
					if (type == 3) {
						jo.put("count", JSONUtil.getInt(jo, "count") - 1);
					} else {
						jo.put("cartcount",
								JSONUtil.getInt(jo, "cartcount") - 1);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				notifyDataSetChanged();
			}

			@Override
			public void onAddClick(int count, int cartcount, double price) {
				try {

					if (type == 3) {
						jo.put("count", JSONUtil.getInt(jo, "count") + 1);
					} else {
						jo.put("cartcount",
								JSONUtil.getInt(jo, "cartcount") + 1);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				notifyDataSetChanged();
				if (type != 0) {
					int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
					View addI = cartView.findViewById(R.id.add);
					addI.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
					ImageView buyImg = new ImageView(mContext);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
					buyImg.setImageResource(R.drawable.sign);// 设置buyImg的图片
					CartAnimUtil anim = new CartAnimUtil((Activity) mContext,
							cartcount, price);
					anim.setAnim(buyImg, start_location, targetV);
				}
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
