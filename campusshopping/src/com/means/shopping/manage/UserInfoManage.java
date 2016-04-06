package com.means.shopping.manage;

import net.duohuo.dhroid.ioc.IocContainer;
import android.app.Activity;
import android.content.Intent;

import com.means.shopping.activity.my.LoginActivity;
import com.means.shopping.bean.User;
import com.means.shopping.utils.ShopPerference;

public class UserInfoManage {

	static UserInfoManage instance;

	static ShopPerference per;

	public static UserInfoManage getInstance() {
		if (instance == null) {
			instance = new UserInfoManage();
		}
		per = IocContainer.getShare().get(ShopPerference.class);
		per.load();
		return instance;
	}

	public boolean checkLogin(final Activity context,
			final LoginCallBack loginCallBack) {
		User user = User.getInstance();
		boolean islogin = user.isLogin();
		if (!islogin) {

			Intent it = new Intent(context, LoginActivity.class);
			context.startActivity(it);

		} else {
			if (loginCallBack != null) {
				loginCallBack.onisLogin();
			}
		}
		return islogin;
	}

	public interface LoginCallBack {
		public void onisLogin();

		public void onLoginFail();
	}
}
