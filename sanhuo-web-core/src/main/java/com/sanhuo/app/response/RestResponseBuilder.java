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
@ToString
public class RestResponseBuilder<T> implements Serializable {

    private RestResponse response = new RestResponse();

    private RestResponseBuilder() {
    }

    public static RestResponseBuilder builder() {
        return new RestResponseBuilder();
    }

    public static RestResponseBuilder builder(RestResponseCodeEnum code) {
        RestResponseBuilder builder = new RestResponseBuilder();
        builder.response.code = code.getId();
        builder.response.msg = code.getText();
        return builder;
    }


    public RestResponseBuilder<T> success() {
        this.response.code = RestResponseCodeEnum.SUCCESS.getId();
        this.response.msg = RestResponseCodeEnum.SUCCESS.getText();
        return this;
    }

    public RestResponseBuilder<T> error() {
        return this.builder(RestResponseCodeEnum.ERROR);
    }


    public RestResponseBuilder<T> setData(T data) {
        this.response.data = data;
        return this;
    }

    public RestResponse build() {
        return this.response;
    }

}
