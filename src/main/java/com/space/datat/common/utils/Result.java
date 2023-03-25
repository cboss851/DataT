package com.space.datat.common.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.space.datat.common.utils.ResultCodeEnum.*;

/**
 * controller响应结果
 *
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {
    //响应代码
    private int code = ResultCodeEnum.SUCCESS.getCode();
    //响应消息
    private String message = ResultCodeEnum.SUCCESS.getMessage();
    //响应时间
    private Date time = new Date();
    //连接时长
    private Long connectTime = 0L;
    //请求体
    private Object reqBody;
    //响应体
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(T data, ResultCodeEnum resultCode) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public Result(String message, T data, ResultCodeEnum resultCode) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = message;
    }

    public static Result success(Object data) {
        return new Result(data);
    }

    public static Result fail(String message) {
        return new Result(message, null, ResultCodeEnum.FAIL);
    }

    public static Result success() {
        return new Result();
    }
}
