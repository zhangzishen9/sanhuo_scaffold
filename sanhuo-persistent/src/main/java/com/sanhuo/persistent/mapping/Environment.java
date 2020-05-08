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
            throw new IllegalArgumentException("Environment's Id cannot be empty");
        }
        this.id = id;
        if (dataSource == null) {
            throw new IllegalArgumentException("Environment's dataSource cannot be empty");
        }
        this.dataSource = dataSource;
    }

    //一个静态内部类Builder
    //建造模式
    //用法应该是new Environment.Builder(id).transactionFactory(xx).dataSource(xx).build();
    public static class Builder {
        private String id;
        private DataSource dataSource;

        public Builder(String id) {
            this.id = id;
        }

        public Builder dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public String id() {
            return this.id;
        }

        public Environment build() {
            return new Environment(this.id, this.dataSource);
        }

    }

    public enum State {
        POOLED,
        UNPOOLED
    }


}
