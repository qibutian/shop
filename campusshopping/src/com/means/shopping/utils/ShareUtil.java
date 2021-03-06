package com.means.shopping.utils;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.api.Constant;
import com.means.shopping.bean.User;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import com.tencent.connect.share.QQShare;

public class ShareUtil {

	/**
	 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
	 * 
	 * 
	 * @param flag
	 *            (0:分享到微信好友，1：分享到微信朋友圈)
	 */
	public static void wechatShare(int flag, Context context, String name,
			String reason, String store_id) {
		IWXAPI api = WXAPIFactory.createWXAPI(context, Constant.WX_APP_KEY);
		if (!isWXAppInstalledAndSupported(context, api)) {
			IocContainer.getShare().get(IDialog.class)
					.showToastShort(context, "请先安装微信");
			return;
		}
		User user = User.getInstance();
		api.registerApp(Constant.WX_APP_KEY);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = API.Baseurl + "/home/index/download?userid="
				+ user.getUserid() + "&code=" + user.getShareCode();
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = name;
		msg.description = reason;
		// 这里替换一张自己工程里的图片资源
		Bitmap thumb = null;

		thumb = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
		msg.setThumbImage(thumb);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}

	private static boolean isWXAppInstalledAndSupported(Context context,
			IWXAPI api) {
		// LogOutput.d(TAG, "isWXAppInstalledAndSupported");
		boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled()
				&& api.isWXAppSupportAPI();
		if (!sIsWXAppInstalledAndSupported) {

		}

		return sIsWXAppInstalledAndSupported;
	}

	public static void QQShare(Context context, String title, String content) {
		User user = User.getInstance();
		Tencent mTencent = Tencent.createInstance(Constant.QQ_APPID, context);
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
				QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, API.Baseurl
				+ "/home/index/download?userid=" + user.getUserid() + "&code="
				+ user.getShareCode());
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
				"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "小蚂蚁1105559060");
		mTencent.shareToQQ((Activity) context, params, new BaseUiListener(
				context));
	}

	public static void QQZOneShare(Context context, String title, String content) {
		User user = User.getInstance();
		Tencent mTencent = Tencent.createInstance(Constant.QQ_APPID, context);
		final Bundle params = new Bundle();
		// params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
		// QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, API.Baseurl
				+ "/home/index/download?userid=" + user.getUserid() + "&code="
				+ user.getShareCode());
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
				"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "小蚂蚁1105559060");
		params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
				QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
		mTencent.shareToQQ((Activity) context, params, new BaseUiListener(
				context));
	}
}
