package com.means.shopping.adapter;

import net.duohuo.dhroid.net.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.activity.study.JobParticularsActivity;

public class JobStatusAdapter extends BaseExpandableListAdapter{
	
	Context mContext;

	LayoutInflater mLayoutInflater;
	
	JSONArray jsa;
	
	public JobStatusAdapter(Context context) {
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	public void setData(JSONArray jsa){
		this.jsa = jsa;
		notifyDataSetChanged();
	}

	@Override
	public JSONObject getChild(int groupPosition, int childPosition) {
		JSONObject jo = getGroup(groupPosition);
		JSONArray jsa = JSONUtil.getJSONArray(jo, "_child");
		if (jsa==null) {
			return null;
		}
		return JSONUtil.getJSONObjectAt(jsa, childPosition);
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder holder = null;
		if (convertView == null) {
			holder = new ChildHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_job_particulars, null);
			holder.jobname = (TextView) convertView.findViewById(R.id.jobname);
			holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
			convertView.setTag(holder);
		}else {
			holder = (ChildHolder) convertView.getTag();
		}
		JSONObject jo = getChild(groupPosition, childPosition);
		holder.jobname.setText(JSONUtil.getString(jo, "name"));
		
		holder.layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(mContext,JobParticularsActivity.class);
				
				mContext.startActivity(it);
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		JSONObject jo = getGroup(groupPosition);
		JSONArray jsa = JSONUtil.getJSONArray(jo, "_child");
		if (jsa==null) {
			return 0;
		}
		// TODO Auto-generated method stub
		return jsa.length();
	}

	@Override
	public JSONObject getGroup(int groupPosition) {
		if (jsa==null) {
			return null;
		}
		return JSONUtil.getJSONObjectAt(jsa, groupPosition);
	}

	@Override
	public int getGroupCount() {
		if (jsa==null) {
			return 0;
		}
		return jsa.length();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder holder = null;
		if (convertView == null) {
			holder = new GroupHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_job_status, null);
			holder.jobname = (TextView) convertView.findViewById(R.id.jobname);
			
			convertView.setTag(holder);
		}else {
			holder = (GroupHolder) convertView.getTag();
		}
		JSONObject jo = getGroup(groupPosition);
		holder.jobname.setText(JSONUtil.getString(jo, "name"));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	class ChildHolder{
		TextView jobname;
		RelativeLayout layout;
	}

	class GroupHolder{
		TextView jobname;
	}
}
