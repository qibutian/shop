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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

public class RechargeActivity extends ShopBaseActivity {

	EditText moneyE;

	RadioGroup groupR;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
	}

	@Override
	public void initView() {
		setTitle("充值");
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
					System.out.println("text");
					((RadioButton) groupR.getChildAt(0)).setChecked(false);
					((RadioButton) groupR.getChildAt(1)).setChecked(false);
					((RadioButton) groupR.getChildAt(2)).setChecked(false);
				}

			}
		});

		groupR = (RadioGroup) findViewById(R.id.group);
		groupR.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				System.out.println("checkedId:" + checkedId);
				if (((RadioButton) groupR.findViewById(groupR
						.getCheckedRadioButtonId())).isChecked()) {
					System.out.println("group");
					moneyE.setText(null);
				}

				((RadioButton) groupR.findViewById(groupR
						.getCheckedRadioButtonId()))
						.setChecked(!((RadioButton) groupR.findViewById(groupR
								.getCheckedRadioButtonId())).isChecked());
			}
		});

		findViewById(R.id.chongzhi).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chongzhi();
			}
		});
	}

	private void chongzhi() {
		String money;
		if (groupR.getCheckedRadioButtonId() != -1) {
			RadioButton rb = (RadioButton) groupR.findViewById(groupR
					.getCheckedRadioButtonId());
			money = rb.getTag().toString();
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
		net.doPostInDialog("充值中...", new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {

				}

			}
		});
	}

}
