package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableSwagger2
@EnableEncryptableProperties
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringbootDatasourceApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDatasourceApplication.class, args);
	}

	// 重写配置消息转换器，使用FastJsonHttpMessageConverter第三方消息转换器
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
		messageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));// utf-8避免乱码
		// 设置jason转换时为空的字段展示空
		FastJsonConfig config = new FastJsonConfig();
		config.setFeatures(Feature.SupportNonPublicField);
		config.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);// 序列化特点
		messageConverter.setFastJsonConfig(config);
		converters.add(messageConverter);
	}
}
