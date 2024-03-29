package com.sanhuo.mvp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 * @author zhangzs
 * @description
 * @date 2022/8/24 14:08
 **/
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class Controller {


    //发送数据缓冲区
    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);

    @GetMapping("/sendMsg")
    public void changDeviceState(Integer msg) throws IOException {
        Map<String, SocketChannel> map = TcpServer.map;
        for (Map.Entry<String, SocketChannel> entry : map.entrySet()) {
            SocketChannel channel = entry.getValue();
            sBuffer.clear();
            sBuffer.put((msg + "").getBytes());
            sBuffer.flip();
            //输出到通道
            if (channel.write(sBuffer) < 0) {
                throw new RuntimeException("设备通信失败!");
            }
            channel.register(TcpServer.selector, SelectionKey.OP_READ);
            log.info("tcp发送消息:'{}'到ip:{}", msg, entry.getKey());
        }

    }

}
