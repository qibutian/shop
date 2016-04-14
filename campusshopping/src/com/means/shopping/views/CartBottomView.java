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
		DhNet net = new DhNet(API.addorder);
		net.doPost(new NetTask(mContext) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo = response.jSON();
					JSONObject data = response.jSONFromData();
					String orderid = JSONUtil.getString(jo, "id");
					String price = JSONUtil.getString(data, "orderprice");

					if (yueC.isChecked()) {
						payByYue(orderid, price);
					} else {
						payZhifuBao(orderid, price);
					}
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
					pay(response.jSONFromData());
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

	private static final int SDK_PAY_FLAG = 1;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
					((Activity) mContext).finish();
					Intent it = new Intent(mContext, MainActivity.class);
					it.putExtra("type", "pay");
					it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mContext.startActivity(it);
					;

				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT)
								.show();
						Intent it = new Intent(mContext, MainActivity.class);
						it.putExtra("type", "pay");
						it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						mContext.startActivity(it);
						;
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT)
								.show();
						Intent it = new Intent(mContext, MainActivity.class);
						it.putExtra("type", "pay");
						it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						mContext.startActivity(it);
					}
				}
				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(JSONObject jo) {

		PayUtil payUtil = new PayUtil();

		String orderInfo = payUtil.getOrderInfo("测试的商品", "该测试商品的详细描述", "0.1",
				JSONUtil.getString(jo, "paycode"),
				JSONUtil.getString(jo, "callback"));

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = payUtil.sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ payUtil.getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity) mContext);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

}
