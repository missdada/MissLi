package com.example.demo.dal.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dal.entity.Travelrecord;
@Mapper
public interface TravelrecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Travelrecord record);

    int insertSelective(Travelrecord record);

    Travelrecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Travelrecord record);

    int updateByPrimaryKey(Travelrecord record);
}