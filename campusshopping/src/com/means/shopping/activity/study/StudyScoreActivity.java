package com.means.shopping.activity.study;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.base.ShopBaseActivity;
import com.means.shopping.bean.QuestionEB;

import de.greenrobot.event.EventBus;

/**
 * 
 * 学习成绩
 * 
 * @author Administrator
 * 
 */
public class StudyScoreActivity extends ShopBaseActivity implements
		OnClickListener {

	// 试题id
	String contentid;
	// 试卷标题
	String title;

	TextView countT, question_txt, accuracy_txt, continuestudyT, againT;
	ProgressBar questionPb, accuracyPb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_score);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		contentid = getIntent().getStringExtra("catid");
		title = getIntent().getStringExtra("title");
		setTitle("学习成绩");

		continuestudyT = (TextView) findViewById(R.id.continuestudy);
		againT = (TextView) findViewById(R.id.again);
		countT = (TextView) findViewById(R.id.count);
		question_txt = (TextView) findViewById(R.id.question_txt);
		accuracy_txt = (TextView) findViewById(R.id.accuracy_txt);
		questionPb = (ProgressBar) findViewById(R.id.question);
		accuracyPb = (ProgressBar) findViewById(R.id.accuracy);

		continuestudyT.setOnClickListener(this);
		againT.setOnClickListener(this);

		getData();

	}

	private void getData() {
		DhNet net = new DhNet(API.paperinfo);
		net.addParam("contentid", contentid);
		net.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					// String answer = JSONUtil.getString(jo, "answer");
					int questioncount = JSONUtil.getInt(jo, "questioncount");// 题目总数量
					int answercount = JSONUtil.getInt(jo, "answercount");// 已答数量
					int errorcount = JSONUtil.getInt(jo, "errorcount");// 打错数量
					// if (!"1".equals(answer)) { // 未答过该题库
					// againT.setVisibility(View.GONE);
					// countT.setText("0");
					// question_txt.setText(0 + "/" + questioncount);
					// questionPb.setProgress(0);
					// accuracy_txt.setText(0 + "/" + questioncount);
					// accuracyPb.setProgress(0);
					// continuestudyT.setText("开始学习");
					// } else {
					againT.setVisibility(View.VISIBLE);
					countT.setText(JSONUtil.getString(jo, "successcount"));
					question_txt.setText(answercount + "/" + questioncount);
					questionPb.setMax(questioncount);
					accuracyPb.setMax(questioncount);
					questionPb.setProgress(answercount);
					accuracy_txt.setText((questioncount - errorcount) + "/"
							+ questioncount);
					accuracyPb.setProgress(questioncount - errorcount);
					
					if (errorcount != 0) {
						againT.setVisibility(View.VISIBLE);
					} else {
						againT.setVisibility(View.GONE);
					}
					continuestudyT.setText("继续学习");
					// }
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		// 继续学习
		case R.id.continuestudy:
			it = new Intent(self, QuestionbankActivity.class);
			it.putExtra("title", title);
			it.putExtra("contentid", contentid);
			it.putExtra("type", "1");
			startActivity(it);
			break;
		// 错题重做
		case R.id.again:
			it = new Intent(self, QuestionbankActivity.class);
			it.putExtra("title", title);
			it.putExtra("contentid", contentid);
			it.putExtra("type", "2");
			startActivity(it);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(QuestionEB event) {
		getData();
	}

}
