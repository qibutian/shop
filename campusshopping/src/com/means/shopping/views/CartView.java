package com.means.shopping.views;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.bean.Cart;
import com.means.shopping.bean.Good;
import com.means.shopping.bean.PriceEB;

import de.greenrobot.event.EventBus;

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
		LayoutInflater.from(mContext).inflate(R.layout.include_cart_view, this);
		cartNumT = (TextView) findViewById(R.id.cart_num);
		minusI = (ImageView) findViewById(R.id.minus);
		minusI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mGood != null) {
					int count = mGood.getCount() == 1 ? 0 : 1;
					changeGoodCount(mContext, mGood.getGoodId(), count,
							mGood.getGoodType());
				}

			}
		});

		addI = (ImageView) findViewById(R.id.add);
		addI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mGood != null) {
					addGood(mContext, mGood.getGoodId(), 1, mGood.getGoodType());
				}

			}
		});
	}

	public TextView getCartNumTextView() {
		return cartNumT;
	}

	public void setCartNumTextView() {
		if (mGood != null) {
			cartNumT.setVisibility(mGood.getCount() >= 1 ? View.VISIBLE
					: View.GONE);
			minusI.setVisibility(mGood.getCount() >= 1 ? View.VISIBLE
					: View.INVISIBLE);
			cartNumT.setText(mGood.getCount() + "");
		} else {
			cartNumT.setVisibility(View.INVISIBLE);
			minusI.setVisibility(View.INVISIBLE);
		}
	}

	public void setThemeWhite() {
		cartNumT.setTextColor(getResources().getColor(R.color.white));
		minusI.setImageResource(R.drawable.icon_minus_white);
		addI.setImageResource(R.drawable.icon_add_white);
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

	public void addGood(Context context, Long goodId, int count, int type) {
		DhNet net = new DhNet(API.addCart);
		net.addParam("goodsid", goodId);
		net.addParam("count", count);
		net.addParam("type", type);
		net.doPostInDialog(new NetTask(context) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					mGood.setCount(mGood.getCount() + 1);
					if (onCartViewClickListener != null) {
						onCartViewClickListener.onAddClick();
					}
				}
			}
		});
	}

	public void changeGoodCount(Context context, Long goodId, int count,
			int type) {
		DhNet net = new DhNet(API.changeCartCount);
		net.addParam("goodsid", goodId);
		net.addParam("count", count);
		net.addParam("type", type);
		net.doPostInDialog(new NetTask(context) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					mGood.setCount(mGood.getCount() - 1);
					if (onCartViewClickListener != null) {
						onCartViewClickListener.onMinusClick();
					}
					EventBus.getDefault().post(new PriceEB());
				}
			}
		});
	}

}
