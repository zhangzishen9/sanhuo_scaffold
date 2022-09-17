package com.sanhuo.app.response;

import com.sanhuo.app.enums.RestResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhangzs
 * @description api返回结果
 * @date 2022/9/16 17:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestResponse<T> implements Serializable {


    private static final long serialVersionUID = -5749400746911722651L;
    /**
     * 真实返回内容
     */
    private T data;
    /**
     * 返回代码
     */
    private Integer code;
    /**
     * 信息
     */
    private String msg;


    RestResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    public RestResponse<T> success() {
        this.code = RestResponseCodeEnum.SUCCESS.getId();
        this.msg = RestResponseCodeEnum.SUCCESS.getText();
        return this;
    }

    public RestResponse<T> setData(T data){
        this.data = data;

    }

}
