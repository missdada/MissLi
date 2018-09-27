package com.example.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.bo.ResultModel;
import com.example.demo.common.utils.AuthUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "TestTokenController")
@RequestMapping("/testtoken")
@RestController
public class TestTokenController {

	@ApiOperation(value = "token测试方法", notes = "token测试方法")
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResultModel<String> test(String p,HttpServletRequest request) {
		return ResultModel.success(AuthUtil.getClinetIdByAuth(request));
	}

}
