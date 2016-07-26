package com.means.shopping.utils;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;

import org.json.JSONObject;

import android.content.Context;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class BaseUiListener implements IUiListener {

	IDialog dialoger;
	Context context;

	public BaseUiListener(Context context) {
		dialoger = IocContainer.getShare().get(IDialog.class);
		this.context = context;
	}

	protected void doComplete(JSONObject values) {
	}

	@Override
	public void onError(UiError e) {
		dialoger.showToastShort(context, "code:" + e.errorCode + ", msg:"
				+ e.errorMessage + ", detail:" + e.errorDetail);
	}

	@Override
	public void onCancel() {
		dialoger.showToastShort(context, "分享取消");
	}

	@Override
	public void onComplete(Object arg0) {
		dialoger.showToastShort(context, "分享成功!");
		// JSONObject jo = (JSONObject) arg0;
		// doComplete(jo);
	}

}
