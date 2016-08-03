package com.means.shopping.activity.my;

import net.duohuo.dhroid.net.JSONUtil;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;

public class AboutOneActivity extends ShopBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_one);
	}

	@Override
	public void initView() {
		setTitle("关于我们");

		findViewById(R.id.tel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ "18751505876"));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});

		findViewById(R.id.info).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent it = new Intent(self, AboutActivity.class);
				startActivity(it);

			}
		});

	}

}
