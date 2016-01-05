package com.means.shopping.bean;

import com.j256.ormlite.field.DatabaseField;

public class Good {
	@DatabaseField
	public Long id;
	@DatabaseField
	public String name;
	@DatabaseField
	public Integer count;
	@DatabaseField
	public Float price;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean equals(Object obj) {
		if ((obj instanceof Good)) {
			return ((Good) obj).getId().equals(getId());
		}
		return false;
	}

}
