package com.sanhuo.persistent.builder.config.yml.pojo;

import com.sanhuo.persistent.builder.config.yml.pojo.datasource.DataSourceElement;
import lombok.Data;

@Data
public class Persistend {
    /**
     * 数据库环境配置
     */
    private DataSourceElement dataSource;
}
