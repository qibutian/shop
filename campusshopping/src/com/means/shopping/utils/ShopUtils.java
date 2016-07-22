package com.means.shopping.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShopUtils {
	// 是否包含字母
	public static boolean isLetter(String str) {
		for (int i = 0; i < str.length(); i++) { // 循环遍历字符串
			if (Character.isLetter(str.charAt(i))) { // 用char包装类中的判断字母的方法判断每一个字符
				return true;
			}
		}
		return false;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStrLong(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

}
