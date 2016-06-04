package com.means.shopping.activity.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.shopping.R;
import com.means.shopping.base.ShopBaseActivity;

/**
 * 
 * 学习成绩
 * 
 * @author Administrator
 * 
 */
public class StudyScoreActivity extends ShopBaseActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_score);
	}

	@Override
	public void initView() {
		setLeftAction(-1, "学习成绩", new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.continuestudy).setOnClickListener(this);
		findViewById(R.id.again).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		// 继续学习
		case R.id.continuestudy:
			it = new Intent(self,QuestionbankActivity.class);
			it.putExtra("title", "2016高考试题");
			startActivity(it);
			break;
		// 错题重做
		case R.id.again:
			it = new Intent(self,QuestionbankActivity.class);
			it.putExtra("title", "2016高考试题");
			startActivity(it);
			break;

		default:
			break;
		}
	}
}
