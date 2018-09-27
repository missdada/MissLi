package com.example.demo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class RedisService {
	
	@Cacheable(value = "dataCenter", key = "#root.methodName + '_' + #root.methodName.concat(#id)")
	public String get(Long id){
		System.out.println("abc"+id+"===");
		return "abc"+id;
	}

}
