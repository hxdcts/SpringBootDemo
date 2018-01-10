package com.cts.spring.boot.demo.controller;

import org.apache.commons.logging.Log;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.spring.boot.demo.bean.Person;
import com.cts.spring.boot.demo.dao.IPersonDao;

@RestController
public class EncryptController {
	Logger log = LoggerFactory.getLogger(EncryptController.class);
	@Autowired
    StringEncryptor stringEncryptor;//密码解码器自动注入
	@Value("${spring.datasource.password}")
    private String password;
	@Autowired
	IPersonDao personDao;
	@RequestMapping("/test")
	public String test(){
	 	Person person =  personDao.getPersonById();
	 	log.info(" == "+ person.getFirst_name());
		String result = stringEncryptor.encrypt("root");
		String decrypt = stringEncryptor.decrypt(result);
		return result+"="+decrypt+"数据库连接密码："+password;
	}
}
