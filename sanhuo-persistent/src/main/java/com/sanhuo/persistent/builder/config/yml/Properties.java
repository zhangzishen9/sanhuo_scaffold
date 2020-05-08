package com.sanhuo.persistent.builder.config.yml;

import com.sanhuo.persistent.builder.config.yml.pojo.Persistend;
import lombok.Data;


/**
 * yml各属性的配置的vo类
 */
@Data
public class Properties {
    /**
     * yml配置主要放在里面
     */
    private Persistend persistend;

}
