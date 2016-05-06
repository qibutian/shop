package com.means.shopping.activity.main;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

public class TiXianActivity extends ShopBaseActivity {

	EditText moneyEt, cardidEt, nameEt;
	Button submitBtn;

	String money;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdraw_card);
	}

	@Override
	public void initView() {
		setTitle("提现");
		moneyEt = (EditText) findViewById(R.id.money);
		cardidEt = (EditText) findViewById(R.id.cardid);
		nameEt = (EditText) findViewById(R.id.name);
		submitBtn = (Button) findViewById(R.id.submit);

		submitBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submit();
			}
		});

		money = getIntent().getStringExtra("money");
		moneyEt.setText(money);
	}

	private void submit() {
		String moneyStr = moneyEt.getText().toString();
		if (TextUtils.isEmpty(moneyStr)) {
			showToast("请输入提现金额");
			return;
		}
		int money = Integer.valueOf(moneyStr);
		String cardid = cardidEt.getText().toString();
		String name = nameEt.getText().toString();

		if (money <= 0) {
			showToast("请输入大于0的金额");
			return;
		}

		if (TextUtils.isEmpty(cardid)) {
			showToast("请输入卡号");
			return;
		}

		if (TextUtils.isEmpty(name)) {
			showToast("请输入姓名");
			return;
		}

		DhNet net = new DhNet(API.tixian);
		net.addParam("name", name);
		net.addParam("num", cardid);
		net.addParam("amount", money);
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					showToast("申请已经提交，请耐心等待2-5工作日");
					Intent it = new Intent(self, MainActivity.class);
					it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(it);
					finish();
				}
			}
		});

	}

}
