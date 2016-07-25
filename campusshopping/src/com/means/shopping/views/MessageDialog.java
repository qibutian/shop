package com.means.shopping.views;

import net.duohuo.dhroid.util.ViewUtil;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.means.shopping.R;
import com.means.shopping.views.dialog.DelectDialog.OnDelectResultListener;

public class MessageDialog extends BaseAlertDialog {

	Context mContext;

	OnDelectResultListener delectResultListener;

	String msg;
	String price;

	public MessageDialog(Context context, String msg, String price) {
		super(context);
		this.mContext = context;
		this.msg = msg;
		this.price = price;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_message);
		initView();
	}

	private void initView() {

		findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		ViewUtil.bindView(findViewById(R.id.des), msg);
		ViewUtil.bindView(findViewById(R.id.price), price);
		findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				delectResultListener.onResult();
				dismiss();
			}
		});

	}

	public interface OnDelectResultListener {
		void onResult();
	}

	public OnDelectResultListener getOnDelectResultListener() {
		return delectResultListener;
	}

	public void setOnDelectResultListener(
			OnDelectResultListener delectResultListener) {
		this.delectResultListener = delectResultListener;
	}

}
