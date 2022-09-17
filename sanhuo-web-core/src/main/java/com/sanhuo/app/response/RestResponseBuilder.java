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

    public RestResponseBuilder<T> success() {
        RestResponseBuilder builder = new RestResponseBuilder();
        this.code = RestResponseCodeEnum.SUCCESS.getId();
        this.msg = RestResponseCodeEnum.SUCCESS.getText();
        return this;
    }

    public RestResponseBuilder<T> setData(T data) {
        this.data = data;

    }

}
