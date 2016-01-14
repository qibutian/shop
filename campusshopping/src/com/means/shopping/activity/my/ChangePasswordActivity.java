package com.means.shopping.activity.my;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;

/**
 * 修改密码
 * 
 * @author Administrator
 * 
 */
public class ChangePasswordActivity extends ShopBaseActivity implements
		OnClickListener {

	private TextView sendSMST;
	
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

		sendSMST.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendsms:
			time.start();
			break;
		}
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
