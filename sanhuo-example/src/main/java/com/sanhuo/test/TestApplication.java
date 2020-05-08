package com.sanhuo.test;

import com.sanhuo.persistent.mapper.annotation.MapperScan;
import com.sanhuo.persistent.session.SqlSession;
import com.sanhuo.persistent.session.SqlSessionFactory;
import com.sanhuo.persistent.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableEurekaClient
@ComponentScan("com.sanhuo")
@MapperScan("com.sanhuo.test")
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);

    }

}
