package com.cts.spring.boot.demo.test;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestBootTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        //自己在用的时候更改此密码
        config.setPassword("e!Jd&ljyJ^e4I5oU");
        //应用配置
        encryptor.setConfig(config);
        String encryptorResult= encryptor.encrypt("root");
        System.out.println(encryptorResult);
        
        String decryptResult =  encryptor.decrypt("BjTeKYrhGjTixlMjuacy8Q==");
        System.out.println(decryptResult);
	}
}
