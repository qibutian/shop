package com.means.shopping.views;

import com.means.shopping.R;
import com.means.shopping.bean.Cart;
import com.means.shopping.bean.Good;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CartView extends LinearLayout {
	Context mContext;

	TextView cartNumT;

	OnCartViewClickListener onCartViewClickListener;

	Cart cart;

	Good good;

	ImageView minusI;

	ImageView addI;

	public CartView(Context context) {
		super(context);
	}

	public CartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	private void initView() {
		cart = Cart.getInstance();
		LayoutInflater.from(mContext).inflate(R.layout.include_cart_view, this);
		cartNumT = (TextView) findViewById(R.id.cart_num);
		minusI = (ImageView) findViewById(R.id.minus);
		minusI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (good != null) {
					cart.reduceGood(good.getId());
				}

				if (onCartViewClickListener != null) {
					onCartViewClickListener.onClick();
				}
			}
		});

		addI = (ImageView) findViewById(R.id.add);
		addI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (good != null) {
					cart.getOrCreateGood(good);
				}

				if (onCartViewClickListener != null) {
					onCartViewClickListener.onClick();
				}
			}
		});
	}

	public TextView getCartNumTextView() {
		return cartNumT;
	}

	public void setCartNumTextView() {
		cartNumT.setVisibility(good.getCount() >= 1 ? View.VISIBLE : View.GONE);
		minusI.setVisibility(good.getCount() >= 1 ? View.VISIBLE : View.GONE);
		cartNumT.setText(good.getCount() + "");
	}

	public void setGood(Good good) {
		this.good = good;
	}

	public OnCartViewClickListener getOnCartViewClickListener() {
		return onCartViewClickListener;
	}

	public void setOnCartViewClickListener(
			OnCartViewClickListener onCartViewClickListener) {
		this.onCartViewClickListener = onCartViewClickListener;
	}

	public interface OnCartViewClickListener {

		void onClick();
	}
}
