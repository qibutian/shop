package com.means.shopping.activity.pay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.DhUtil;
import net.duohuo.dhroid.util.ViewUtil;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.means.shopping.R;
import com.means.shopping.activity.my.ConsigneeAddressActivity;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

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

	// 地址选择点击区域
	View address_layoutV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
	}

	@Override
	public void initView() {
		getWindowsWidth();
		setTitle("订单");
		foodslayoutLl = (LinearLayout) findViewById(R.id.foodslayout);
		address_layoutV = findViewById(R.id.address_layout);
		address_layoutV.setOnClickListener(this);
		String data = getIntent().getStringExtra("data");
		try {
			JSONArray jsa = new JSONArray(data);
			setFoodsImgs(jsa);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			Log.d("img---------", i + 1 + "");

		}

	}

	private void getAddress() {
		// DhNet net =new DhNet(API.preorder);
		// net.addParam("", value)
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.address_layout:
			it = new Intent(self, ConsigneeAddressActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
		// TODO Auto-generated method stub
	}
}
