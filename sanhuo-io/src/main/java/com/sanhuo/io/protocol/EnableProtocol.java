package com.sanhuo.io.protocol;

import com.sanhuo.io.enums.ProtocolEnum;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/6 12:08
 **/
@Import(ProtocolConfiguration.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableProtocol {

    ProtocolEnum[] values() default {ProtocolEnum.TCP};
}
