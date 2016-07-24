package com.means.shopping.views.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.views.BaseAlertDialog;
import com.means.shopping.views.dialog.DelectDialog.OnDelectResultListener;

public class SelectTypeDialog extends BaseAlertDialog {

	Context mContext;

	OnResultListener onResultListener;

	TextView shangpinT, marketT, nightT;

	public SelectTypeDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_select_type);
		initView();
	}

	private void initView() {

		findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		shangpinT = (TextView) findViewById(R.id.shop);
		shangpinT.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (onResultListener != null) {
					onResultListener.onResult("商品");
				}
				dismiss();
			}
		});

		marketT = (TextView) findViewById(R.id.market);
		marketT.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (onResultListener != null) {
					onResultListener.onResult("超市");
				}
				dismiss();
			}
		});

		nightT = (TextView) findViewById(R.id.night);
		nightT.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (onResultListener != null) {
					onResultListener.onResult("培训收费");
				}
				dismiss();
			}
		});
	}

	public interface OnResultListener {
		void onResult(String type);
	}

	public OnResultListener getOnResultListener() {
		return onResultListener;
	}

	public void setOnResultListener(OnResultListener onResultListener) {
		this.onResultListener = onResultListener;
	}

}
