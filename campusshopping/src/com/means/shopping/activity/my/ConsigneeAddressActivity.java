package com.means.shopping.activity.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

/**
 * 收货地址
 * @author Administrator
 *
 */
public class ConsigneeAddressActivity extends ShopBaseActivity {
	
	private RadioGroup sexGroup;
	private EditText nameEt,phoneEt,detailEt;
	private TextView addressT;
	private TextView submitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consignee_address);
	}

	@Override
	public void initView() {
		setTitle("收货地址");
		sexGroup = (RadioGroup) findViewById(R.id.sexgroup);
		
		sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
			}
		});
		
		nameEt = (EditText) findViewById(R.id.name);
		phoneEt = (EditText) findViewById(R.id.phone);
		detailEt = (EditText) findViewById(R.id.detail);
		addressT = (TextView) findViewById(R.id.address);
		submitBtn = (TextView) findViewById(R.id.submit);
		
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				modify();
			}
		});
		
		getDate();
		
	}

	private void getDate() {
		DhNet net = new DhNet(API.address);
		net.doGetInDialog(new NetTask(self) {
			
			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					nameEt.setText(JSONUtil.getString(jo, "lxname"));
					phoneEt.setText(JSONUtil.getString(jo, "lxphone"));
					sexGroup.check(JSONUtil.getInt(jo, "sex") == 1 ? R.id.man : R.id.woman );//1男2女
					addressT.setText(JSONUtil.getString(jo, "lxaddress"));
				}
			}
		});
	}
	
	private void modify() {
		final String name = nameEt.getText().toString();
		final String phone = phoneEt.getText().toString();
		final String address = addressT.getText().toString();
		final String detail_address = detailEt.getText().toString();
		if (TextUtils.isEmpty(name)) {
			showToast("请填写收货方姓名");
			return;
		}
		if (TextUtils.isEmpty(phone)) {
			showToast("请填写收货方联系方式");
			return;
		}
//		if (TextUtils.isEmpty(address)) {
//			showToast("请填写收货地址");
//			return;
//		}
		
		DhNet net = new DhNet(API.editaddress);
		net.addParam("lxname",name);
		net.addParam("lxphone",phone);
		net.addParam("sex",sexGroup.getCheckedRadioButtonId()==R.id.man ? 1 : 2);
		net.addParam("lxaddress",address+detail_address);
		net.doPostInDialog(new NetTask(self) {
			
			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					Intent it = getIntent();
					it.putExtra("lxname", name);
					it.putExtra("lxphone", phone);
					it.putExtra("lxaddress", address+detail_address);
					setResult(RESULT_OK, it);
					finish();
				}
			}
		});
	}
}
