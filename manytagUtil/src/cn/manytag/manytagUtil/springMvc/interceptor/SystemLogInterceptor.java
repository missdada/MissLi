package cn.manytag.manytagUtil.springMvc.interceptor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import cn.manytag.manytagUtil.annotation.SystemLog;

public class SystemLogInterceptor {

	private static Logger log = Logger.getLogger(SystemLogInterceptor.class);

	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

		Object obj = joinPoint.proceed();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SystemLog systemLog = method.getAnnotation(SystemLog.class);
		if (systemLog == null) {
			return obj;
		}

		String de = systemLog.module() + " - " + systemLog.methods() + " : " + systemLog.description();
		String me = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

		String paramType = "";
		for (Class<?> c : signature.getParameterTypes()) {
			paramType += c.getName() + ", ";
		}
		paramType = paramType.substring(0, paramType.length() - 2);

		String paramName = "";
		for (String s : signature.getParameterNames()) {
			paramName += s + ", ";
		}
		paramName = paramName.substring(0, paramName.length() - 2);

		String param = "";
		for (Object o : joinPoint.getArgs()) {
			param += o + ", ";
		}
		param = param.substring(0, param.length() - 2);

		String str = de + "\n" + me + "\n" + paramType + "\n" + paramName + "\n" + param + "\n" + obj;
		log.info(str);

		return obj;
	}

}
