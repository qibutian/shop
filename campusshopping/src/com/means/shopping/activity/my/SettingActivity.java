package com.means.shopping.activity.my;

import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseFragmentActivity;
import com.means.shopping.bean.LogoutEB;
import com.means.shopping.utils.ShopPerference;

import de.greenrobot.event.EventBus;

/**
 * 设置
 * 
 * @author Administrator
 * 
 */
public class SettingActivity extends ShopBaseFragmentActivity implements
		OnClickListener {

	private LinearLayout addressLl, changepwdLl, aboutLl;
	private Button logoutBtn;
	ShopPerference per;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		per = IocContainer.getShare().get(ShopPerference.class);
		per.load();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		setTitle("设置");
		addressLl = (LinearLayout) findViewById(R.id.address);
		changepwdLl = (LinearLayout) findViewById(R.id.changepwd);
		aboutLl = (LinearLayout) findViewById(R.id.about);
		logoutBtn = (Button) findViewById(R.id.logout);

		addressLl.setOnClickListener(this);
		changepwdLl.setOnClickListener(this);
		aboutLl.setOnClickListener(this);
		logoutBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		// 收货地址
		case R.id.address:
			it = new Intent(self, ConsigneeAddressActivity.class);
			startActivity(it);
			break;
		// 修改密码
		case R.id.changepwd:
			it = new Intent(self, ChangePasswordActivity.class);
			startActivity(it);
			break;
		// 关于我们
		case R.id.about:
			it = new Intent(self, AboutActivity.class);
			startActivity(it);
			break;
		// 推出登陆
		case R.id.logout:
			logout();
			break;

		default:
			break;
		}
	}
	
	//退出登录
	private void logout(){
		DhNet net = new DhNet(API.logout);
		net.doPostInDialog(new NetTask(self) {
			
			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					showToast("已退出登录");
					LogoutEB out = new LogoutEB();
					EventBus.getDefault().post(out);
					finish();
				}
			}
		});
	}
}
