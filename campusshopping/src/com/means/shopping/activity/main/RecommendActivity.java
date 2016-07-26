package com.means.shopping.activity.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.utils.ShareUtil;
import com.means.shopping.views.pop.SharePop;
import com.means.shopping.views.pop.SharePop.ShareResultListener;

public class RecommendActivity extends ShopBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
	}

	@Override
	public void initView() {
		setTitle("推荐有奖");
		findViewById(R.id.share).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharePop pop = new SharePop(self);
				pop.setOnShareResultListener(new ShareResultListener() {

					@Override
					public void onResult(int result) {
						if (result == 2) {
							ShareUtil.QQShare(self, "小蚂蚁校园购物",
									"方便实用的校园购物助手,你想要的我们都有");
						} else if (result == 3) {
							ShareUtil.QQZOneShare(self, "小蚂蚁校园购物",
									"方便实用的校园购物助手,你想要的我们都有");
						} else {
							ShareUtil.wechatShare(result, self, "小蚂蚁校园购物",
									"方便实用的校园购物助手,你想要的我们都有", "");
						}
					}
				});
				pop.show();
			}
		});
	}
}
