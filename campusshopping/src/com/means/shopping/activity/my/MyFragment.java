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

import com.means.shopping.R;
import com.means.shopping.activity.main.MsgListActivity;
import com.means.shopping.activity.main.RecommendActivity;
import com.means.shopping.activity.market.CartActivity;
import com.means.shopping.activity.my.redpacket.MyRedPacketActivity;
import com.means.shopping.activity.study.JobParticularsActivity;
import com.means.shopping.api.API;
import com.means.shopping.bean.CreditEB;
import com.means.shopping.bean.ReChargeEB;
import com.means.shopping.bean.User;
import com.means.shopping.utils.ShopPerference;

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

	// 我的余额.我的佣金按钮
	View yueV, yongjinV;

	// 积分兑换按钮
	View jifenV;

	// 按钮按钮
	View anhaoV;
	
	// 培训考试
	View examinationV;

	String credit;

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

		yueV = mainV.findViewById(R.id.yue_layout);
		yongjinV = mainV.findViewById(R.id.yongjin_layout);
		yueV.setOnClickListener(this);
		yongjinV.setOnClickListener(this);

		jifenV = mainV.findViewById(R.id.jifen_layout);
		jifenV.setOnClickListener(this);

		anhaoV = mainV.findViewById(R.id.anhao_layout);
		anhaoV.setOnClickListener(this);
		
		examinationV = mainV.findViewById(R.id.examination_layout);
		examinationV.setOnClickListener(this);
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
					ViewUtil.bindView(mainV.findViewById(R.id.jifen),
							JSONUtil.getString(jo, "credit"));
					credit = JSONUtil.getString(jo, "credit");
					ViewUtil.bindNetImage(headI,
							JSONUtil.getString(jo, "faceimg_s"), "default");
					int msgcount = JSONUtil.getInt(jo, "msgcount");
					User.getInstance().setBlance(
							JSONUtil.getDouble(jo, "balance"));
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
			// it = new Intent(getActivity(), LoginActivity.class);
			// // it = new Intent(getActivity(), PaymentActivity.class);
			// // it = new Intent(getActivity(), CampusSelectActivity.class);
			// getActivity().startActivity(it);
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

		case R.id.yue_layout:
			it = new Intent(getActivity(), MyBlanceActivity.class);
			getActivity().startActivity(it);
			break;

		case R.id.anhao_layout:
			it = new Intent(getActivity(), AnhaoActivity.class);
			getActivity().startActivity(it);
			break;

		case R.id.yongjin_layout:
			it = new Intent(getActivity(), MyCommissionActivity.class);
			getActivity().startActivity(it);
			break;

		case R.id.jifen_layout:
			it = new Intent(getActivity(), RedeemActivity.class);
			it.putExtra("credit", credit);
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
			//培训考试
		case R.id.examination_layout:
			it = new Intent(getActivity(), JobParticularsActivity.class);
			getActivity().startActivity(it);
			break;
		default:
			break;
		}
	}

	public void onEventMainThread(ReChargeEB reChargeEB) {
		getUserInfo();
	}
	
	public void onEventMainThread(CreditEB creditEB) {
		getUserInfo();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
