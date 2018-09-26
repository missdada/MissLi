package com.example.demo.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dal.dao.CustomerMapper;
import com.example.demo.dal.dao.TravelrecordMapper;
import com.example.demo.dal.entity.Travelrecord;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class TestController {
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private TravelrecordMapper travelrecordMapper;
	
	@ApiOperation("测试https")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
    public String overview(){
        return "use ssl set http change https";
    }
	
	@ApiOperation("获取")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object getCustomer(){
        return customerMapper.selectByPrimaryKey(2).getName();
    }
	
	@ApiOperation("添加")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
    public Object getCustomer(@RequestBody Travelrecord record){
        return travelrecordMapper.insert(record);
    }


}
