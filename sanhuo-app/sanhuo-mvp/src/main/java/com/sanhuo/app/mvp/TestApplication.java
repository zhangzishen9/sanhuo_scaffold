package com.sanhuo.app.mvp;

import com.sanhuo.io.protocol.EnableProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@Slf4j
@EnableProtocol
public class TestApplication {


    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }


}
