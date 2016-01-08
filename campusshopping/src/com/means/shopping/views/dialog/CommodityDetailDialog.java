package com.means.shopping.views.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.means.shopping.R;
import com.means.shopping.bean.Good;
import com.means.shopping.bean.PriceEB;
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

	OnCommodityResultListener commodityResultListener;

	CartView cartView;

	Good good;

	public CommodityDetailDialog(Context context, Good good) {
		super(context);
		this.mContext = context;
		this.good = good;
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
			public void onMinusClick() {
				cartView.setCartNumTextView();
			}

			@Override
			public void onAddClick() {
				cartView.setCartNumTextView();
				EventBus.getDefault().post(new PriceEB());
				// TODO Auto-generated method stub

			}

		});

		findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

	}

	public interface OnCommodityResultListener {
		void onResult(String year, String month, String day);
	}

	public OnCommodityResultListener getOnCommodityResultListener() {

		return commodityResultListener;
	}

	public void setOnCommodityResultListener(
			OnCommodityResultListener commodityResultListener) {
		this.commodityResultListener = commodityResultListener;
	}

}
