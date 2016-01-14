package com.means.shopping.activity.my;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.my.ChangePasswordActivity.TimeCount;
import com.means.shopping.base.ShopBaseFragmentActivity;

/**
 * 注册页面
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends ShopBaseFragmentActivity implements
		OnClickListener {
	private TextView sendSMST;

	private TimeCount time = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initView();
	}

	private void initView() {
		setTitle("注册");
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
