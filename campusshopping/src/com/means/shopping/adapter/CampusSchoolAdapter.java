package com.means.shopping.adapter;

import net.duohuo.dhroid.net.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.means.shopping.R;

public class CampusSchoolAdapter extends BaseAdapter{
	Context mContext;

	LayoutInflater mLayoutInflater;
	
	JSONArray jsa;

	public CampusSchoolAdapter(Context context) {
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (jsa!=null) {
			return jsa.length();
		}
		return 0;
	}

	@Override
	public JSONObject getItem(int position) {
		if (jsa!=null) {
			JSONObject jo = JSONUtil.getJSONObjectAt(jsa, position);
			return jo;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_campus_school,
					null);
			holder.schoolname = (TextView) convertView.findViewById(R.id.school_name);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		JSONObject jo = getItem(position);
		holder.schoolname.setText(JSONUtil.getString(jo, "name"));

		// TODO Auto-generated method stub
		return convertView;
	}
	
	class ViewHolder{
		TextView schoolname;
	}
	
	public void setDate(JSONArray jsa){
		this.jsa = jsa;
		notifyDataSetChanged();
	}
}
