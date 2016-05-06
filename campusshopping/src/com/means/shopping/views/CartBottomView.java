package com.means.shopping.views;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.view.BadgeView;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.means.shopping.R;
import com.means.shopping.activity.main.MainActivity;
import com.means.shopping.activity.market.CartActivity;
import com.means.shopping.activity.pay.PayResult;
import com.means.shopping.activity.pay.PayUtil;
import com.means.shopping.activity.pay.PaymentActivity;
import com.means.shopping.api.API;
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

	JSONArray jsa;

	// 1代表订单确认页面
	int type = 0;

	CheckBox zhifubaoC, yueC;

	double yue;

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
				if (type == 0) {
					Intent it = new Intent(mContext, PaymentActivity.class);
					it.putExtra("data", jsa.toString());
					mContext.startActivity(it);
				} else {
					addOrder();
					// Intent it = new Intent(mContext, PaymentActivity.class);
					// it.putExtra("data", jsa.toString());
					// mContext.startActivity(it);
				}

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

	public void setYuE(double money) {
		this.yue = money;

	}

	public void setType(int type) {
		this.type = type;
		if (type == 1) {
			zhifubaoC = (CheckBox) getRootView().findViewById(
					R.id.check_zhifubao);
			zhifubaoC.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {

					yueC.setChecked(!isChecked);

				}
			});
			yueC = (CheckBox) getRootView().findViewById(R.id.check_yue);
			yueC.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {

					zhifubaoC.setChecked(!isChecked);

				}
			});
		}
	}

	public void getData() {
		DhNet net = new DhNet(API.cartList);
		net.doGet(new NetTask(mContext) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo = response.jSON();
					jsa = JSONUtil.getJSONArray(jo, "list");
					priceT.setText(JSONUtil.getString(jo, "price"));
					setCartNum(JSONUtil.getInt(jo, "count"));
				}

			}
		});

	}

	// 下单
	private void addOrder() {

		TextView addressT = (TextView) getRootView().findViewById(R.id.name);

		if (TextUtils.isEmpty(addressT.getText().toString())) {
			IDialog dialog = IocContainer.getShare().get(IDialog.class);
			dialog.showToastShort(mContext, "请填写收货地址!");
			return;
		}

		DhNet net = new DhNet(API.addorder);
		net.addParam("walletid", "");
		net.addParam("is_balance", yueC.isChecked() ? 1 : 0);
		net.doPost(new NetTask(mContext) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo = response.jSON();
					JSONObject data = response.jSONFromData();
					String orderid = JSONUtil.getString(jo, "id");
					String price = JSONUtil.getString(data, "orderprice");

					if (yueC.isChecked()) {
						if (yue >= JSONUtil.getDouble(data, "orderprice")) {
							payByYue(orderid, price);
						} else {
							recharge(orderid,
									JSONUtil.getDouble(data, "orderprice")
											- yue);
						}
					} else {
						payZhifuBao(orderid, price);
					}
				}

			}
		});
	}

	// 充值
	private void recharge(String orderid, double price) {
		DhNet net = new DhNet(API.chongzhi);
		net.addParam("amount", price);
		net.addParam("orderid", orderid);
		net.doPostInDialog("余额不足,充值中...", new NetTask(mContext) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					PayUtil payUtil = new PayUtil(response.jSONFromData(),
							mContext, 0);
					payUtil.pay("小蚂蚁校园购物充值");
				}

			}
		});
	}

	// 余额支付
	private void payByYue(String orderid, String price) {
		DhNet net = new DhNet(API.payyue);
		net.addParam("orderid", orderid);
		net.addParam("payprice", price);
		net.doPost(new NetTask(mContext) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {

					IDialog dialog = IocContainer.getShare().get(IDialog.class);
					dialog.showToastShort(mContext, "支付成功");
					Intent it = new Intent(mContext, MainActivity.class);
					it.putExtra("type", "pay");
					it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mContext.startActivity(it);

				}

			}
		});
	}

	// 支付宝支付
	private void payZhifuBao(String orderid, String price) {
		DhNet net = new DhNet(API.pay_zhifubao);
		net.addParam("orderid", orderid);
		net.addParam("amount", price);
		net.doPost(new NetTask(mContext) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					PayUtil payUtil = new PayUtil(response.jSONFromData(),
							mContext, 0);
					payUtil.pay("小蚂蚁校园购物");
					// IDialog dialog =
					// IocContainer.getShare().get(IDialog.class);
					// dialog.showToastShort(mContext, "支付成功");

				}

			}
		});
	}

	public void setCartNum(CartBottomNumEB cartBottomNumEB) {
		int count = cartBottomNumEB.getCount();
		setCartNum(count);
		priceT.setText(cartBottomNumEB.getPrice() + "");
	}

	public void setCartNum(int count) {
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
