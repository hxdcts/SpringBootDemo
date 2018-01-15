package com.cts.service;

import java.util.List;

import com.cts.entity.City;

public interface CityService {
	List<City> getAll();
	City getCityByIdN(City city);
}

	