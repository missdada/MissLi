package com.example.demo.dal.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dal.entity.Customer;
@Mapper
public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}