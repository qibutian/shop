package com.means.shopping.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import net.duohuo.dhroid.net.JSONUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.means.shopping.R;

public class CampusCityAdapter extends BaseAdapter{
	Context mContext;

	LayoutInflater mLayoutInflater;
	
	JSONArray jsa;
	
	int select_id = 0;

	public CampusCityAdapter(Context context) {
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
			convertView = mLayoutInflater.inflate(R.layout.item_campus_city,
					null);
			holder.cityname = (TextView) convertView.findViewById(R.id.city_name);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		JSONObject jo = getItem(position);
		holder.cityname.setText(JSONUtil.getString(jo, "name"));
		if (select_id == position) {
			holder.layout.setBackgroundResource(R.color.white);
		}else {
			holder.layout.setBackgroundResource(R.color.campus_grey);
		}
		
		// TODO Auto-generated method stub
		return convertView;
	}
	
	class ViewHolder{
		TextView cityname;
		LinearLayout layout;
	}
	
	public void setDate(JSONArray jsa){
		this.jsa = jsa;
		notifyDataSetChanged();
	}
	
	public void select(int select){
		this.select_id = select;
		notifyDataSetChanged();
	}
}
