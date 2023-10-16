package org.example.points.advice;

import lombok.extern.slf4j.Slf4j;
import org.example.points.common.exception.BusinessException;
import org.example.points.common.vo.CommonResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <h2>全局异常捕获处理</h2>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public CommonResponse<String> handleBusinessException(HttpServletRequest req, Exception ex) {
        BusinessException exception = (BusinessException) ex;
        CommonResponse<String> response = new CommonResponse<>(exception.getCode(), exception.getMessage());
        log.error("service has error: [{}]", ex.getMessage(), ex);
        return response;
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handleException(HttpServletRequest req, Exception ex) {
        CommonResponse<String> response = new CommonResponse<>(500, "系统异常");
        log.error("service has system error: [{}]", ex.getMessage(), ex);
        return response;
    }

    @ExceptionHandler(value = BindException.class)
    public CommonResponse<String> handleException(HttpServletRequest req, BindException ex) {
        final String defaultMessage = ex.getAllErrors().get(0).getDefaultMessage();
        CommonResponse<String> response = new CommonResponse<>(4000, defaultMessage);
        log.error("service has system error: [{}]", ex.getMessage(), ex);
        return response;
    }
}
