package com.sanhuo.persistent.binding.proxy;

import com.sanhuo.commom.spring.SpringContextManager;
import com.sanhuo.persistent.SqlSessionManager;
import com.sanhuo.persistent.mapping.MappedStatement;
import com.sanhuo.persistent.reflection.Reflector;
import com.sanhuo.persistent.session.RowBounds;
import com.sanhuo.persistent.session.SqlSession;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * mapper的代理类,具体在这里调用SqlSession
 *
 * @author sanhuo
 * @date 2020/3/2 0002 下午 21:23
 */
public class MapperProxy implements InvocationHandler {

    public MapperProxy(String id) {
        this.id = id;
    }

    private String id;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SqlSession sqlSession = SpringContextManager.getBean(SqlSessionManager.class).openSqlSession();
        //方法的签名
        String id = Reflector.getSignature(method);
        MappedStatement
                ms = sqlSession.getConfiguration().getMappedStatement(id);
        //todo 分页参数
        return sqlSession.execute(ms, new RowBounds(), args);
    }

    @Override
    public String toString() {
        return id + "'s proxy";
    }
}
