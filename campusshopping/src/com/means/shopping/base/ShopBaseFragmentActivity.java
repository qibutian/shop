package com.means.shopping.base;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.means.shopping.R;

public class ShopBaseFragmentActivity extends FragmentActivity{
	public IDialog dialoger;

	public Activity self;

	Integer dialogcount = 0;

	Dialog progressdialog;

	public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// FoodsUtils.initSystemBar(this);
		dialoger = IocContainer.getShare().get(IDialog.class);
		self = this;
	}

	@Override
	protected void onStart() {
		super.onStart();
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(CONNECTIVITY_CHANGE_ACTION);
		// registerReceiver(netReceiver, filter);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initTitleBar();

	}

	private void initTitleBar() {
		View backV = findViewById(R.id.backLayout);
		if (backV != null) {
			backV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();
				}
			});
		}
	}


	/**
	 * 左边返回按钮为空
	 */
	public void setLeftIconGone() {
		ImageView backV = (ImageView) findViewById(R.id.back);
		if (backV != null) {
			backV.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置titlebar 显示 or 隐藏
	 * @param visibility
	 */
	public void setTitleVisibility(int visibility){
		RelativeLayout titleLayout  =  (RelativeLayout) findViewById(R.id.labt);
		if (titleLayout != null) {
				titleLayout.setVisibility(visibility);
		}
	}
	
	/**
	 * 设置标题右侧图标
	 */
	public void setTitleLeftImg(int leftid){
		ImageView leftImg = (ImageView) findViewById(R.id.gallery_img);
		if(leftImg!= null){
			if(leftid == -1){
				leftImg.setVisibility(View.GONE);
			}else {
				leftImg.setImageResource(leftid);
				leftImg.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * 设置标题
	 */
	public void setTitle(String text) {
		TextView titleT = (TextView) findViewById(R.id.title);
		if (titleT != null) {
			titleT.setText(text);
		}
	}
	
	/**
	 * 设置左边的点击事件
	 */
	public void setLeftAction(int leftid, String leftText,
			OnClickListener listener) {
		ImageView backV = (ImageView) findViewById(R.id.back);
		if (backV != null) {
			if (leftid == -1) {
				backV.setVisibility(View.VISIBLE);
			} else if (leftid == -2) {
				backV.setVisibility(View.GONE);
			} else {
				backV.setVisibility(View.VISIBLE);
				backV.setImageResource(leftid);
			}
			if (listener != null) {
				View backLV = findViewById(R.id.backLayout);
				if (backLV != null) {
					backLV.setOnClickListener(listener);
				}
			}
		}

		TextView leftTextT = (TextView) findViewById(R.id.left_text);
		if (leftText != null) {
			if (leftTextT != null) {
				leftTextT.setText(leftText);
				leftTextT.setVisibility(View.VISIBLE);
			}
		} else {
			leftTextT.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 设置右面的点击事件
	 */
	public void setRightAction(String text, int id, OnClickListener listener) {
		TextView rightT = (TextView) findViewById(R.id.right_text);
		if (rightT != null) {
			if (text != null) {
				rightT.setVisibility(View.VISIBLE);
				rightT.setText(text);
				rightT.setOnClickListener(listener);
			} else {
				rightT.setVisibility(View.GONE);
			}
		}
		ImageView rightI = (ImageView) findViewById(R.id.right_icon);
		if (rightI != null) {
			if (id != -1) {
				rightI.setVisibility(View.VISIBLE);
				rightI.setImageResource(id);
				rightI.setOnClickListener(listener);
			} else {
				rightI.setVisibility(View.GONE);
			}
		}

	}

	/**
	 * 设置右面显示或者隐藏
	 */
	public void setRightVISIBLEOrGone(int type) {
		TextView rightT = (TextView) findViewById(R.id.right_text);
		if (rightT != null) {
			rightT.setVisibility(type);
		}

		ImageView rightI = (ImageView) findViewById(R.id.right_icon);
		if (rightI != null) {
			rightI.setVisibility(type);
		}
	}

	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		super.startActivity(intent);
		// modalInAnim();
	}

	@Override
	public void finish() {
		super.finish();
//		popOutAnim();
	}

	public void finishWithoutAnim() {
		super.finish();
	}

	public void finishAnim() {
		super.finish();
		modalOutAnim();
	}

	@SuppressLint("NewApi")
	public void popInAnim() {
		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}

	/**
	 * 左右滑动切换页面的动画 后退
	 */
	@SuppressLint("NewApi")
	public void popOutAnim() {
		overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
	}

	/**
	 * 向上推出时切换的动画 进入
	 */
	@SuppressLint("NewApi")
	public void modalInAnim() {
		overridePendingTransition(R.anim.slide_up_in, R.anim.fade_out);
	}

	/**
	 * 向上推出时切换的动画 后退
	 */
	public void modalOutAnim() {
		overridePendingTransition(R.anim.dialog_enter, R.anim.dialog_exit);
	}

	/**
	 * 弹出提示框
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		dialoger.showToastShort(getApplicationContext(), msg);
	}

	/**
	 * 显示加载框
	 * 
	 * @param msg
	 * @return
	 */
	public Dialog showProgressDialog(String msg) {
		if (TextUtils.isEmpty(msg))
			msg = getString(R.string.progress_doing);

		synchronized (dialogcount) {
			dialogcount++;
		}

		System.out.println("show" + dialogcount);

		if (progressdialog == null || !progressdialog.isShowing()) {
			progressdialog = dialoger.showProgressDialog(this, msg);
			System.out.print("progress dlg:"
					+ dialoger.getClass().getSimpleName());
		}
		return progressdialog;
	}

	/**
	 * dismiss加载框
	 */
	public void hidenProgressDialog() {
		synchronized (dialogcount) {
			dialogcount--;
		}
		System.out.println("hiden" + dialogcount);
		// if (dialogcount == 0) {
		if (progressdialog != null) {
			progressdialog.dismiss();
		}
		// }
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
