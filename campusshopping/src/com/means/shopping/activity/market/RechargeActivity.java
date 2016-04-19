package com.means.shopping.activity.market;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

public class RechargeActivity extends ShopBaseActivity {

	EditText moneyE;

	LinearLayout groupL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
	}

	@Override
	public void initView() {
		setTitle("充值");
		groupL = (LinearLayout) findViewById(R.id.group);
		moneyE = (EditText) findViewById(R.id.money);
		moneyE.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!TextUtils.isEmpty(moneyE.getText().toString())
						&& Integer.parseInt(moneyE.getText().toString()) != 0) {
					cleanCheck(-1);
				}

			}
		});

		findViewById(R.id.chongzhi).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chongzhi();
			}
		});

		initGroup();
	}

	private void chongzhi() {

		CheckBox checkBox = getChecked();
		String money;
		if (checkBox != null) {
			money = checkBox.getTag().toString();
		} else {
			money = moneyE.getText().toString();
		}

		if (TextUtils.isEmpty(money)) {
			showToast("请选择或者输入充值金额!");
			return;
		}

		if (Integer.parseInt(money) == 0) {
			showToast("充值金额不能为0元!");
			return;
		}

		DhNet net = new DhNet(API.chongzhi);
		net.addParam("amount", money);
		net.doPostInDialog("充值中...", new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {

				}

			}
		});
	}

	private void initGroup() {
		for (int i = 0; i < groupL.getChildCount(); i++) {
			final int index = i;
			CheckBox check = (CheckBox) groupL.getChildAt(i);
			check.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						cleanCheck(index);
						moneyE.setText("");
					}
				}
			});
		}
	}

	private void cleanCheck(int currentIndex) {
		for (int i = 0; i < groupL.getChildCount(); i++) {
			if (i != currentIndex) {
				CheckBox check = (CheckBox) groupL.getChildAt(i);
				check.setChecked(false);
			}
		}
	}

	private CheckBox getChecked() {
		for (int i = 0; i < groupL.getChildCount(); i++) {
			CheckBox check = (CheckBox) groupL.getChildAt(i);
			if (check.isChecked()) {
				return check;
			}
		}
		return null;
	}

}
