package com.sanhuo.app.advice;

import com.sanhuo.app.annotation.SanhuoApplication;
import com.sanhuo.app.response.RestResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author zhangzs
 * @description 全局统一拦截
 * @date 2022/9/16 17:59
 **/
@ControllerAdvice(basePackages = {SanhuoApplication.BASIC_PACKAGE})
public class RestControllerAdvice {

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        final String returnTypeName = methodParameter.getParameterType().getName();
        //如果无返回值
        if ("void".equals(returnTypeName)) {
            return new ResponseVo.Builder().ok().build();
        }

        //如果返回值是String类型，单独处理，否则会出现类型转换错误
        if ("java.lang.String".equals(returnTypeName)) {
            return JSON.toJSONString(new ResponseVo.Builder().ok().data(o).build());
        }

        //如果返回类型是资源类型，不处理
        if (o instanceof Resource) {
            return o;
        }
        //如果头信息返回类型不是application/json,不处理
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return o;
        }
        //如果用户自己返回已经定义为统一返回的格式，不处理
        if (returnTypeName.endsWith(".ResponseVo")) {
            return o;
        }

        //其他情况，统一处理为统一的格式
        return new ResponseVo.Builder().ok().data(o).build();
    }


}
