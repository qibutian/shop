package com.means.shopping.activity.market;

import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.view.BadgeView;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.adapter.CatAdapter;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.Cart;
import com.means.shopping.bean.Good;
import com.means.shopping.bean.PriceEB;
import com.means.shopping.views.RefreshListViewAndMore;

import de.greenrobot.event.EventBus;

public class MarketActivity extends ShopBaseActivity {

	ListView catListV;

	CatAdapter catAdapter;

	RefreshListViewAndMore goodListV;

	HomePageAdapter goodAdater;

	ListView goodListContentV;

	ImageView cartI;

	View bradeV;

	BadgeView badgeT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		setTitle("超市");
		catListV = (ListView) findViewById(R.id.listview_normal);
		catAdapter = new CatAdapter(self);
		catListV.setAdapter(catAdapter);

		goodListV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		goodListContentV = goodListV.getListView();
		goodAdater = new HomePageAdapter(API.test, self,
				R.layout.item_home_list, 1);
		goodAdater.fromWhat("data");
		cartI = (ImageView) findViewById(R.id.cart);
		goodAdater.setTargetView(cartI);
		goodListV.setAdapter(goodAdater);
		goodListContentV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = goodAdater.getTItem(position);
				Long goodId = JSONUtil.getLong(jo, "area_id");
				Good good = new Good();
				good.setGoodId(goodId);
				CommodityDetailDialog dialog = new CommodityDetailDialog(self,good);
				dialog.show();

			}
		});
		goodListV.setAdapter(goodAdater);

		bradeV = findViewById(R.id.brade);
		badgeT = new BadgeView(self, cartI);// 创建一个BadgeView对象，view为你需要显示提醒信息的控件
		badgeT.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.中间，还有其他位置属性
		badgeT.setTextColor(Color.WHITE); // 文本颜色
		badgeT.setBadgeBackgroundColor(getResources().getColor(
				R.color.text_ff9_yellow)); // 背景颜色
		badgeT.setTextSize(10); // 文本大小
		setCartNum();
	}

	public void setCartNum() {
		int count = Cart.getInstance().getCount();
		badgeT.setVisibility(count != 0 ? View.VISIBLE : View.GONE);
		badgeT.setText(count + "");
	}

	public void onEventMainThread(PriceEB priceEB) {
		setCartNum();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
