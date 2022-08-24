package com.sanhuo.mvp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Map;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@Slf4j
public class TestApplication {


    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
        TcpServer server = new TcpServer();
        server.startSocketServer(8907);
    }


}
