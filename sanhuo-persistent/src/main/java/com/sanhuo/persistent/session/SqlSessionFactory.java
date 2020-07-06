package com.sanhuo.persistent.session;

import java.sql.SQLException;

/**
 * SqlSession的工厂
 *
 * @author sanhuo
 * @date 2020/2/23 0023 下午 17:13
 */
public interface SqlSessionFactory {
    //8个方法可以用来创建SqlSession实例
    SqlSession openSession() throws SQLException;

    Configuration getConfiguration();
}
