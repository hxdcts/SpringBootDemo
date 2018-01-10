package com.cts.spring.boot.demo.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mockito.asm.tree.IntInsnNode;

import com.cts.spring.boot.demo.bean.Person;
import com.cts.spring.boot.demo.util.TargetDataSource;

@Mapper
public interface IPersonDao {
	public Person getPersonById();
	   /**
     * 从test1数据源中获取用户信息
     */
    Person selectByOddUserId(int id);
    /**
     * 从test2数据源中获取用户信息
     */
    @TargetDataSource("test2")
    Person selectByEvenUserId(int id);
}
