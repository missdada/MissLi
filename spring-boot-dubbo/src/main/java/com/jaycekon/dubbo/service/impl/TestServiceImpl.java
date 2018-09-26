package com.jaycekon.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jaycekon.dubbo.domain.User;
import com.jaycekon.dubbo.service.TestService;
@Service
public class TestServiceImpl implements TestService {

	@Override
	public User saveUser(User user) {
		System.out.println(user.toString());
		return user;
	}

}
