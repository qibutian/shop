package com.means.shopping.activity.home;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;
import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.eventbus.EventBus;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.views.RefreshListViewAndMore;

public class HomePageFragment extends Fragment{
	static HomePageFragment instance;

	View mainV;

	PtrFrameLayout mPtrFrame;

	RefreshListViewAndMore listV;

	LoadMoreListViewContainer loadMoreListV;

	View headV;

	LayoutInflater mLayoutInflater;

	View bottomSearchV;

	ListView contentListV;

	ImageView collectI;

	NetJSONAdapter adapter;

	public static HomePageFragment getInstance() {
		if (instance == null) {
			instance = new HomePageFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_home_page, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {

	}
}
