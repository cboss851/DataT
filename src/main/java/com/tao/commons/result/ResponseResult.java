package com.tao.commons.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 响应结果
 *
 * @Author：cboss
 * @Date：2023/3/25
 */
@Data
public class ResponseResult<T> implements Serializable {
    //响应代码
    private int code;
    //响应消息
    private String message;
    //接收开始时间
    private Date requestTime = new Date();
    //执行时间
    private Long executionTime;
    //请求ID
    private Object requestId;
    //响应体
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(String message, T data, ResponseCodeEnum resultCode) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = message;
    }

    public static ResponseResult success() {
        return new ResponseResult(ResponseCodeEnum.SUCCESS.getMessage(), null, ResponseCodeEnum.SUCCESS);
    }

    public static ResponseResult successMsg(String message) {
        return new ResponseResult(message, null, ResponseCodeEnum.SUCCESS);
    }

    public static ResponseResult successData(Object data) {
        return new ResponseResult(ResponseCodeEnum.SUCCESS.getMessage(), data, ResponseCodeEnum.SUCCESS);
    }

    public static ResponseResult success(String message, Object data) {
        return new ResponseResult(message, data, ResponseCodeEnum.SUCCESS);
    }

    public static ResponseResult fail() {
        return new ResponseResult(ResponseCodeEnum.SYSTEM_ERROR.getMessage(), null, ResponseCodeEnum.SYSTEM_ERROR);
    }

    public static ResponseResult failMsg(String message) {
        return new ResponseResult(message, null, ResponseCodeEnum.SYSTEM_ERROR);
    }

    public static ResponseResult failData(Object data) {
        return new ResponseResult(ResponseCodeEnum.SYSTEM_ERROR.getMessage(), data, ResponseCodeEnum.SYSTEM_ERROR);
    }

    public static ResponseResult fail(String message, Object data) {
        return new ResponseResult(message, data, ResponseCodeEnum.SYSTEM_ERROR);
    }

    public static ResponseResult failCode(ResponseCodeEnum resultCode) {
        return new ResponseResult(resultCode.getMessage(), null, resultCode);
    }

    public static ResponseResult failCode(String message, ResponseCodeEnum resultCode) {
        return new ResponseResult(message, null, resultCode);
    }
}