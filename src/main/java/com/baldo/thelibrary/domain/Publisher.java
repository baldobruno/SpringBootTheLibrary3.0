package com.baldo.thelibrary.domain;

import javax.persistence.Entity;

@Entity
public class Publisher extends AbstractDomainClass {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}