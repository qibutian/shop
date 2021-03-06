package com.means.shopping.utils;

import net.duohuo.dhroid.net.JSONUtil;

import com.means.shopping.bean.CartBottomNumEB;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

public class CartAnimUtil {

	private ViewGroup anim_mask_layout;

	Activity mActivity;

	OnAnimationEndListener onAnimationEndListener;
	int count;
	double price;

	public CartAnimUtil(Activity activity, int count, double price) {
		this.mActivity = activity;
		this.count = count;
		this.price = price;
	}

	private ViewGroup createAnimLayout(Activity activity) {
		ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(activity);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	public void setAnim(final View v, int[] start_location, View shopCart) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout(mActivity);
		anim_mask_layout.addView(v);// 把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v,
				start_location);
		int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
		shopCart.getLocationInWindow(end_location);// shopCart是那个购物车

		// 计算位移
		int endX = end_location[0] - start_location[0] + shopCart.getWidth()
				/ 2;// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(800);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
				CartBottomNumEB cartBottomNumEB = new CartBottomNumEB();
				cartBottomNumEB.setCount(count);
				cartBottomNumEB.setPrice(price);
				EventBus.getDefault().post(cartBottomNumEB);
			}
		});

	}

	public OnAnimationEndListener getOnAnimationEndListener() {
		return onAnimationEndListener;
	}

	public void setOnAnimationEndListener(
			OnAnimationEndListener onAnimationEndListener) {
		this.onAnimationEndListener = onAnimationEndListener;
	}

	public interface OnAnimationEndListener {
		void onEnd();
	}
}
