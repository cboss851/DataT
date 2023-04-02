package com.tao.datat.handler;

import com.tao.commons.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult exception(RuntimeException ex, HttpServletRequest request) {
        log.error("来源URL：{}", request.getRequestURL());
        log.error("业务异常：{}", ex);
        return ResponseResult.failMsg(ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult exception(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.error("来源URL：{}", request.getRequestURL());
        log.error("缺失必填参数：{}", ex.getLocalizedMessage());
        return ResponseResult.failMsg(String.format("缺失必填参数：%s", ex.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult exception(MethodArgumentNotValidException exception, HttpServletRequest request) {
        try {
            StringBuilder errorInfo = new StringBuilder();
            BindingResult bindingResult = exception.getBindingResult();
            for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
                if (i > 0) {
                    errorInfo.append(";");
                }
                FieldError fieldError = bindingResult.getFieldErrors().get(i);
                errorInfo.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage());
            }
            return ResponseResult.failMsg("方法参数无效：" + errorInfo.toString());
        } catch (Exception e) {
            log.error("来源URL：{}", request.getRequestURL());
            log.error("方法参数无效：{}", e);
            return ResponseResult.failMsg("方法参数无效：" + e.getMessage());
        }

    }

    @ExceptionHandler
    public ResponseResult exception(Exception ex, HttpServletRequest request) {
        log.error("来源URL：{}", request.getRequestURL());
        log.error("系统异常：{}", ex);
        return ResponseResult.failMsg("系统异常：" + ex.getMessage());
    }

}