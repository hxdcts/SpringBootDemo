package com.comven.example.mapper;

import com.comven.example.annotation.TargetDataSource;
import com.comven.example.pojo.UserInfo;


public interface UserInfoMapper {
	/**
	 * 从test1数据源中获取用户信息
	 */
	//@TargetDataSource("test1")
	UserInfo selectByOddUserId(Integer id);
	/**
	 * 从test2数据源中获取用户信息
	 */
	@TargetDataSource("test2")
	UserInfo selectByEvenUserId(Integer id);
}