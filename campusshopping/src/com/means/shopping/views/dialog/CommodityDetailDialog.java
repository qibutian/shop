package com.means.shopping.views.dialog;

import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.means.shopping.R;
import com.means.shopping.bean.Good;
import com.means.shopping.bean.GoodEB;
import com.means.shopping.utils.CartAnimUtil;
import com.means.shopping.views.BaseAlertDialog;
import com.means.shopping.views.CartView;
import com.means.shopping.views.CartView.OnCartViewClickListener;

import de.greenrobot.event.EventBus;

/**
 * 商品详情
 * 
 * @author Administrator
 * 
 */
public class CommodityDetailDialog extends BaseAlertDialog {
	Context mContext;

	OnResultListener commodityResultListener;

	CartView cartView;

	Good good;

	JSONObject jo;

	public CommodityDetailDialog(Context context, Good good, JSONObject jo) {
		super(context);
		this.mContext = context;
		this.good = good;
		this.jo = jo;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_commodity_detail);
		initView();
	}

	private void initView() {
		cartView = (CartView) findViewById(R.id.cartview);
		cartView.setThemeWhite();
		cartView.setGood(good);
		cartView.setCartNumTextView();
		cartView.setOnCartViewClickListener(new OnCartViewClickListener() {

			@Override
			public void onMinusClick(int count, int cartcount, double price) {
				cartView.setCartNumTextView();
				if (commodityResultListener != null) {
					commodityResultListener.onResult(count);
				}
			}

			@Override
			public void onAddClick(int count, int cartcount, double price) {
				cartView.setCartNumTextView();
				if (commodityResultListener != null) {
					commodityResultListener.onResult(count);
				}
			}
		});

		ViewUtil.bindNetImage((ImageView) findViewById(R.id.pic),
				JSONUtil.getString(jo, "pic"), "default");
		ViewUtil.bindView(findViewById(R.id.title),
				JSONUtil.getString(jo, "title"));
		ViewUtil.bindView(findViewById(R.id.price),
				JSONUtil.getString(jo, "price"));
		ViewUtil.bindView(findViewById(R.id.des),
				JSONUtil.getString(jo, "stitle"));

		findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

	}

	public interface OnResultListener {
		void onResult(int cartcount);
	}

	public OnResultListener getOnResultListener() {

		return commodityResultListener;
	}

	public void setOnResultListener(OnResultListener commodityResultListener) {
		this.commodityResultListener = commodityResultListener;
	}

}
