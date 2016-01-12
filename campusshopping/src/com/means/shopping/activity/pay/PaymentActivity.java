package com.means.shopping.activity.pay;

import net.duohuo.dhroid.util.DhUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;

/**
 * 付款页
 * @author Administrator
 *
 */
public class PaymentActivity extends ShopBaseActivity {
	private int mWindoWidth;
	
	private LinearLayout foodslayoutLl;
	
	int[] imgs = {R.drawable.red_packet_top_bg,R.drawable.red_packet_top_bg,R.drawable.red_packet_top_bg,R.drawable.red_packet_top_bg,R.drawable.red_packet_top_bg,R.drawable.red_packet_top_bg,R.drawable.red_packet_top_bg,R.drawable.red_packet_top_bg};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
	}

	@Override
	public void initView() {
		getWindowsWidth();
		foodslayoutLl = (LinearLayout) findViewById(R.id.foodslayout);
		setFoodsImgs();
	}
	
	private void getWindowsWidth(){
		WindowManager wm = this.getWindowManager();
		mWindoWidth = wm.getDefaultDisplay().getWidth();
	}
	
	private void setFoodsImgs(){
		int with = DhUtil.dip2px(self,(DhUtil.px2dip(self, mWindoWidth)-60)/7);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(with, with);
		lp.setMargins(0, 0,  DhUtil.dip2px(self, 10),0);
		for (int i = 0; i < imgs.length; i++) {
			ImageView img = new ImageView(self);
			img.setLayoutParams(lp);
			img.setScaleType(ScaleType.CENTER_CROP);
			if(i==6){
				img.setImageResource(R.drawable.icon_more);
				foodslayoutLl.addView(img);
				img.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showToast("敬请期待");
					}
				});
				break;
			}
			img.setImageResource(imgs[i]);
			foodslayoutLl.addView(img);
			Log.d("img---------", i+1+"");
			
		}
		
	}
}
