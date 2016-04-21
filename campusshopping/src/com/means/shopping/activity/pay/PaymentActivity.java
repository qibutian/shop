package com.means.shopping.activity.pay;

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
import com.means.shopping.activity.my.ConsigneeAddressActivity;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.views.CartBottomView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
	}

	@Override
	public void initView() {
		getWindowsWidth();
		setTitle("订单");
		CartBottomView bottom_view = (CartBottomView) findViewById(R.id.bottom_view);
		bottom_view.setType(1);
		foodslayoutLl = (LinearLayout) findViewById(R.id.foodslayout);
		address_layoutV = findViewById(R.id.address_layout);
		nameT = (TextView) findViewById(R.id.name);
		addressT = (TextView) findViewById(R.id.address);

		address_layoutV.setOnClickListener(this);

		getAddress();
		getData();
		// String data = getIntent().getStringExtra("data");
		// try {
		// JSONArray jsa = new JSONArray(data);
		// setFoodsImgs(jsa);
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void getWindowsWidth() {
		WindowManager wm = this.getWindowManager();
		mWindoWidth = wm.getDefaultDisplay().getWidth();
	}

	private void setFoodsImgs(JSONArray jsa) {
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
					setFoodsImgs(jsa);
				}

			}
		});

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
			}
		}
	}

}
