package com.means.shopping.adapter;

import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.adapter.CampusCityAdapter.ViewHolder;

public class CatAdapter extends BaseAdapter {
	Context mContext;

	LayoutInflater mLayoutInflater;
	
	JSONArray jsa;
	
	int select_id = 0;

	public CatAdapter(Context context) {
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
			convertView = mLayoutInflater.inflate(R.layout.item_market_cat,
					null);
			holder.catName = (TextView) convertView.findViewById(R.id.name);
			holder.layout = (RelativeLayout) convertView.findViewById(R.id.bg);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		JSONObject jo = getItem(position);
		holder.catName.setText(JSONUtil.getString(jo, "name"));
		if (select_id == position) {
			holder.layout.setBackgroundResource(R.color.white);
		}else {
			holder.layout.setBackgroundResource(R.color.campus_grey);
		}
		
		// TODO Auto-generated method stub
		return convertView;
	}
	
	class ViewHolder{
		TextView catName;
		RelativeLayout layout;
	}
	
	public void setData(JSONArray jsa){
		this.jsa = jsa;
		notifyDataSetChanged();
	}
	
	public void select(int select){
		this.select_id = select;
		notifyDataSetChanged();
	}

}
