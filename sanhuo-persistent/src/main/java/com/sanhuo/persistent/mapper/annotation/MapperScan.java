package com.sanhuo.persistent.mapper.annotation;

import com.sanhuo.persistent.mapper.MapperScanRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * MapperScan
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 21:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MapperScanRegister.class)
public @interface MapperScan {
    String value();
}
