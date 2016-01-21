package com.means.shopping.utils;

public class ShopUtils {
	//是否包含字母
	public static boolean isLetter(String str){
		for (int i = 0; i < str.length(); i++) { // 循环遍历字符串
			if (Character.isLetter(str.charAt(i))) { // 用char包装类中的判断字母的方法判断每一个字符
				return true;
			}
		}
		return false;
	}
}
