package com.example.demo.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import com.example.demo.common.enums.ResultEnum;
import com.example.demo.common.utils.JwtHelper;
import com.fasterxml.jackson.databind.ObjectMapper;


@Order(value = 1) 
@WebFilter(filterName = "colationFilter", urlPatterns = "/testtoken/*")
public class HTTPBasicAuthorizeAttribute implements Filter {

	private Logger logger = LoggerFactory.getLogger(HTTPBasicAuthorizeAttribute.class);

	@Override
	public void destroy() {
		logger.info("后台token过滤器,溜了溜了溜了溜了");
		// 可以日志管理添加
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("后台token过滤器检测");
		// 1.检测当前是否需要重新登录
		// if(audience!=null){
		// if (audience.getClientId().equals(StaticInfo.SIGNOUT)){
		// toResponse((HttpServletResponse) response,1,(HttpServletRequest)
		// request);
		// return;
		// }
		// }
		// 2.检测请求同token信息
		ResultEnum resultStatusCode = checkHTTPBasicAuthorize(request);
		if (resultStatusCode.equals(ResultEnum.INVALID_SINGTIMEOUT)) {// 超时
			toResponse((HttpServletResponse) response, 2, (HttpServletRequest) request);
			return;
		} else if (resultStatusCode.equals(ResultEnum.INVALID_PERMISSION_DENIED)) {// 权限不够
			toResponse((HttpServletResponse) response, 0, (HttpServletRequest) request);
			return;
		}
		logger.info("后台token过滤器检测通过");
		chain.doFilter(request, response);
	}

	/**
	 * 响应
	 * 
	 * @param response
	 * @param i
	 *            类型
	 * @throws IOException
	 */
	private void toResponse(HttpServletResponse response, int i, HttpServletRequest request) throws IOException {
		HttpServletResponse httpResponse = response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PATCH,PUT");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers",
				"Origin,X-Requested-With,x-requested-with,X-Custom-Header," + "Content-Type,Accept,Authorization");
		String method = request.getMethod();
		if ("OPTIONS".equalsIgnoreCase(method)) {
			logger.info("OPTIONS请求");
			httpResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
		}

		ObjectMapper mapper = new ObjectMapper();
		PrintWriter writer = httpResponse.getWriter();
		// if (i==1)
		// writer.write(mapper.writeValueAsString(ResultEnum.RESTARTLOGIN));
		if (i == 2)
			writer.write(mapper.writeValueAsString(ResultEnum.INVALID_SINGTIMEOUT));
		else if (i == 0)
			writer.write(mapper.writeValueAsString(ResultEnum.INVALID_PERMISSION_DENIED));

		writer.close();

		if (writer != null)
			writer = null;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("后台token过滤器启动");
	}

	/**
	 * 检测请求同token信息
	 * 
	 * @param request
	 * @return
	 */
	private ResultEnum checkHTTPBasicAuthorize(ServletRequest request) {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String auth = httpRequest.getHeader("Authorization");
			if ((auth != null) && (auth.length() > 7)) {
				String HeadStr = auth.substring(0, 6).toLowerCase();
				/*if (HeadStr.compareTo("basic") == 0) {
					auth = auth.substring(6, auth.length());
					int i = JwtHelper.checkToken(auth, httpRequest);
					if (i == 1) {
						return ResultEnum.OK;
					} else if (i == 2) {
						return ResultEnum.INVALID_SINGTIMEOUT;
					} else if (i == 0) {
						return ResultEnum.INVALID_PERMISSION_DENIED;
					}
				}*/
				if (HeadStr.compareTo("bearer") == 0) {

					auth = auth.substring(7, auth.length());
					int i = JwtHelper.checkToken(auth, httpRequest);
					if (i == 1) {
						return ResultEnum.OK;
					} else if (i == 2) {
						return ResultEnum.INVALID_SINGTIMEOUT;
					} else if (i == 0) {
						return ResultEnum.INVALID_PERMISSION_DENIED;
					}
				}
			}
			return ResultEnum.INVALID_PERMISSION_DENIED;
		} catch (Exception ex) {
			return ResultEnum.INVALID_PERMISSION_DENIED;
		}

	}

}
