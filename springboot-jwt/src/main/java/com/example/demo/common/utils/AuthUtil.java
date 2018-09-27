package com.example.demo.common.utils;

import javax.servlet.http.HttpServletRequest;

public class AuthUtil {
	public static String getClinetIdByAuth(HttpServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String auth = httpRequest.getHeader("Authorization");
		auth = auth.substring(7, auth.length());
		return JwtHelper.getClinetIdByJWT(auth);
	}

}
