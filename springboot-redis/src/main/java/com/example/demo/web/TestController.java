package com.example.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RedisService;
@RestController
@RequestMapping("/api")
public class TestController {
	
	/**login show测试session共享**/
	@RequestMapping("login")
    public void login(HttpServletRequest request,String name) {
        request.getSession().setAttribute("username", name);
    }
	@RequestMapping("show")
	public Object show(HttpServletRequest request) {
	    return request.getSession().getAttribute("username");
	}
	
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("get")
	public Object get(HttpServletRequest request) {
	    return redisService.get2(8888l);
	}
}
