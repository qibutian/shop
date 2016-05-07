package com.means.shopping.activity.my;

import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.CreditEB;
import com.means.shopping.views.dialog.RedPacketTypeDialog;
import com.means.shopping.views.dialog.RedPacketTypeDialog.OnSelectListener;

import de.greenrobot.event.EventBus;

public class RedeemActivity extends ShopBaseActivity {

	GridView gridView;

	NetJSONAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redeem);
	}

	@Override
	public void initView() {
		setTitle("积分兑换");
		ViewUtil.bindView(findViewById(R.id.credit), getIntent()
				.getStringExtra("credit"));
		gridView = (GridView) findViewById(R.id.gridView);
		adapter = new NetJSONAdapter(API.jifenduihuanlist, self,
				R.layout.item_redeem);
		adapter.fromWhat("data");
		adapter.addField("des", R.id.type);
		adapter.addField(new FieldMap("credit", R.id.credit) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {

				final JSONObject data = (JSONObject) jo;

				TextView exchangeT = (TextView) itemV
						.findViewById(R.id.exchange);
				exchangeT.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						exchange(JSONUtil.getString(data, "id"));
					}
				});
				// TODO Auto-generated method stub
				return o;
			}
		});
		adapter.addField("value", R.id.price);
		gridView.setAdapter(adapter);
		adapter.refreshDialog();

		findViewById(R.id.type).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				RedPacketTypeDialog dialog = new RedPacketTypeDialog(self);
				dialog.setOnSelectListener(new OnSelectListener() {

					@Override
					public void result(String id) {
						adapter.addparam("catid", id);
						adapter.refreshDialog();
					}
				});
				dialog.show();
			}
		});
	}

	private void exchange(String id) {
		DhNet net = new DhNet(API.jifenduihuan);
		net.addParam("id", id);
		net.doPostInDialog("兑换中...", new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					showToast("兑换成功!");
					EventBus.getDefault().post(new CreditEB());
				}

			}
		});
	}
}
