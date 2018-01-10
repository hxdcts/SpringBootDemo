package com.cts.spring.boot.demo.dao;


import org.apache.ibatis.annotations.Mapper;

import com.cts.spring.boot.demo.bean.Person;

@Mapper
public interface IPersonDao {
	public Person getPersonById();
}
