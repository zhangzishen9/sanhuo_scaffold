package com.sanhuo.app.response;

import com.sanhuo.app.enums.RestResponseCodeEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @author zhangzs
 * @description api返回结果
 * @date 2022/9/16 17:57
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestResponse<T> implements Serializable {


    private static final long serialVersionUID = -5749400746911722651L;
    /**
     * 真实返回内容
     */
    T data;
    /**
     * 返回代码
     */
    Integer code;
    /**
     * 信息
     */
    String msg;


    RestResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }



}
