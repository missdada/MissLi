package cn.manytag.manytagUtil.springMvc.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.manytag.manytagUtil.util.DateUtils;

public class MyExceptionResolver implements HandlerExceptionResolver {

	private static final Logger log = Logger.getLogger(MyExceptionResolver.class);

	/**
	 * 进行全局异常的过滤和处理
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		//handler为当前处理器适配器执行的对象

		log.error("↓", e);

		//跳转到相应的处理页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("msg", e.getClass().toString().substring(6) + (e.getMessage() == null ? "" : (" ： " + e.getMessage())));
		modelAndView.addObject("date", DateUtils.format(new Date()));
		modelAndView.setViewName("main/error");
		return modelAndView;
	}
}
