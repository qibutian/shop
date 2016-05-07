package com.means.shopping.activity.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

public class AnhaoActivity extends ShopBaseActivity {

	EditText codeE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anhao);
	}

	@Override
	public void initView() {
		setTitle("暗号");
		codeE = (EditText) findViewById(R.id.code);
		findViewById(R.id.submit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				submit();
			}
		});
	}

	private void submit() {
		if (TextUtils.isEmpty(codeE.getText().toString())) {
			showToast("暗号不能为空!");
			return;
		}
		DhNet net = new DhNet(API.sharesure);
		net.addParam("code", codeE.getText().toString());
		net.doPostInDialog("提交中...", new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					showToast("提交成功!");
					finish();
				}

			}
		});
	}

}
