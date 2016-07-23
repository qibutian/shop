package com.means.shopping.activity.study;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.shopping.R;
import com.means.shopping.adapter.SimpleFragmentPageAdapter;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;

/**
 * 
 * 题库
 * 
 * @author Administrator
 * 
 */
public class QuestionbankActivity extends ShopBaseActivity {

	String contentid;
	String type;//(1继续学习or2错题重做)

	private ViewPager viewPager;
	
	public static List<Map<String, String>> answer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionbank);
	}

	@Override
	public void initView() {
		String title = getIntent().getStringExtra("title");
		contentid = getIntent().getStringExtra("contentid");
		type = getIntent().getStringExtra("type");
		
		answer = new ArrayList<Map<String,String>>();//存储试题答案
		setTitle(title);

		viewPager = (ViewPager) findViewById(R.id.viewpager);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		getData();
	}

	private void getData() {
		String url = "";
		if ("1".equals(type)) {//继续学习
			url = API.quuestionlist;
		}else {
			url = API.quuestionerrorlist;
		}
		DhNet net = new DhNet(url);
		net.addParam("contentid", contentid);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONArray jsa = response.jSONArrayFrom("list");
					List<Fragment> frags = new ArrayList<Fragment>();
					if (jsa!=null) {
						for (int i = 0; i < jsa.length(); i++) {  
							frags.add(new QuestionbankFragment(i+1,jsa.length(),JSONUtil.getJSONObjectAt(jsa, i),type,contentid));
						}
					}
					
					SimpleFragmentPageAdapter adapter = new SimpleFragmentPageAdapter(
							getSupportFragmentManager(), frags);
					viewPager.setAdapter(adapter);
					viewPager.setOffscreenPageLimit(jsa.length());
				}
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		answer.clear();
	}
}
