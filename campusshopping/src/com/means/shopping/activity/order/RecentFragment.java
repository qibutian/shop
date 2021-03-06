package com.means.shopping.activity.order;

import java.math.BigDecimal;
import java.util.Date;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.utils.Arith;
import com.means.shopping.utils.ShopUtils;
import com.means.shopping.views.CartBottomView;
import com.means.shopping.views.MessageDialog;
import com.means.shopping.views.RefreshListViewAndMore;
import com.means.shopping.views.MessageDialog.OnDelectResultListener;

/**
 * 订单->近一月
 * 
 * @author Administrator
 * 
 */
public class RecentFragment extends Fragment {
	static RecentFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	RefreshListViewAndMore listV;
	NetJSONAdapter adapter;
	ListView contentListV;

	public static RecentFragment getInstance() {
		if (instance == null) {
			instance = new RecentFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_recent, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		getData();
	}

	private void getData() {

		adapter = new NetJSONAdapter(API.listall, getActivity(),
				R.layout.item_recent_order_list);
		adapter.addparam("type", 1);
		adapter.fromWhat("list");
		adapter.addField("payprice", R.id.payprice);
		adapter.addField("code", R.id.code);

		adapter.addField("buyphone", R.id.buyphone);
		adapter.addField(new FieldMap("adddateline", R.id.adddateline) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {

				final JSONObject data = (JSONObject) jo;
				int paystatus = JSONUtil.getInt(data, "paystatus");
				final TextView zhifuT = (TextView) itemV
						.findViewById(R.id.zhifu);
				if (paystatus == 4) {
					zhifuT.setText("确认收货");
					zhifuT.setVisibility(View.VISIBLE);
				} else if (paystatus == 3) {
					zhifuT.setText("立即支付");
					zhifuT.setVisibility(View.VISIBLE);
				} else if (paystatus == 2) {
					zhifuT.setText("待收货");
					zhifuT.setVisibility(View.VISIBLE);
				} else if (paystatus == 1) {
					zhifuT.setText("立即支付");
					zhifuT.setVisibility(View.VISIBLE);
				} else if (paystatus == 5) {
					zhifuT.setText("已完成");
					zhifuT.setVisibility(View.VISIBLE);
				} else {
					zhifuT.setVisibility(View.GONE);
				}

				zhifuT.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						if (zhifuT.getText().toString().equals("立即支付")) {

							pay(JSONUtil.getString(data, "id"), Arith.sub(
									JSONUtil.getDouble(data, "payprice"),
									JSONUtil.getDouble(data, "payedprice")));

							// pay(JSONUtil.getString(data, "id"),
							// (payprice - payedprice) / 100d);
						} else if (zhifuT.getText().toString().equals("确认收货")) {
							ordersure(data);
						}

					}
				});

				itemV.findViewById(R.id.buyphone_layout).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {

								Intent intent = new Intent(Intent.ACTION_DIAL,
										Uri.parse("tel:"
												+ JSONUtil.getString(data,
														"buyphone")));
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
							}
						});

				return ShopUtils.dateToStrLong(new Date(Long.parseLong(o
						.toString()) * 1000));
			}
		});

		listV.setAdapter(adapter);
		contentListV = listV.getListView();

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = adapter.getTItem(position - 1);
			}
		});
	}

	private void pay(final String orderid, final double orderprice) {

		DhNet net = new DhNet(API.userInfo);
		net.doGetInDialog(new NetTask(getActivity()) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();

					final CartBottomView cattV = new CartBottomView(
							getActivity());
					final double balance = JSONUtil.getDouble(jo, "balance");
					if (balance != 0) {
						if (balance >= orderprice) {
							MessageDialog dialog = new MessageDialog(
									getActivity(), "你将使用余额支付",

									orderprice + "元");

							dialog.setOnDelectResultListener(new OnDelectResultListener() {

								@Override
								public void onResult() {
									cattV.payByYue(orderid, orderprice + "");
								}
							});
							dialog.show();

						} else {

							MessageDialog dialog = new MessageDialog(
									getActivity(), "您的余额不足,将通过支付宝充值",

									Arith.sub(orderprice, balance) + "元");

							dialog.setOnDelectResultListener(new OnDelectResultListener() {

								@Override
								public void onResult() {
									cattV.recharge(orderid,
											Arith.sub(orderprice, balance));
								}
							});
							dialog.show();

						}
					} else {

						MessageDialog dialog = new MessageDialog(getActivity(),
								"你将使用支付宝支付",

								orderprice + "元");

						dialog.setOnDelectResultListener(new OnDelectResultListener() {

							@Override
							public void onResult() {
								cattV.payZhifuBao(orderid, orderprice + "");
							}
						});
						dialog.show();

					}

				}
			}
		});

	}

	private void ordersure(final JSONObject jo) {
		DhNet net = new DhNet(API.ordersure);
		net.addParam("orderid", JSONUtil.getString(jo, "id"));
		net.doPostInDialog(new NetTask(getActivity()) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					try {
						jo.put("paystatus", 5);
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	public void refresh() {
		if (listV != null) {
			listV.refresh();
		}
	}
}
