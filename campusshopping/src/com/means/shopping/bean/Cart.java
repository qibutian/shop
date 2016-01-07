package com.means.shopping.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.duohuo.dhroid.ioc.IocContainer;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

public class Cart {

	private List<Good> goodList;
	Dao<Good, Integer> goodDao;
	static Cart instance;

	public static Cart getInstance() {
		if (instance == null) {
			instance = new Cart();
		}

		return instance;
	}

	public Cart() {
		OrmLiteSqliteOpenHelper daoHelper = IocContainer.getShare().get(
				OrmLiteSqliteOpenHelper.class);
		try {
			goodDao = daoHelper.getDao(Good.class);
			goodList = goodDao.queryForAll();
			if (goodList == null) {
				goodList = new ArrayList<Good>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取或新增商品
	 * 
	 * @param goodid
	 * @return
	 */
	public Good getOrCreateGood(Long goodid) {
		Good good = new Good();
		good.setGoodId(goodid);
		// good.setName(name);

		int i = goodList.indexOf(good);
		if (i != -1) {
			Good oldgood = goodList.get(i);
			oldgood.setCount(1 + oldgood.getCount());
			try {
				goodDao.update(good);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			good.setCount(1);
			goodList.add(good);
			try {
				goodDao.create(good);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return good;
	}

	/**
	 * 获取商品
	 * 
	 * @param goodid
	 * @return
	 */
	public Good getGood(Long id) {
		for (Iterator iterator = goodList.iterator(); iterator.hasNext();) {
			Good Good = (Good) iterator.next();
			if (Good.getGoodId().equals(id)) {
				return Good;
			}
		}
		return null;
	}

	/**
	 * 删除商品
	 * 
	 * @param goodid
	 * @return
	 */
	public void removeGood(Long goodId) {
		Good good = new Good();
		good.setGoodId(goodId);
		int i = this.goodList.indexOf(good);
		if (i != -1) {
			try {
				goodDao.delete(goodList.get(i));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.goodList.remove(i);
		}
	}

	/**
	 * 删减商品
	 * 
	 * @param goodid
	 * @return
	 */
	public void reduceGood(Long goodId) {
		Good good = new Good();
		good.setGoodId(goodId);
		int i = this.goodList.indexOf(good);
		if (i != -1) {
			Good oldgood = this.goodList.get(i);
			oldgood.setCount(oldgood.getCount() - 1);
			if (oldgood.getCount() == 0) {
				removeGood(good.getGoodId());
			} else {
				try {
					goodDao.update(oldgood);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public List<Good> getGoods() {
		return goodList;
	}

	/**
	 * 清空所有商品
	 * 
	 * @param goodid
	 * @return
	 */
	public void clear() {
		goodList.clear();
	}

	/**
	 * 获取商品总额
	 * 
	 * @param goodid
	 * @return
	 */
	public float getMoney() {
		float price = 0;
		for (Iterator<Good> iterator = goodList.iterator(); iterator.hasNext();) {
			Good good = (Good) iterator.next();
			price = price + good.getCount() * good.getPrice();
		}
		return price;
	}

	public int getCount() {
		int count = 0;
		for (Iterator<Good> iterator = goodList.iterator(); iterator.hasNext();) {
			Good good = (Good) iterator.next();
			Integer goods = good.getCount();
			count += goods != null ? goods : 0;
		}
		return count;
	}

}
