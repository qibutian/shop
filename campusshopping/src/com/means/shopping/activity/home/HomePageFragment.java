package com.means.shopping.activity.home;

import org.json.JSONException;
import org.json.JSONObject;

import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.JSONUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.cat.CatActivity;
import com.means.shopping.activity.main.SignActivity;
import com.means.shopping.activity.market.MarketActivity;
import com.means.shopping.activity.market.RechargeActivity;
import com.means.shopping.activity.my.CampusSelectActivity;
import com.means.shopping.activity.my.MyCommissionActivity;
import com.means.shopping.adapter.HomePageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.bean.Good;
import com.means.shopping.bean.SchoolEB;
import com.means.shopping.utils.ShareUtil;
import com.means.shopping.utils.ShopPerference;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.dialog.CommodityDetailDialog;
import com.means.shopping.views.dialog.CommodityDetailDialog.OnResultListener;
import com.means.shopping.views.dialog.RedPacketDialog;
import com.means.shopping.views.pop.SharePop;
import com.means.shopping.views.pop.SharePop.ShareResultListener;

import de.greenrobot.event.EventBus;

public class HomePageFragment extends Fragment implements OnClickListener {
	static HomePageFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	RefreshListViewAndMore listV;

	View headV;

	HomePageAdapter adapter;

	ListView contentListV;

	TextView titleT;

	// 超市点击按钮
	View marketV, redpacketV;

	// 分类按钮
	View classifyV;

	// 充值按钮
	View chongzhiV;

	// 夜生活按钮
	View night_lifeV;
	// 分享按钮
	View shareV;

	// 佣金
	View commissionV;

	// 签到
	View sign_layoutV;

	public static HomePageFragment getInstance() {
		if (instance == null) {
			instance = new HomePageFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_home_page, null);
		mLayoutInflater = inflater;
		EventBus.getDefault().register(this);
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		headV = mLayoutInflater.inflate(R.layout.head_home_page, null);
		headV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		listV.addHeadView(headV);
		contentListV = listV.getListView();
		adapter = new HomePageAdapter(API.market_daylist, getActivity(),
				R.layout.item_home_list);
		adapter.fromWhat("list");
		listV.setAdapter(adapter);
		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				final JSONObject jo = adapter.getTItem(position
						- contentListV.getHeaderViewsCount());
				Long goodId = JSONUtil.getLong(jo, "id");
				Good good = new Good();
				good.setCount(JSONUtil.getInt(jo, "cartcount"));
				good.setGoodId(goodId);
				good.setGoodType(1);
				CommodityDetailDialog dialog = new CommodityDetailDialog(
						getActivity(), good, jo);
				dialog.setOnResultListener(new OnResultListener() {

					@Override
					public void onResult(int cartcount) {
						try {
							jo.put("cartcount", cartcount);
							adapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				dialog.show();

			}
		});
		titleT = (TextView) mainV.findViewById(R.id.title);
		titleT.setOnClickListener(this);

		ShopPerference per = IocContainer.getShare().get(ShopPerference.class);
		per.load();

		if (!TextUtils.isEmpty(per.schoolName)) {
			titleT.setText(per.schoolName);
		}

		marketV = headV.findViewById(R.id.market);
		redpacketV = headV.findViewById(R.id.redpacket);
		night_lifeV = headV.findViewById(R.id.night_life);
		night_lifeV.setOnClickListener(this);
		marketV.setOnClickListener(this);

		classifyV = headV.findViewById(R.id.classify);
		classifyV.setOnClickListener(this);
		redpacketV.setOnClickListener(this);

		chongzhiV = headV.findViewById(R.id.chongzhi);
		chongzhiV.setOnClickListener(this);
		shareV = headV.findViewById(R.id.share);
		shareV.setOnClickListener(this);
		commissionV = headV.findViewById(R.id.commission);
		commissionV.setOnClickListener(this);
		sign_layoutV = headV.findViewById(R.id.sign_layout);
		sign_layoutV.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		// 校区选择
		case R.id.title:
			it = new Intent(getActivity(), CampusSelectActivity.class);
			startActivity(it);
			break;
		// 超市
		case R.id.market:
			it = new Intent(getActivity(), MarketActivity.class);
			startActivity(it);
			break;
		// 红包
		case R.id.redpacket:
			RedPacketDialog redDialog = new RedPacketDialog(getActivity());
			redDialog.show();
			break;

		case R.id.classify:
			it = new Intent(getActivity(), CatActivity.class);
			startActivity(it);
			break;

		case R.id.chongzhi:
			it = new Intent(getActivity(), RechargeActivity.class);
			startActivity(it);
			break;

		case R.id.night_life:
			it = new Intent(getActivity(), MarketActivity.class);
			startActivity(it);
			break;

		// 佣金
		case R.id.commission:
			it = new Intent(getActivity(), MyCommissionActivity.class);
			startActivity(it);
			break;

		// 分享
		case R.id.share:
			SharePop pop = new SharePop(getActivity());
			pop.setOnShareResultListener(new ShareResultListener() {

				@Override
				public void onResult(int result) {
					ShareUtil.wechatShare(result, getActivity(), "王者荣耀"
							+ result, "一起玩啊", "");
				}
			});
			pop.show();
			break;

		case R.id.sign_layout:
			it = new Intent(getActivity(), SignActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(SchoolEB school) {
		titleT.setText(school.getName());
		adapter.addparam("schoolid", school.getId());
		adapter.refresh();
	}
}
