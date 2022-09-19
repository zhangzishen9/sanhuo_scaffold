package com.sanhuo.app.advice;

import com.alibaba.fastjson.JSON;
import com.sanhuo.app.annotation.SanhuoApplication;
import com.sanhuo.app.exception.BusinessException;
import com.sanhuo.app.response.RestResponse;
import com.sanhuo.app.response.RestResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.awt.*;

/**
 * @author zhangzs
 * @description 全局统一拦截
 * @date 2022/9/16 17:59
 **/
@Slf4j
@ControllerAdvice(basePackages = {SanhuoApplication.BASIC_PACKAGE})
public class RestControllerAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        final String returnTypeName = returnType.getParameterType().getName();
        //如果无返回值
        if ("void".equals(returnTypeName)) {
            return RestResponseBuilder.builder().success();
        }

        //如果用户自己返回已经定义为统一返回的格式，不处理
        if (returnTypeName.endsWith(RestResponse.class.getSimpleName())) {
            return body;
        }

        //其他情况，统一处理为统一的格式
        return RestResponseBuilder.builder().setData(body).success();
    }


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(Exception.class)
    public RestResponse ExceptionHandler(Exception e) {
        e.printStackTrace();
        return RestResponseBuilder.builder().setData(e.getMessage()).error().build();
    }

    @ExceptionHandler(BusinessException.class)
    public RestResponse handleAuthorizationException(BusinessException e) {
        e.printStackTrace();
        return RestResponseBuilder.builder().setData(e.getMessage()).error().build();
    }


}
