package com.example.demo.dal.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dal.entity.CEmp;
@Mapper
public interface CEmpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CEmp record);

    int insertSelective(CEmp record);

    CEmp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CEmp record);

    int updateByPrimaryKey(CEmp record);
}