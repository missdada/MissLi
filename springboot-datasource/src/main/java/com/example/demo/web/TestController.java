package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "TestController", description = "test接口")
@RequestMapping("/api")
@RestController
public class TestController {
	@Autowired
	private TestService testService;
	
	@ApiOperation(value="test1", notes="基本测试方法")
	@GetMapping("/test1")
	public Object test(Integer id){
		return testService.getEmp(id);
	}
	
	@ApiOperation(value="test2", notes="基本测试方法")
	@GetMapping("/test2")
	public Object test2(Integer id){
		return testService.getEmp2(id);
	}
	
	@ApiOperation(value="test3", notes="基本测试方法")
	@GetMapping("/test3")
	public Object test3(Integer id){
		return testService.getEmp3(id);
	}

}
