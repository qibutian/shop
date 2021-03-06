package com.means.shopping.activity.pay;

import java.math.BigDecimal;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.DhUtil;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.market.RechargeActivity;
import com.means.shopping.activity.my.CanUserRedPacket;
import com.means.shopping.activity.my.ConsigneeAddressActivity;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.ReChargeEB;
import com.means.shopping.utils.Arith;
import com.means.shopping.views.CartBottomView;

import de.greenrobot.event.EventBus;

/**
 * 付款页
 * 
 * @author Administrator
 * 
 */
public class PaymentActivity extends ShopBaseActivity implements
		OnClickListener {
	private int mWindoWidth;

	private LinearLayout foodslayoutLl;

	private TextView nameT, addressT;

	// 地址选择点击区域
	View address_layoutV;

	private final int ADDRESS_CODE = 1;

	private final int REDPACKET_CODE = 2;

	JSONArray walletjsa;

	double blance;

	CartBottomView bottom_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
	}

	@Override
	public void initView() {
		EventBus.getDefault().register(this);
		getWindowsWidth();
		setTitle("订单");
		bottom_view = (CartBottomView) findViewById(R.id.bottom_view);
		bottom_view.setType(1);
		foodslayoutLl = (LinearLayout) findViewById(R.id.foodslayout);
		address_layoutV = findViewById(R.id.address_layout);
		nameT = (TextView) findViewById(R.id.name);
		addressT = (TextView) findViewById(R.id.address);

		address_layoutV.setOnClickListener(this);

		findViewById(R.id.hongbao_layout).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (walletjsa != null && walletjsa.length() != 0) {
							Intent it = new Intent(self, CanUserRedPacket.class);
							it.putExtra("data", walletjsa.toString());
							startActivityForResult(it, REDPACKET_CODE);
						}
					}
				});

		findViewById(R.id.recharge).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(self, RechargeActivity.class);
				startActivity(it);
			}
		});

		getAddress();
		getData();
	}

	private void getWindowsWidth() {
		WindowManager wm = this.getWindowManager();
		mWindoWidth = wm.getDefaultDisplay().getWidth();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private void setFoodsImgs(JSONArray jsa) {
		ViewUtil.bindView(findViewById(R.id.count_des), "共" + jsa.length()
				+ "份");
		int with = DhUtil.dip2px(self,
				(DhUtil.px2dip(self, mWindoWidth) - 60) / 7);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(with, with);
		lp.setMargins(0, 0, DhUtil.dip2px(self, 10), 0);
		for (int i = 0; i < jsa.length(); i++) {
			JSONObject jo = JSONUtil.getJSONObjectAt(jsa, i);
			ImageView img = new ImageView(self);

			img.setLayoutParams(lp);
			img.setScaleType(ScaleType.CENTER_CROP);
			if (i == 6) {
				img.setImageResource(R.drawable.icon_more);
				img.setScaleType(ScaleType.FIT_XY);
				foodslayoutLl.addView(img);
				img.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showToast("敬请期待");
					}
				});
				break;
			}
			ViewUtil.bindNetImage(img, JSONUtil.getString(jo, "pic"), "default");

			// img.setImageResource(imgs[i]);
			foodslayoutLl.addView(img);
		}

	}

	private void getAddress() {
		DhNet net = new DhNet(API.address);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					if (TextUtils.isEmpty(JSONUtil.getString(jo, "lxname"))) {
						addressT.setText("请填写地址");
					} else {
						nameT.setText(JSONUtil.getString(jo, "lxname"));
						addressT.setText(JSONUtil.getString(jo, "lxaddress"));
					}
				}

			}
		});
	}

	public void getData() {
		DhNet net = new DhNet(API.preorder);
		net.doGet(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo = response.jSON();
					JSONArray jsa = JSONUtil.getJSONArray(jo, "list");
					walletjsa = JSONUtil.getJSONArray(jo, "wallet");
					if (walletjsa == null || walletjsa.length() == 0) {
						ViewUtil.bindView(findViewById(R.id.redpacket_des),
								"没有可用红包");
					}
					blance = JSONUtil.getDouble(jo, "balance");
					bottom_view.setYuE(blance);
					if (blance > JSONUtil.getDouble(jo, "price")) {
						ViewUtil.bindView(findViewById(R.id.blance_des), "当前余额"
								+ blance);
					} else {
						ViewUtil.bindView(findViewById(R.id.blance_des), "当前余额"
								+ blance);
					}

					setFoodsImgs(jsa);
				}

			}
		});

	}

	public void onEventMainThread(ReChargeEB reChargeEB) {
		blance = Arith.add(blance, reChargeEB.getMoney());
		bottom_view.setYuE(blance);
		ViewUtil.bindView(findViewById(R.id.blance_des), "当前余额" + blance);
	}

	@Override
	public void onClick(View v) {
		Intent it;

		switch (v.getId()) {
		case R.id.address_layout:
			it = new Intent(self, ConsigneeAddressActivity.class);
			startActivityForResult(it, ADDRESS_CODE);
			break;

		default:
			break;
		}
		// TODO Auto-generated method stub
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == RESULT_OK) {
			if (arg0 == ADDRESS_CODE) {
				nameT.setText(arg2.getStringExtra("lxname"));
				addressT.setText(arg2.getStringExtra("lxaddress"));
			} else if (arg0 == REDPACKET_CODE) {
				String data = arg2.getStringExtra("jo");
				JSONObject redjo;
				try {
					redjo = new JSONObject(data);
					ViewUtil.bindView(findViewById(R.id.redpacket_money),
							JSONUtil.getString(redjo, "amount"));
					bottom_view.setRedPackData(redjo);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
