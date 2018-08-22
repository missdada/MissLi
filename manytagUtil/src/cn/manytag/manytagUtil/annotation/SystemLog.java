package cn.manytag.manytagUtil.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

	public static final String SEPARATOR = ",";

	String module() default ""; //模块名称 用户管理

	String methods() default ""; //模块方法新增用户

	String description() default ""; //描述

	//需要记录的参数,逗号隔开
	String param() default "";
}