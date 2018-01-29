package com.cts.service.impl;

import org.springframework.stereotype.Service;

import com.cts.service.HelloService;
@Service
public class HelloServiceImpl implements HelloService{

	@Override
	public String hello(String name, String date) {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("name = ").append(name).append(" date = ").append(date);
		return sbBuffer.toString();
			
	}

}

	