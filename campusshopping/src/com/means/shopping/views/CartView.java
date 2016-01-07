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

	ImageView minusI;

	ImageView addI;

	Good mGood;

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

				if (mGood != null) {
					cart.reduceGood(mGood.getGoodId());
				}

				if (onCartViewClickListener != null) {
					onCartViewClickListener.onMinusClick();
				}
			}
		});

		addI = (ImageView) findViewById(R.id.add);
		addI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mGood != null) {
					cart.getOrCreateGood(mGood.getGoodId());
				}

				if (onCartViewClickListener != null) {
					onCartViewClickListener.onAddClick();
				}
			}
		});
	}

	public TextView getCartNumTextView() {
		return cartNumT;
	}

	public void setCartNumTextView() {
		Good good = cart.getGood(mGood.getGoodId());
		if (good != null) {
			cartNumT.setVisibility(good.getCount() >= 1 ? View.VISIBLE
					: View.GONE);
			minusI.setVisibility(good.getCount() >= 1 ? View.VISIBLE
					: View.INVISIBLE);
			cartNumT.setText(good.getCount() + "");
		} else {
			cartNumT.setVisibility(View.INVISIBLE);
			minusI.setVisibility(View.INVISIBLE);
		}
	}

	public void setGood(Good good) {
		this.mGood = good;
	}

	public OnCartViewClickListener getOnCartViewClickListener() {
		return onCartViewClickListener;
	}

	public void setOnCartViewClickListener(
			OnCartViewClickListener onCartViewClickListener) {
		this.onCartViewClickListener = onCartViewClickListener;
	}

	public interface OnCartViewClickListener {

		void onAddClick();

		void onMinusClick();
	}
}
