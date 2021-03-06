package com.jaycekon.dubbo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jaycekon.dubbo.domain.City;
import com.jaycekon.dubbo.domain.User;
import org.springframework.stereotype.Component;

/**
 * 城市 Dubbo 服务消费者
 * <p>
 * Created by Jaycekon on 20/09/2017.
 */
@Component
public class CityDubboConsumerService {

    @Reference
    CityDubboService cityDubboService;

    @Reference
    UserService userService;
    
    @Reference
    TestService testService;

    public void printCity() {
        String cityName = "广州";
        City city = cityDubboService.findCityByName(cityName);
        System.out.println(city.toString());
    }


    public User saveUser() {
        User user = new User();
        user.setUsername("jaycekon");
        user.setPassword("jaycekong824");
        return userService.saveUser(user);
    }
    
    public User saveUser1() {
    	 User user = new User();
    	 user.setPassword("q1q1");
    	 user.setUsername("rewrew");
        return testService.saveUser(user);
    }
}
