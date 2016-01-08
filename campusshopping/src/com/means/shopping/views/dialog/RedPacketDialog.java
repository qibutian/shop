package com.means.shopping.views.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.means.shopping.R;
import com.means.shopping.bean.Good;
import com.means.shopping.views.BaseAlertDialog;
import com.means.shopping.views.CartView;
import com.means.shopping.views.CartView.OnCartViewClickListener;
import com.means.shopping.views.dialog.CommodityDetailDialog.OnCommodityResultListener;

/**
 * 获得红包弹出框
 * @author Administrator
 *
 */
public class RedPacketDialog extends BaseAlertDialog{
	Context mContext;

	OnRedPacketResultListener redPacketResultListener;

	public RedPacketDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_red_packet);
		initView();
	}

	private void initView() {

		findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

	}

	public interface OnRedPacketResultListener {
		void onResult(String year, String month, String day);
	}

	public OnRedPacketResultListener getOnRedPacketResultListener() {
		return redPacketResultListener;
	}

	public void setOnRedPacketResultListener(
			OnRedPacketResultListener redPacketResultListener) {
		this.redPacketResultListener = redPacketResultListener;
	}

}
