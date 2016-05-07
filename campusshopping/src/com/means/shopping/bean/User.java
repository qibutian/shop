package com.means.shopping.bean;

public class User {

	static User insance;

	boolean isLogin = false;

	String userid;

	String shareCode;

	// 余额
	double blance;

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

	public double getBlance() {
		return blance;
	}

	public void setBlance(double blance) {
		this.blance = blance;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
	
	

}
