package com.means.shopping.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter{

	List<Fragment> frags;
	public SimpleFragmentPageAdapter(FragmentManager fm,List<Fragment> frags) {
		super(fm);
		this.frags = frags;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		if (frags!=null) {
			return frags.get(arg0);
		}
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount() {
		if (frags!=null) {
			return frags.size();
		}
		// TODO Auto-generated method stub
		return 0;
	}

}
