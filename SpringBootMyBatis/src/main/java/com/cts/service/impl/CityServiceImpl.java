package com.cts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.entity.City;
import com.cts.mapper.CityMapper;
import com.cts.service.CityService;
import com.github.pagehelper.PageHelper;
@Service
public class CityServiceImpl implements CityService{
	@Autowired
    private CityMapper cityDao;

    @Override
    public List<City> getAll() {
        PageHelper.startPage(1,1);
        return cityDao.selectAll();
    }
	@Override
	public City getCityByIdN(City city) {
		return cityDao.selectOne(city);
	}
    
    
}

	