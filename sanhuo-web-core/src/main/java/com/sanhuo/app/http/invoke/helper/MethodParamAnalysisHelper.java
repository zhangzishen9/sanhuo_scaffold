package com.sanhuo.app.http.invoke.helper;

import com.sanhuo.app.http.invoke.param.HttpMethodParam;
import com.sanhuo.app.http.invoke.param.ParamTypeEnum;
import com.sanhuo.app.http.invoke.type.InnerObjectHandler;
import com.sanhuo.app.http.invoke.type.TypeHandler;
import com.sanhuo.app.http.invoke.type.TypeHandlerRegister;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzs
 * @description 方法参数解析帮助类
 * @date 2022/10/9 09:12
 **/
@AllArgsConstructor
public class MethodParamAnalysisHelper {

    private Method method;

    public List<HttpMethodParam> analysis() {
        Parameter[] parameters = this.method.getParameters();
        int index = 0;
        List<HttpMethodParam> result = new ArrayList<>();
        for (Parameter parameter : parameters) {
            index++;
            HttpMethodParam.HttpMethodParamBuilder paramBuilder = HttpMethodParam.builder();
            paramBuilder.paramIndex(index);
            paramBuilder.name(parameter.getName());
            Class paramType = parameter.getType();
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            ParamTypeEnum paramTypeEnum = null;
            if (requestBody != null) {
                paramTypeEnum = ParamTypeEnum.REQUEST_BODY;
            }
            if (pathVariable != null) {
                paramTypeEnum = ParamTypeEnum.PATH_VARIABLE;
                if (StringUtils.hasText(pathVariable.name())) {
                    paramBuilder.alias(pathVariable.name());
                }
            }
            if (requestParam != null) {
                paramTypeEnum = ParamTypeEnum.REQUEST_PARAM;
                if (StringUtils.hasText(requestParam.name())) {
                    paramBuilder.alias(requestParam.name());
                }
                if (StringUtils.hasText(requestParam.defaultValue())) {
                    paramBuilder.defaultValue(requestParam.defaultValue());
                }
            }
            TypeHandler<?> typeHandler = TypeHandlerRegister.get(paramType);
            paramBuilder.typeHandler(typeHandler);
            boolean isInnerObject = typeHandler instanceof InnerObjectHandler;
            if (isInnerObject) {
                //对象取里面属性作为参数
                PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(paramType);
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    if ("class".equals(propertyDescriptor.getName())) {
                        continue;
                    }
                    result.add(new HttpMethodParam(
                            propertyDescriptor.getName(),
                            parameter.getName() + "." + propertyDescriptor.getName(),
                            index,
                            paramTypeEnum,
                            null,
                            null,
                            typeHandler
                    ));
                }
            } else {
                result.add(paramBuilder.build());
            }
        }
        return result;
    }


}
