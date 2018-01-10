package com.comven.exmaple;

import com.comven.example.App;
import com.comven.example.mapper.UserInfoMapper;
import com.comven.example.pojo.UserInfo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class AppTest {
	Logger log = LoggerFactory.getLogger(AppTest.class);
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Test
	public void testDynamicDatasource() {
		UserInfo userInfo;
		for (int i = 1; i <= 2; i++) {
			//i为奇数时调用selectByOddUserId方法获取，i为偶数时调用selectByEvenUserId方法获取
			userInfo = i % 2 == 1 ? userInfoMapper.selectByOddUserId(i) : userInfoMapper.selectByEvenUserId(i);
			log.info("{}->={}", userInfo.getId(),userInfo.getRemarks());
		}
	}
}
