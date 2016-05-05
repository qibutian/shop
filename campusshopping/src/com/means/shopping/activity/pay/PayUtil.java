package com.means.shopping.activity.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.JSONUtil;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.means.shopping.activity.main.MainActivity;
import com.means.shopping.api.Constant;
import com.means.shopping.bean.ReChargeEB;

import de.greenrobot.event.EventBus;

public class PayUtil {

	/**
	 * create the order info. 创建订单信息
	 * 
	 */

	JSONObject jo;

	Context mContext;

	JSONObject keyJo;

	private static final int SDK_PAY_FLAG = 1;

	// 0代表付款,1代表充值
	int type = 0;

	public PayUtil(JSONObject jo, Context mContext, int type) {
		this.jo = jo;
		this.mContext = mContext;
		keyJo = JSONUtil.getJSONObject(jo, "app");
		this.type = type;
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(String title) {

		// PayUtil payUtil = new PayUtil(JSONUtil.getJSONObject(jo, "app"));

		String orderInfo = getOrderInfo(title, title,
				JSONUtil.getString(jo, "amount"),
				JSONUtil.getString(jo, "paycode"),
				JSONUtil.getString(jo, "callback"));

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo);
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
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity) mContext);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = 1;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	public String getOrderInfo(String subject, String body, String price,
			String oriderid, String callbackUrl) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\""
				+ JSONUtil.getString(keyJo, "PARTNER") + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + JSONUtil.getString(keyJo, "SELLER")
				+ "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + oriderid + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + callbackUrl + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils
				.sign(content, JSONUtil.getString(keyJo, "RSA_PRIVATE"));
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

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
					IocContainer.getShare().get(IDialog.class)
							.showToastShort(mContext, "支付成功");

					((Activity) mContext).finish();
					if (type == 0) {
						Intent it = new Intent(mContext, MainActivity.class);
						it.putExtra("type", "pay");
						it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						mContext.startActivity(it);
					} else {
						EventBus.getDefault().post(new ReChargeEB());
					}

				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						IocContainer.getShare().get(IDialog.class)
								.showToastShort(mContext, "支付结果确认中");
						if (type == 0) {
							Intent it = new Intent(mContext, MainActivity.class);
							it.putExtra("type", "pay");
							it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							mContext.startActivity(it);
						}
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						IocContainer.getShare().get(IDialog.class)
								.showToastShort(mContext, "支付失败");
						if (type == 0) {
							Intent it = new Intent(mContext, MainActivity.class);
							it.putExtra("type", "pay");
							it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							mContext.startActivity(it);
						}
					}
				}
				break;
			}
			default:
				break;
			}
		};
	};

}
