package com.means.shopping.bean;

import com.j256.ormlite.field.DatabaseField;

public class Good {
	@DatabaseField(generatedId = true)
	public Integer id;
	@DatabaseField
	public String name;
	@DatabaseField
	public Integer count = 0;
	@DatabaseField
	public Float price;
	@DatabaseField
	public Long goodId;
	@DatabaseField
	public Integer goodType;

	public Good() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	

	public Integer getGoodType() {
		return goodType;
	}

	public void setGoodType(Integer goodType) {
		this.goodType = goodType;
	}

	public boolean equals(Object obj) {
		if ((obj instanceof Good)) {
			return ((Good) obj).getGoodId().equals(getGoodId());
		}
		return false;
	}

}
