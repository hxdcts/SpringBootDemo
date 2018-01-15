package com.cts.SpringBootMyBatis;


import com.cts.App;
import com.cts.entity.City;
import com.cts.service.CityService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class ApplicationTests {

    @Autowired
    private CityService cityMapper;

    @Test
    public void contextLoads() {
    	List<City> list = cityMapper.getAll();
    	log.info(list.size()+"***************");
    	City city = new City();
    	city.setId("1");
    	City city2= cityMapper.getCityByIdN(city);
    	log.info(" city = {}",city2.toString());
    }

}
	