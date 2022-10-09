package com.sanhuo.app.http.invoke.param;

/**
 * @author zhangzs
 * @description 参数类型
 * @date 2022/10/9 09:14
 **/
public enum ParamTypeEnum {
    /**
     * #{@link  org.springframework.web.bind.annotation.RequestParam}
     */
    REQUEST_PARAM,
    /**
     * #{@link  org.springframework.web.bind.annotation.RequestBody}
     */
    REQUEST_BODY,
    /**
     * #{@link  org.springframework.web.bind.annotation.PathVariable}
     */
    PATH_VARIABLE;
}
