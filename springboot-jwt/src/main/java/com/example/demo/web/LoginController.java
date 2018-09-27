package com.example.demo.web;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.bo.ResultModel;
import com.example.demo.common.bo.TokenObject;
import com.example.demo.common.utils.JwtHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "LoginController")
@RequestMapping("/api")
@Controller
public class LoginController {

	@ApiOperation(value = "登录测试方法", notes = "登录测试方法")
	@RequestMapping(value = "/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResultModel<String> login(@RequestBody String userName) {
		System.out.println("用户名："+userName);
		// 1、业务逻辑
		// 2、返回token
		TokenObject token = new TokenObject();
		token.setBase64Secret("test");
		token.setClientId(userName);
		token.setExpiresSecond(new Date().getTime() + 5*60 * 1000);
		return ResultModel.success(JwtHelper.createJWTByObj(token));
	}

}
