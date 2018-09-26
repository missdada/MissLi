package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,WebMvcAutoConfiguration.class })
@MapperScan(basePackages = {"com.example.demo.dal.dao"})
public class SpringbootMycat2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMycat2Application.class, args);
	}
}
