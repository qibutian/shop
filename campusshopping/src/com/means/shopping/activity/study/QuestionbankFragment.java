package com.means.shopping.activity.study;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.means.shopping.R;

/**
 * 
 * 题库碎片
 * @author Administrator
 *
 */
public class QuestionbankFragment extends Fragment {
	View mainV;

	LayoutInflater mLayoutInflater;

	TextView question,reference;
	RadioGroup options;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_question_bank, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		question = (TextView) mainV.findViewById(R.id.question);
		reference = (TextView) mainV.findViewById(R.id.reference);
		options = (RadioGroup) mainV.findViewById(R.id.options);
		
		options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				for (int i = 0; i < group.getChildCount(); i++) {
					RadioButton rb = (RadioButton) group.getChildAt(i);
					rb.setTextColor(getActivity().getResources().getColor(R.color.text_43_black));
				}
				RadioButton rb = (RadioButton) mainV.findViewById(checkedId);
				rb.setTextColor(getActivity().getResources().getColor(R.color.text_06_green));
			}
		});
		getData();
	}

	private void getData() {
	}
}