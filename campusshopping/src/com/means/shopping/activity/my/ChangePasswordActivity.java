package com.means.shopping.activity.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.utils.ShopUtils;

/**
 * 修改密码
 * 
 * @author Administrator
 * 
 */
public class ChangePasswordActivity extends ShopBaseActivity implements
		OnClickListener {

	private TextView sendSMST;
	private EditText telEt, codeEt, passwordEt;
	private Button submitBtn;
	private TimeCount time = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
	}

	@Override
	public void initView() {
		setTitle("修改登录密码");
		time = new TimeCount(60000, 1000);

		sendSMST = (TextView) findViewById(R.id.sendsms);
		submitBtn = (Button) findViewById(R.id.submit);
		telEt = (EditText) findViewById(R.id.tel);
		codeEt = (EditText) findViewById(R.id.code);
		passwordEt = (EditText) findViewById(R.id.password);

		sendSMST.setOnClickListener(this);
		submitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendsms:
			getMobileCode();
			break;
		case R.id.submit:
			submit();
			break;
		}
	}

	public void submit() {
		String tel = telEt.getText().toString();
		String password = passwordEt.getText().toString();
		String code = codeEt.getText().toString();
		if (TextUtils.isEmpty(tel)) {
			showToast("请输入手机号");
			return;
		}
		if (tel.length() != 11) {
			showToast("手机号格式不正确");
			return;
		}
		if (TextUtils.isEmpty(code)) {
			showToast("请输入验证码");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			showToast("请输入密码");
			return;
		}
		if (password.length() < 6 || password.length() > 15) {
			showToast("密码为6-15位字母和数字");
			return;
		}
		if (!ShopUtils.isLetter(password)) {
			showToast("密码中必须包含字母");
			return;
		}

		DhNet smsNet = new DhNet(API.resetpswdbyphone	);
		smsNet.addParam("pswd", password);
		smsNet.addParam("phone", tel);
		smsNet.addParam("mobilecode", code);
		smsNet.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					showToast("修改成功");
					finish();
				}
			}
		});

	}

	// 获取验证码
	private void getMobileCode() {
		String tel = telEt.getText().toString();
		if (TextUtils.isEmpty(tel)) {
			showToast("请输入手机号");
			return;
		}
		if (tel.length() != 11) {
			showToast("手机号格式不正确");
			return;
		}
		DhNet smsNet = new DhNet(API.mobilecode);
		smsNet.addParam("phone", tel);
		smsNet.addParam("type", "5"); // 1为改密
		smsNet.doGet(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					time.start();
				}
			}
		});
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			sendSMST.setText("重新发送");
			sendSMST.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			sendSMST.setEnabled(false);
			sendSMST.setText(millisUntilFinished / 1000 + "秒");

		}
	}
}
