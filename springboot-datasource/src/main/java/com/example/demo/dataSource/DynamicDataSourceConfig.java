package com.example.demo.dataSource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DynamicDataSourceConfig {
	@Bean(name = "primaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid.primary")
	public DataSource primaryDataSource() {
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		dataSource.setName("primaryDataSource");
		return dataSource;
	}

	@Bean(name = "secondDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid.second")
	public DataSource secondDataSource() {
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		dataSource.setName("secondDataSource");
		return dataSource;
	}
	
	 /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(primaryDataSource());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap<Object, Object>(2);
        dsMap.put("primaryDataSource", primaryDataSource());
        dsMap.put("secondDataSource", secondDataSource());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

}
