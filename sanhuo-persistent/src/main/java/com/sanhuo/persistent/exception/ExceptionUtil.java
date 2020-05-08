package com.sanhuo.persistent.exception;

import com.sanhuo.persistent.common.Constant;
import com.sanhuo.persistent.reflection.Reflector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 抛异常信息的工具
 */
public class ExceptionUtil {
    /**
     * 满足条件才会抛异常
     *
     * @param condition 条件 ,为true就会抛异常
     * @param exception 异常类型
     * @param constant  错误信息模板(无需要替换占位符)
     */
    public static void throwException(Boolean condition, Class<? extends PersistenceException> exception, ExceptionMessageConstant constant) {
        if (condition) {
            throwException(exception, constant);
        }
    }

    /**
     * 满足条件才会抛异常
     *
     * @param condition 条件 ,为true就会抛异常
     * @param exception 异常类型
     * @param constant  错误信息模板(无需要替换占位符)
     * @param param     错误信息模板需要替换的字符串
     */
    public static void throwException(Boolean condition, Class<? extends PersistenceException> exception, ExceptionMessageConstant constant, String... param) {
        if (condition) {
            throwException(exception, constant, param);
        }
    }


    /**
     * 抛出异常
     *
     * @param exception 异常类型
     * @param constant  错误信息模板(无需要替换占位符)
     */
    public static void throwException(Class<? extends PersistenceException> exception, ExceptionMessageConstant constant) {
        throwException(exception, constant, null);
    }

    /**
     * 抛出异常
     *
     * @param exception 异常类型
     * @param constant  错误信息模板
     * @param param     错误信息模板需要替换的字符串
     */
    public static void throwException(Class<? extends PersistenceException> exception, ExceptionMessageConstant constant, String... param) {
        String exceptionMessage = param == null ? getExceptionMessage(constant) : getExceptionMessage(constant, param);
        throwException(exception, exceptionMessage);
    }

    /**
     * 抛出异常(不推荐直接用)
     *
     * @param exception 异常类型
     * @param message   错误信息
     */
    public static void throwException(Class<? extends PersistenceException> exception, String message) {
        Constructor<? extends PersistenceException> exceptionConstructor = Reflector.getConstructor(exception, String.class);
        throwException(exceptionConstructor, message);
    }

    /**
     * 抛异常由这里来处理,算是重复代码,抽出来
     */
    private static void throwException(Constructor<? extends PersistenceException> exceptionConstructor, String message) {

        try {
            throw exceptionConstructor
                    .newInstance(message);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            //TODO catch之后的处理需要优化
            e.printStackTrace();
        }
    }


    /**
     * 获取错误信息
     */
    public static String getExceptionMessage(ExceptionMessageConstant constant, String... param) {
        //获取对应的模板
        String messageTemplate = constant.getMessageTemplate();
        //如果模板中存在 [ '%s' ]  ,则说明有要替换的字符串, 无则直接返回
        return messageTemplate.contains(Constant.FORMAT_STRING) ? String.format(messageTemplate, param) : messageTemplate;
    }

    /**
     * 获取错误信息
     */
    private static String getExceptionMessage(ExceptionMessageConstant constant) {
        //获取对应的模板
        String messageTemplate = constant.getMessageTemplate();
        if (messageTemplate.contains(Constant.FORMAT_STRING)) {
            //该模板需要参数
            String message = String.format(ExceptionMessageConstant.EXCEPTION_MESSAGE_NEED_PARAM.getMessageTemplate(), constant.name());
            throw new ErrorMessageException(message);
        }
        return messageTemplate;
    }


}
