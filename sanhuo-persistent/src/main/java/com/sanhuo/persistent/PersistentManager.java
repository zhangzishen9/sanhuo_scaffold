package com.sanhuo.persistent;

import com.sanhuo.commom.manager.SpringManager;
import com.sanhuo.persistent.session.SqlSessionFactory;
import com.sanhuo.persistent.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 在springboot启动后执行
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 19:14
 */
@Component
@Order(value = 1)
public class PersistentManager implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void run(String... args) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //创建sqlSessionFactory
        SqlSessionFactory factory = builder.builder();
        //把sqlSessionFactory注册到容器里(单例)
        SpringManager.registerSingleton(factory);

        System.out.println(1);
    }

}
