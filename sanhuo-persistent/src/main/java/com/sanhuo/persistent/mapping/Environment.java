package com.sanhuo.persistent.mapping;

import lombok.Getter;

import javax.sql.DataSource;

/**
 * 环境
 * 决定加载哪种环境(product/dev)
 */
@Getter
public class Environment {
    //环境Id
    private final String id;
    //数据源
    private final DataSource dataSource;

    public Environment(String id, DataSource dataSource) {
        if (id == null) {
            throw new IllegalArgumentException("Environment's Identifier cannot be empty");
        }
        this.id = id;
        if (dataSource == null) {
            throw new IllegalArgumentException("Environment's dataSource cannot be empty");
        }
        this.dataSource = dataSource;
    }


}
