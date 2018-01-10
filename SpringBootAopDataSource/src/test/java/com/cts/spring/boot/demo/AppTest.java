package com.cts.spring.boot.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.spring.boot.demo.bean.Person;
import com.cts.spring.boot.demo.dao.IPersonDao;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class AppTest {
	Logger log = LoggerFactory.getLogger(AppTest.class);
    @Autowired
    private IPersonDao personDao;

    @Test
    public void testDynamicDatasource() {
        Person userInfo;
        for (int i = 1; i <= 2; i++) {
            //i为奇数时调用selectByOddUserId方法获取，i为偶数时调用selectByEvenUserId方法获取
            userInfo = i % 2 == 1 ? personDao.selectByOddUserId(i) : personDao.selectByEvenUserId(i);
            log.info("{}->={}", userInfo.getId(),userInfo.getFirst_name());
        }
        
    }
}
