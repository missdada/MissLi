package com.example.demo.common.enums;

public enum  ResultEnum {
	INVALID_CLIENTID(30003, "Invalid clientid"),  
	INVALID_PASSWORD(30004, "User name or password is incorrect"),  
	INVALID_CAPTCHA(30005, "Invalid captcha or captcha overdue"),  
	INVALID_TOKEN(30006, "Invalid token"),
	INVALID_SINGTIMEOUT(30007, "timeout"),
	INVALID_PERMISSION_DENIED(30008, "permission denied"),
	OK(30000, "GET TOKEN"); 

    private int code;

    private String desc;

    ResultEnum(int i, String s) {
        this.code = i;
        this.desc = s;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
