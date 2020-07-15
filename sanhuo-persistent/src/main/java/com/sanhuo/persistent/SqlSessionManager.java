package com.sanhuo.persistent;

import com.sanhuo.persistent.session.SqlSession;
import com.sanhuo.persistent.session.SqlSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * <p>
 * SqlSession管理器
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/15:20:12
 */
@Component
@Order(10)
@Slf4j
public class SqlSessionManager implements BeanFactoryAware, HandlerInterceptor, WebMvcConfigurer {

    private SqlSessionFactory sqlSessionFactory;
    private ThreadLocal<SqlSession> sqlSessionThreadLocal;
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    public void init() {
        this.sqlSessionFactory = beanFactory.getBean(SqlSessionFactory.class);
        //每个线程打开一个SqlSession
        this.sqlSessionThreadLocal = ThreadLocal.withInitial(() -> {
            try {
                return sqlSessionFactory.openSession();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public SqlSession openSqlSession() {
        return sqlSessionThreadLocal.get();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("====== 当前线程结束,关闭SqlSession ======");
        this.sqlSessionThreadLocal.get().close();
        sqlSessionThreadLocal.set(null);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }
}
