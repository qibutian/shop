package com.means.shopping.utils;

import net.duohuo.dhroid.util.Perference;

public class ShopPerference extends Perference {
	// 第一次登陆
	public int isFirst = 0;

	public String pswd;

	public String phone;
	
	public boolean login;

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

}
