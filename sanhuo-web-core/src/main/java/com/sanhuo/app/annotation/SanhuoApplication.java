package com.sanhuo.app.annotation;

import com.sanhuo.app.http.EnableHttpClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/16 17:49
 **/
@SpringBootApplication(
        scanBasePackages = {
                SanhuoApplication.CONFIGURATION_PACKAGE,
                SanhuoApplication.COMPONENT_PACKAGE,
                SanhuoApplication.SERVICE_PACKAGE
        }
)
@EntityScan({SanhuoApplication.ENTITY_PACKAGE})
@EnableHttpClient
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SanhuoApplication {

    String BASIC_PACKAGE = "com.sanhuo";
    String ANY_MATCH = "**";
    String PACKAGE_SCAN_PREFIX = BASIC_PACKAGE + "." + ANY_MATCH;
    String CONFIGURATION_PACKAGE = PACKAGE_SCAN_PREFIX + ".configuration";
    String SERVICE_PACKAGE = PACKAGE_SCAN_PREFIX + ".impl";
    String COMPONENT_PACKAGE = PACKAGE_SCAN_PREFIX + ".component";
    String ENTITY_PACKAGE = PACKAGE_SCAN_PREFIX + ".entity";


}
