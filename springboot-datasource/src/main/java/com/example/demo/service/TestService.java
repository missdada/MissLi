package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.dal.dao.CEmpMapper;
import com.example.demo.dal.entity.CEmp;
import com.example.demo.dataSource.DataSourceContextHolder;
import com.example.demo.dataSource.annotation.DataSource;

@Service
public class TestService {
	private static final Logger log = LoggerFactory.getLogger(TestService.class);
	@Autowired
	private CEmpMapper empMapper;
	
	@Cacheable(value = "dataCenter", key = "#id")
	@DataSource("secondDataSource")
	public String getEmp(Integer id){
		System.out.println(empMapper.selectByPrimaryKey(id).getName());
		return empMapper.selectByPrimaryKey(id).getName();
	}
	
	//@DataSource("primaryDataSource")
	public String getEmp2(Integer id){
		System.out.println(empMapper.selectByPrimaryKey(id).getName());
		return empMapper.selectByPrimaryKey(id).getName();
	}
	
	@DataSource("primaryDataSource")
	public CEmp getEmp3(Integer id){
		System.out.println(empMapper.selectByPrimaryKey(id).getName());
		return empMapper.selectByPrimaryKey(id);
	}

}
