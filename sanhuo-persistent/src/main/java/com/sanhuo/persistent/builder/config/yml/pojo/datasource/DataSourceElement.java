package com.sanhuo.persistent.builder.config.yml.pojo.datasource;

import com.sanhuo.persistent.enums.DataSourceType;
import lombok.Data;

import java.util.List;

@Data
public class DataSourceElement {
    /**
     * 是否使用数据库池/UNPOOL/POOL 暂时两种
     */
    private DataSourceType type;
    /**
     * 不同环境的数据库连接参数
     */
    private List<DataSourceProperty> dataSourceProperties;
    /**
     * 选择哪一个环境的数据库连接参数
     */
    private String profiles;
}
