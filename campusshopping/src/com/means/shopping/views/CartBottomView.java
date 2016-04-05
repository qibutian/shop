package com.means.shopping.views;

import net.duohuo.dhroid.view.BadgeView;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.market.CartActivity;
import com.means.shopping.activity.pay.PaymentActivity;
import com.means.shopping.bean.CartBottomNumEB;
import com.means.shopping.bean.Good;
import com.means.shopping.views.CartView.OnCartViewClickListener;

import de.greenrobot.event.EventBus;

public class CartBottomView extends LinearLayout {
	Context mContext;

	TextView cartNumT;

	OnCartViewClickListener onCartViewClickListener;

	ImageView minusI;

	ImageView addI;

	Good mGood;

	Button payB;

	TextView desT, priceT;

	ImageView cartI;

	View bradeV;

	BadgeView badgeT;

	public CartBottomView(Context context) {
		super(context);
	}

	public CartBottomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		EventBus.getDefault().register(this);
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(
				R.layout.include_cart_bottom_view, this);
		payB = (Button) findViewById(R.id.pay);
		payB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, PaymentActivity.class);
				mContext.startActivity(it);

			}
		});
		badgeT = (BadgeView) findViewById(R.id.badgeView);// 创建一个BadgeView对象，view为你需要显示提醒信息的控件
		badgeT.hide();
		desT = (TextView) findViewById(R.id.cart_des);
		priceT = (TextView) findViewById(R.id.price);
		cartI = (ImageView) findViewById(R.id.cart);
		cartI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, CartActivity.class);
				mContext.startActivity(it);
			}
		});
		getData();
	}

	public void getData() {

	}

	public void setCartNum(CartBottomNumEB cartBottomNumEB) {
		int count = cartBottomNumEB.getCount();
	}

	public void setCartNum(int   count) {
		badgeT.setVisibility(count != 0 ? View.VISIBLE : View.GONE);
		payB.setVisibility(count != 0 ? View.VISIBLE : View.GONE);
		badgeT.setText(count + "");
	}

	public ImageView getCartImageView() {
		return cartI;
	}

	/**
	 * 设置购物车描述
	 */
	public void setCartDes(String des) {
		desT.setText(des);
	}

	public void onEventMainThread(CartBottomNumEB cartBottomNumEB) {
		setCartNum(cartBottomNumEB);
	}

}
