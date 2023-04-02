package com.tao.commons.result;

/**
 * controller响应代码枚举
 */
public enum ResponseCodeEnum {
    SUCCESS(200, "成功"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "签名验证失败"),
    NO_AUTH(403, "无接口相关权限"),
    NOT_FOUND(404, "请求的资源不存在"),//查询的id或者请求URL是否正确
    SYSTEM_ERROR(500, "系统异常"),//错误指引进行重试
    SERVICE_NOT_ONLINE(502, "服务下线，暂时不可用"),//请求无法处理，请稍后重试
    SERVICE_UNAVAILABLE(503, "服务不可用，过载保护");//请求无法处理，请稍后重试
    public int code;
    public String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
