package com.means.shopping.views;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.bean.CartBottomNumEB;
import com.means.shopping.bean.Good;
import com.means.shopping.manage.UserInfoManage;
import com.means.shopping.manage.UserInfoManage.LoginCallBack;

import de.greenrobot.event.EventBus;

public class CartView extends LinearLayout {
	Context mContext;

	TextView cartNumT;

	OnCartViewClickListener onCartViewClickListener;

	ImageView minusI;

	ImageView addI;

	Good mGood;

	// 0代表超市,夜市,1代表首页页面
	int cartViewType = 1;

	public boolean isadd = false;

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
					int count = mGood.getCount() - 1;
					isadd = false;
					changeGoodCount(mContext, mGood.getGoodId(), count,
							mGood.getGoodType());
				}

			}
		});

		addI = (ImageView) findViewById(R.id.add);
		addI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isadd = true;
				if (mGood != null) {
					if (mGood.getCount() == 0) {
						addGood(mContext, mGood.getGoodId(), 1,
								mGood.getGoodType());
					} else {
						changeGoodCount(mContext, mGood.getGoodId(),
								mGood.getCount() + 1, mGood.getGoodType());
					}
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

		void onAddClick(int count, double price);

		void onMinusClick(int count, double price);
	}

	public void addGood(final Context context, final Long goodId,
			final int count, final int type) {
		UserInfoManage.getInstance().checkLogin((Activity) context,
				new LoginCallBack() {

					@Override
					public void onisLogin() {
						DhNet net = new DhNet(API.addCart);
						net.addParam("goodsid", goodId);
						net.addParam("count", count);
						net.addParam("type", type);
						net.doPostInDialog(new NetTask(context) {

							@Override
							public void doInUI(Response response,
									Integer transfer) {
								if (response.isSuccess()) {
									JSONObject jo = response.jSONFromData();
									mGood.setCount(JSONUtil.getInt(jo, "count"));

									if (cartViewType == 1) {

										CartBottomNumEB cartBottomNumEB = new CartBottomNumEB();
										cartBottomNumEB.setCount(mGood
												.getCount());
										cartBottomNumEB.setPrice(JSONUtil
												.getDouble(jo, "price"));
										EventBus.getDefault().post(
												cartBottomNumEB);
									}

									if (onCartViewClickListener != null) {
										onCartViewClickListener.onAddClick(
												mGood.getCount(),
												JSONUtil.getDouble(jo, "price"));
									}
								}
							}
						});
					}

					@Override
					public void onLoginFail() {
					}
					// TODO Auto-generated method stub

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
					JSONObject jo = response.jSONFromData();
					mGood.setCount(JSONUtil.getInt(jo, "count"));

					if (cartViewType == 1) {

						CartBottomNumEB cartBottomNumEB = new CartBottomNumEB();
						cartBottomNumEB.setCount(mGood.getCount());
						cartBottomNumEB.setPrice(JSONUtil
								.getDouble(jo, "price"));
						EventBus.getDefault().post(cartBottomNumEB);
					}
					if (onCartViewClickListener != null) {

						if (isadd) {
							onCartViewClickListener.onAddClick(
									mGood.getCount(),
									JSONUtil.getDouble(jo, "price"));
						} else {
							onCartViewClickListener.onMinusClick(
									mGood.getCount(),
									JSONUtil.getDouble(jo, "price"));
						}
					}
				}
			}
		});
	}

}
