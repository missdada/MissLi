package cn.manytag.manytagUtil.springMvc.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class IsUrlSuccessInterceptor {

	public static final String IS_URL_SUCCESS = "isUrlSuccess";
	public static final String RE_STRING = "reString";
	private static Logger log = Logger.getLogger(IsUrlSuccessInterceptor.class);

	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		Object obj = joinPoint.proceed();
		if (sra != null) {
			HttpServletRequest request = sra.getRequest();

			String url = request.getRequestURL()
					+ (request.getQueryString() == null ? "" : "?" + request.getQueryString());
			log.debug(url);

			request.setAttribute(IS_URL_SUCCESS, true);
			if (obj instanceof String) {
				request.setAttribute(RE_STRING, String.valueOf(obj));
			}
		}
		return obj;
	}

}
