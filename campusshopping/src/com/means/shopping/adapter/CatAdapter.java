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

import com.means.shopping.R;

public class CatAdapter extends BaseAdapter {
	Context mContext;

	LayoutInflater mLayoutInflater;

	JSONArray jsa;

	int checkPosition = -1;

	public CatAdapter(Context context) {
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	public void setData(JSONArray jsa) {
		this.jsa = jsa;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (jsa == null) {
			return 0;
		}

		return jsa.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return jsa.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setBg(int position) {
		checkPosition = position;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_market_cat,
					null);
		}

		JSONObject jo = getItem(position);
		ViewUtil.bindView(convertView.findViewById(R.id.name),
				JSONUtil.getString(jo, "name"));

		View bgV = convertView.findViewById(R.id.bg);

		if (checkPosition == position) {
			bgV.setBackgroundColor(mContext.getResources().getColor(
					R.color.white));
		} else {
			bgV.setBackgroundColor(mContext.getResources().getColor(
					R.color.nothing));
		}

		// TODO Auto-generated method stub
		return convertView;
	}

}
