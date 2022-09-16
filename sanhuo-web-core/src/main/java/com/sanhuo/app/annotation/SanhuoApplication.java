package com.sanhuo.app.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/16 17:49
 **/
@SpringBootApplication(
        scanBasePackages = {SanhuoApplication.CONFIGURATION_PACKAGE}
)
public @interface SanhuoApplication {

    String BASIC_PACKAGE = "com.sanhuo";
    String ANY_MATCH = "**";
    String CONFIGURATION_PACKAGE = BASIC_PACKAGE + "." + ANY_MATCH + ".configuration";
}
