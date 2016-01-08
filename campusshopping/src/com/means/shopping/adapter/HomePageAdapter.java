package com.means.shopping.adapter;

import org.json.JSONObject;

import com.means.shopping.R;
import com.means.shopping.bean.Cart;
import com.means.shopping.bean.Good;
import com.means.shopping.bean.PriceEB;
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

public class HomePageAdapter extends NetJSONAdapter {

	Cart cart;
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

		JSONObject jo = getTItem(position);
		Long goodId = JSONUtil.getLong(jo, "area_id");

		Good good = new Good();
		good.setGoodId(goodId);
		final CartView cartView = (CartView) convertView
				.findViewById(R.id.cartView);
		cartView.setGood(good);
		cartView.setCartNumTextView();
		cartView.setOnCartViewClickListener(new OnCartViewClickListener() {

			@Override
			public void onMinusClick() {
				notifyDataSetChanged();
			}

			@Override
			public void onAddClick() {
				notifyDataSetChanged();
				if (type != 0) {
					int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
					View addI = cartView.findViewById(R.id.add);
					addI.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
					ImageView buyImg = new ImageView(mContext);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
					buyImg.setImageResource(R.drawable.sign);// 设置buyImg的图片
					CartAnimUtil anim = new CartAnimUtil((Activity) mContext);
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
