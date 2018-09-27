package com.example.demo.dataSource;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.dataSource.annotation.DataSource;

@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {
	@Around("execution(* com.example.demo.service..*(..))")
	public Object switchDS(ProceedingJoinPoint point) throws Throwable {
		Class<?> className = point.getTarget().getClass();
		String dataSource = "primaryDataSource";

		if (className.isAnnotationPresent(DataSource.class)) {
			DataSource ds = className.getAnnotation(DataSource.class);
			dataSource = ds.value();
		} else {
			// 得到访问的方法对象
			String methodName = point.getSignature().getName();
			Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
			Method method = className.getMethod(methodName, argClass);
			// 判断是否存在@DS注解
			if (method.isAnnotationPresent(DataSource.class)) {
				DataSource annotation = method.getAnnotation(DataSource.class);
				// 取出注解中的数据源名
				dataSource = annotation.value();
			}
		}

		// 切换数据源
		DataSourceContextHolder.setDB(dataSource);
		try {
			return point.proceed();
		} finally {
			DataSourceContextHolder.clearDB();
		}
	}

}
