package com.means.shopping.activity.study;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.DhUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.means.shopping.R;
import com.means.shopping.api.API;
import com.means.shopping.bean.QuestionEB;

import de.greenrobot.event.EventBus;

/**
 * 
 * 题库碎片
 * 
 * @author Administrator
 * 
 */
@SuppressLint("ValidFragment")
public class QuestionbankFragment extends Fragment {
	View mainV;

	LayoutInflater mLayoutInflater;

	TextView question, reference;
	RadioGroup options;
	LinearLayout reference_layout;

	int count; // 总题数
	int current; // 当前题数
	JSONObject jo; // 内容
	String type; // 状态（2错题重答or1继续答题）
	String id; // 试题id
	Map<String, String> map = new HashMap<String, String>();

	public QuestionbankFragment() {
	}

	public QuestionbankFragment(int current, int count, JSONObject jo,
			String type) {
		this.current = current;
		this.count = count;
		this.jo = jo;
		this.type = type;

		id = JSONUtil.getString(jo, "id");
		map.put(id, "");
		QuestionbankActivity.answer.add(map);
	}

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
		reference_layout = (LinearLayout) mainV
				.findViewById(R.id.reference_layout);
		options = (RadioGroup) mainV.findViewById(R.id.options);

		options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				for (int i = 0; i < group.getChildCount(); i++) {
					RadioButton rb = (RadioButton) group.getChildAt(i);

					if (rb.getId() == checkedId) {
						rb.setTextColor(getActivity().getResources().getColor(
								R.color.text_06_green));
						// 更换选项
						map.put("answer", transformLetter(i));
						map.put(id, transformLetter(i));
						map.put("questionId", JSONUtil.getString(jo, "id"));
						QuestionbankActivity.answer.set(current - 1, map);
					} else {
						rb.setTextColor(getActivity().getResources().getColor(
								R.color.text_43_black));
					}
				}
			}
		});
		getData();
	}

	private void getData() {
		if (current == count) {// 如果是最后一题 则显示提交试卷
			TextView submitT = (TextView) mainV.findViewById(R.id.submit);
			submitT.setVisibility(View.VISIBLE);
			submitT.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					submit();
				}
			});
		}
		if ("2".equals(type)) {// 如果是错题重答的话 显示参考答案
			reference.setText(JSONUtil.getString(jo, "solution"));
			reference_layout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					reference.setVisibility(View.VISIBLE);
				}
			});
			reference_layout.setVisibility(View.VISIBLE);

		}
		// 问题
		String squestion = current + "/" + count + " "
				+ JSONUtil.getString(jo, "content");
		question.setText(squestion);
		// 选项
		JSONArray jsa = JSONUtil.getJSONArray(jo, "itemdata");
		RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.WRAP_CONTENT,
				RadioGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, DhUtil.dip2px(getActivity(), 10), 0, 0);
		if (jsa != null) {
			for (int i = 0; i < jsa.length(); i++) {
				RadioButton rb = new RadioButton(getActivity());
				rb.setLayoutParams(lp);
				rb.setTextColor(getActivity().getResources().getColor(
						R.color.text_43_black));
				rb.setTextSize(14);
				rb.setButtonDrawable(getActivity().getResources().getDrawable(
						R.drawable.study_check_s));
				rb.setChecked(false);
				rb.setPadding(DhUtil.dip2px(getActivity(), 10), 0, 0, 0);
				try {
					rb.setText(jsa.getString(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				options.addView(rb);
			}
		}
	}

	private String transformLetter(int a) {
		String str = "A";
		switch (a) {
		case 0:
			str = "A";
			break;
		case 1:
			str = "B";
			break;
		case 2:
			str = "C";
			break;
		case 3:
			str = "D";
			break;
		case 4:
			str = "E";
			break;
		case 5:
			str = "F";
			break;
		case 6:
			str = "G";
			break;

		default:
			break;
		}
		return str;

	}

	// 提交试卷
	private void submit() {
		List<Map<String, String>> answer = QuestionbankActivity.answer;
		if (answer == null || answer.size() < 1) {
			Toast.makeText(getActivity(), "请选择答案", Toast.LENGTH_SHORT).show();
			return;
		}
		DhNet net = new DhNet(API.questionanser);
		net.addParam("answer", getAnswer(answer));
		net.addParam("contentid", getAnswerId(answer));
		net.doPostInDialog(new NetTask(getActivity()) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					Toast.makeText(getActivity(), "已成功提交", Toast.LENGTH_SHORT)
							.show();
					getActivity().finish();
					EventBus.getDefault().post(new QuestionEB());// 通知学习页面刷新
				}
				// TODO Auto-generated method stub
			}
		});
	}

	private String getAnswer(List<Map<String, String>> answer) {

		String result = "";
		for (int i = 0; i < answer.size(); i++) {
			Map<String, String> map = answer.get(i);
			if (TextUtils.isEmpty(result)) {
				result = answer.get(i).get("answer");
			} else {
				result = result + "," + answer.get(i).get("answer");
			}

		}

		return result;
	}

	private String getAnswerId(List<Map<String, String>> answer) {

		String result = "";
		for (int i = 0; i < answer.size(); i++) {
			Map<String, String> map = answer.get(i);
			if (TextUtils.isEmpty(result)) {
				result = answer.get(i).get("questionId");
			} else {
				result = result + "," + answer.get(i).get("questionId");
			}

		}

		return result;
	}

}