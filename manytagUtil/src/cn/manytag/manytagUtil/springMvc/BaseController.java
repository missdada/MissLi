package cn.manytag.manytagUtil.springMvc;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import cn.manytag.manytagUtil.log.Log4jUtil;
import cn.manytag.manytagUtil.util.DateUtils;
import cn.manytag.manytagUtil.web.CookieUtil;

public class BaseController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 271020621258494028L;

	public static final String DATE_FORMAT = DateUtils.yyyyMMddHHmmss;
	protected static final Logger log = Logger.getLogger(BaseController.class);
	protected static final CustomDateEditor cde = new CustomDateEditor(new SimpleDateFormat(DATE_FORMAT), true);

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, cde);
	}

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		if (Log4jUtil.isDebug()) {
			log.debug("session+++++++++++++++++++++++++++++++++++++++++++++");
			Enumeration<String> sessionNames = session.getAttributeNames();
			while (sessionNames.hasMoreElements()) {
				String str = sessionNames.nextElement();
				log.debug(str + ":\n" + session.getAttribute(str));
			}
		}
	}

	protected Cookie getCookie(String str) {
		return CookieUtil.getCookie(request, str);
	}
}
