package com.sanhuo.persistent.session.defaults;

import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.session.SqlSession;
import com.sanhuo.persistent.session.SqlSessionFactory;

/**
 * SqlSession的默认工厂
 *
 * @author sanhuo
 * @date 2020/2/23 0023 下午 17:15
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }
}
