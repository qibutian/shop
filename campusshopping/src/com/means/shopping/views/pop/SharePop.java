package com.means.shopping.views.pop;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.means.shopping.R;

/**
 * Created by Administrator on 2015/11/19.
 */
public class SharePop implements View.OnClickListener {

	Activity context;

	View contentV;

	PopupWindow pop;

	LinearLayout layout_share_weixin, layout_share_wxcircle, layout_share_qq,
			layout_share_qqzone;

	ShareResultListener shareResultListener;

	public SharePop(Activity context) {
		this.context = context;
		contentV = LayoutInflater.from(context).inflate(R.layout.pop_share,
				null);
		pop = new PopupWindow(contentV, ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT, true);
		// 需要设置一下此参数，点击外边可消失
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);
		pop.setAnimationStyle(R.style.mystyle);
		pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		initView();

	}

	private void initView() {

		layout_share_weixin = (LinearLayout) contentV
				.findViewById(R.id.layout_share_weixin);
		layout_share_wxcircle = (LinearLayout) contentV
				.findViewById(R.id.layout_share_wxcircle);
		layout_share_qq = (LinearLayout) contentV
				.findViewById(R.id.layout_share_qq);
		layout_share_qqzone = (LinearLayout) contentV
				.findViewById(R.id.layout_share_qqzone);

		layout_share_weixin.setOnClickListener(this);
		layout_share_wxcircle.setOnClickListener(this);
		layout_share_qq.setOnClickListener(this);
		layout_share_qqzone.setOnClickListener(this);
	}

	public void show() {
		pop.showAtLocation(contentV.getRootView(), Gravity.CENTER, 0, 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 好友
		case R.id.layout_share_weixin:
			pop.dismiss();
			if (shareResultListener != null) {
				shareResultListener.onResult(0);
			}
			break;
		// 朋友圈
		case R.id.layout_share_wxcircle:
			pop.dismiss();
			if (shareResultListener != null) {
				shareResultListener.onResult(1);
			}

			break;

		case R.id.layout_share_qq:
			pop.dismiss();
			if (shareResultListener != null) {
				shareResultListener.onResult(2);
			}
			break;

		case R.id.layout_share_qqzone:
			pop.dismiss();
			if (shareResultListener != null) {
				shareResultListener.onResult(3);
			}
			break;
		}
	}

	public interface ShareResultListener {
		void onResult(int result);
	}

	public ShareResultListener getOnShareResultListener() {
		return shareResultListener;
	}

	public void setOnShareResultListener(ShareResultListener shareResultListener) {
		this.shareResultListener = shareResultListener;
	}

}
