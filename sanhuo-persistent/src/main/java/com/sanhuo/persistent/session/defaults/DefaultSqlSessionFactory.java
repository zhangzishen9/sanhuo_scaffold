package com.sanhuo.persistent.session.defaults;

import com.sanhuo.persistent.SqlSessionManager;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.session.SqlSession;
import com.sanhuo.persistent.session.SqlSessionFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * SqlSession的默认工厂
 *
 * @author sanhuo
 * @date 2020/2/23 0023 下午 17:15
 */
@Data
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;


    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() throws SQLException {
        return new SqlSession(this.configuration);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }
}
