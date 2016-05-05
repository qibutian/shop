package com.means.shopping.activity.my;

import java.io.File;

import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.means.shopping.R;
import com.means.shopping.activity.main.MsgListActivity;
import com.means.shopping.activity.main.RecommendActivity;
import com.means.shopping.activity.market.CartActivity;
import com.means.shopping.activity.my.redpacket.MyRedPacketActivity;
import com.means.shopping.api.API;
import com.means.shopping.bean.CartBottomNumEB;
import com.means.shopping.bean.ReChargeEB;
import com.means.shopping.utils.FileUtil;
import com.means.shopping.utils.ShopPerference;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.greenrobot.event.EventBus;

/**
 * 我的
 * 
 * @author Administrator
 * 
 */
public class MyFragment extends Fragment implements OnClickListener {
	static MyFragment instance;

	View mainV;

	LayoutInflater mLayoutInflater;

	private ImageView headI;
	private ImageView settingI;
	private LinearLayout myredpacketLl;
	private TextView nicknameT, balanceT, goldT;
	private RelativeLayout msgLayoutR;

	View cartV;

	View recommendV;

	ShopPerference per;

	// 我的消息点击区域
	View msg_layoutV;

	File mCacheDir;

	public static MyFragment getInstance() {
		if (instance == null) {
			instance = new MyFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_my, null);
		mLayoutInflater = inflater;
		per = IocContainer.getShare().get(ShopPerference.class);
		per.load();
		initView();
		return mainV;
	}

	private void initView() {
		EventBus.getDefault().register(this);
		headI = (ImageView) mainV.findViewById(R.id.head);
		settingI = (ImageView) mainV.findViewById(R.id.setting);
		myredpacketLl = (LinearLayout) mainV.findViewById(R.id.myredpacket);
		recommendV = mainV.findViewById(R.id.recommend);
		msgLayoutR = (RelativeLayout) mainV.findViewById(R.id.msglayout);
		nicknameT = (TextView) mainV.findViewById(R.id.nickname);
		balanceT = (TextView) mainV.findViewById(R.id.balance);
		goldT = (TextView) mainV.findViewById(R.id.gold);
		msg_layoutV = mainV.findViewById(R.id.msg_layout);

		headI.setOnClickListener(this);
		settingI.setOnClickListener(this);
		myredpacketLl.setOnClickListener(this);
		recommendV.setOnClickListener(this);
		msg_layoutV.setOnClickListener(this);

		cartV = mainV.findViewById(R.id.cart_layout);
		cartV.setOnClickListener(this);

		getUserInfo();
	}

	private void getUserInfo() {
		DhNet net = new DhNet(API.userInfo);
		net.doGetInDialog(new NetTask(getActivity()) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					ViewUtil.bindView(nicknameT,
							JSONUtil.getString(jo, "nickname"));
					ViewUtil.bindView(goldT, JSONUtil.getString(jo, "gold"));
					ViewUtil.bindView(balanceT,
							JSONUtil.getString(jo, "balance"));
					ViewUtil.bindNetImage(headI,
							JSONUtil.getString(jo, "faceimg_s"), "default");
					int msgcount = JSONUtil.getInt(jo, "msgcount");
					if (msgcount > 0) {
						msgLayoutR.setVisibility(View.VISIBLE);
						ViewUtil.bindView(mainV.findViewById(R.id.msgcount),
								msgcount);
					}

				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		// 头像
		case R.id.head:
			// it = new Intent(getActivity(), ChangePasswordActivity.class);
			// it = new Intent(getActivity(), RegisterActivity.class);
			it = new Intent(getActivity(), LoginActivity.class);
			// it = new Intent(getActivity(), PaymentActivity.class);
			// it = new Intent(getActivity(), CampusSelectActivity.class);
			getActivity().startActivity(it);
			break;
		// 设置
		case R.id.setting:
			it = new Intent(getActivity(), SettingActivity.class);
			getActivity().startActivity(it);
			break;
		// 我的红包
		case R.id.myredpacket:
			it = new Intent(getActivity(), MyRedPacketActivity.class);
			getActivity().startActivity(it);
			break;

		case R.id.recommend:
			it = new Intent(getActivity(), RecommendActivity.class);
			getActivity().startActivity(it);
			break;

		case R.id.cart_layout:
			it = new Intent(getActivity(), CartActivity.class);
			getActivity().startActivity(it);
			break;
		// // 修改密码
		// case R.id.changepwd:
		// it = new Intent(getActivity(), ChangePasswordActivity.class);
		// getActivity().startActivity(it);
		// break;

		case R.id.msg_layout:
			it = new Intent(getActivity(), MsgListActivity.class);
			getActivity().startActivity(it);

			break;
		// 清除缓存
		// case R.id.wipe_cache:
		// ImageLoader.getInstance().getMemoryCache().clear();
		// ImageLoader.getInstance().getDiskCache().clear();
		// if (FileUtil.deleteFileOrDir(mCacheDir)) {
		// Toast.makeText(getActivity(), "缓存清理成功", Toast.LENGTH_SHORT)
		// .show();
		// cacheT.setText("0M");
		// } else {
		// Toast.makeText(getActivity(), "已经非常干净了", Toast.LENGTH_SHORT)
		// .show();
		// }
		// break;
		default:
			break;
		}
	}

	public void onEventMainThread(ReChargeEB reChargeEB) {
		getUserInfo();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
