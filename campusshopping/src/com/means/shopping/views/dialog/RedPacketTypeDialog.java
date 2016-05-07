package com.means.shopping.views.dialog;

import org.json.JSONObject;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.views.BaseAlertDialog;

public class RedPacketTypeDialog extends BaseAlertDialog {

	Context context;

	ListView listV;

	NetJSONAdapter adapter;

	OnSelectListener onSelectListener;

	public RedPacketTypeDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_language);
		initView();
	}

	private void initView() {
		listV = (ListView) findViewById(R.id.listview);
		adapter = new NetJSONAdapter(API.jifenduihuantype, context,
				R.layout.item_jifen);
		adapter.fromWhat("data");
		adapter.addField("name", R.id.name);
		listV.setAdapter(adapter);
		listV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				JSONObject jo = adapter.getTItem(position);
				if (onSelectListener != null) {
					onSelectListener.result(JSONUtil.getString(jo, "id"));
				}

				dismiss();

			}
		});
		adapter.refreshDialog();
	}

	public OnSelectListener getOnSelectListener() {
		return onSelectListener;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		this.onSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		void result(String id);
	}

}
