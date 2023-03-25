package com.space.datat.common.utils;

/**
 * controller响应代码枚举
 */
public enum ResultCodeEnum {
    SUCCESS(1000, "成功"),
    FAIL(1001, "失败");
    public int code;
    public String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
