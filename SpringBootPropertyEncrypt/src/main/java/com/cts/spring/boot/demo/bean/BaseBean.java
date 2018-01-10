package com.cts.spring.boot.demo.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseBean implements Serializable{
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
