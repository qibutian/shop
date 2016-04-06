package com.means.shopping.bean;

public class User {

	static User insance;

	boolean isLogin = false;

	public static User getInstance() {

		if (insance == null) {
			insance = new User();
		}

		return insance;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

}
