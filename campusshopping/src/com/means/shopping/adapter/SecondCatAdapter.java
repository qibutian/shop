package com.means.shopping.adapter;

import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.ViewUtil;
import net.duohuo.dhroid.view.YToXImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.means.shopping.R;
import com.means.shopping.adapter.CampusCityAdapter.ViewHolder;

public class SecondCatAdapter extends BaseAdapter {
	Context mContext;

	LayoutInflater mLayoutInflater;
	
	JSONArray jsa;
	
	int select_id = 0;

	public SecondCatAdapter(Context context) {
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
			convertView = mLayoutInflater.inflate(R.layout.item_second_cat,
					null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.pic = (YToXImageView) convertView.findViewById(R.id.pic);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		JSONObject jo = getItem(position);
		holder.name.setText(JSONUtil.getString(jo, "name"));
		ViewUtil.bindNetImage(holder.pic,JSONUtil.getString(jo, "pic"), "default");
		
		// TODO Auto-generated method stub
		return convertView;
	}
	
	class ViewHolder{
		YToXImageView pic;
		TextView name;
	}
	
	public void setDate(JSONArray jsa){
		this.jsa = jsa;
		notifyDataSetChanged();
	}
	
}
