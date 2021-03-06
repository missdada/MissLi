package com.example.demo.common.enums;

public enum  CodeEnum {
    //成功
    SUCCESS(200, "success"),
    //客户端错误
    CLIENT_ERROR(400, "client error"),
    //服务端错误
    SERVER_ERROR(500, "server error");

    private int code;

    private String desc;

    CodeEnum(int i, String s) {
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
