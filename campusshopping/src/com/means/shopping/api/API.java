package com.means.shopping.api;

public class API {

	public static String test = "http://www.foodies.im/wap.php?g=Wap&c=Travel&a=cityList";

	public static String Baseurl = "http://115.28.184.43";

	// 关于我们
	public static String about = Baseurl + "/home/index/aboutdetail";
	// 获取验证码
	public static String mobilecode = Baseurl + "/home/user/mobilecode";
	// 用户注册
	public static String registaction = Baseurl + "/home/user/registaction";
	// 注册协议
	public static String aboutregist = Baseurl + "/home/index/aboutregist";
	// 用户登录
	public static String login = Baseurl + "/home/user/login";
	// 修改密码
	public static String resetpswdbyphone = Baseurl
			+ "/home/user/resetpswdbyphone";
	// 用户信息接口
	public static String userInfo = Baseurl + "/home/user/info";
	// 用户退出
	public static String logout = Baseurl + "/home/user/logout";

	// 超市分类
	public static String marketCat = Baseurl + "/home/market/catlist";

	// 超市商品列表
	public static String marketGoodList = Baseurl + "/home/market/contentlist";

	// 学校列表
	public static String schooldata = Baseurl + "/home/market/schooldata";

	// 首页天天特卖分页列表
	public static String market_daylist = Baseurl + "/home/market/daylist";

	// 商品分类列表
	public static String shop_catlist = Baseurl + "/home/shop/catlist";

	// 商品分页列表
	public static String shop_contentlist = Baseurl + "/home/shop/contentlist";
}
