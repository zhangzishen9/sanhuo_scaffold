package com.sanhuo.app.enums;

import com.sanhuo.commom.enums.BasicEnum;

/**
 * @author zhangzs
 * @description 返回状态枚举
 * @date 2022/9/16 18:02
 **/
public enum RestResponseCodeEnum implements BasicEnum {
    SUCCESS(200, "成功"),
    ERROR(500, "系统异常,请稍后重试");


    Integer id;
    String text;

    RestResponseCodeEnum(Integer id, String text) {
        this.id = id;
        this.text = text;
    }


    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
