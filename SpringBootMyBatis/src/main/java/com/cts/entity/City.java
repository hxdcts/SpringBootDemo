package com.cts.entity;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="t_city")
public class City {
	private String id;
    private String name;
    private String state;
}

	