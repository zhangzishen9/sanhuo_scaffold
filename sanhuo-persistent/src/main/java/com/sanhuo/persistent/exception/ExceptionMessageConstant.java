package com.sanhuo.persistent.exception;

import com.sanhuo.persistent.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 抛异常的信息的常量
 */
@AllArgsConstructor
public enum ExceptionMessageConstant {
    /**
     * 错误信息模板需要参数
     */
    EXCEPTION_MESSAGE_NEED_PARAM(" exception message : [" + Constant.FORMAT_STRING + " ] need params"),

    /**
     * 数据源的配置信息缺少
     */
    DATASOURCE_PROPERTIES_DEFECT("Database properties must be configured[ driver & url & username & password ]"),
    /**
     * 不存在指定id的数据源的配置
     */
    DATASOURCE_ENVIRONMENT_UN_EXIST("doesn't exist profiles : [ " + Constant.FORMAT_STRING + " ] 's environment"),
    /**
     * 不存在无参构造器
     */
    NO_ARG_CONSTRUCTOR_UN_EXIST(" class : [" + Constant.FORMAT_STRING + " ] doesn't exist no arg constructor "),
    /**
     * 数据源类型错误
     */
    DATASOURCE_TYPE_ERROR("DatasourceType must be :[UNPOOL / POOL ]"),
    /**
     * 不存在对应参数的构造器
     */
    CONSTRUCTOR_UN_EXIST(" class : [" + Constant.FORMAT_STRING + " ] doesn't exist params: [ " + Constant.FORMAT_STRING + " ]'s constructor"),
    /**
     * 不存在此字段
     */
    FIELD_NOT_EXIST("class : [" + Constant.FORMAT_STRING + " ] doesn't exist filed : [" + Constant.FORMAT_STRING + " ] "),
    /**
     * 调用set方法时参数属性不一致
     */
    FIELD_TYPE_UN_MATCH("field : [" + Constant.FORMAT_STRING + " ] type no match"),
    /**
     * 调用get方法时出错
     */
    GET_PROPRETRY_ERROR("could not get proprety : [" + Constant.FORMAT_STRING + " ]"),
    /**
     * 调用set方法时出错
     */
    SET_PROPRETRY_ERROR("could not set proprety : [" + Constant.FORMAT_STRING + " ]");


    @Getter
    @Setter
    private String messageTemplate;

}
