package com.sanhuo.io.protocol.tcp;

import com.sanhuo.io.protocol.ProtocolSupport;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangzs
 * @description
 * @date 2022/8/24 18:56
 **/
@Slf4j
public class TcpSupport extends ProtocolSupport {

    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);
    private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);
    public static Selector selector;

    /**
     * tcp client map <br>
     * key : client's ip <br>
     * value : client's socket <br>
     */
    public static Map<String, SocketChannel> clientMap = new ConcurrentHashMap<>();

    @Override
    protected String protocol() {
        return "tcp";
    }


    /**
     * 启动socket服务，开启监听
     *
     * @param port
     * @throws IOException
     */
    @Override
    protected void openChannel(int port) throws Exception {
        try {
            //打开通信信道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            //获取套接字
            ServerSocket serverSocket = serverSocketChannel.socket();
            //绑定端口号
            serverSocket.bind(new InetSocketAddress(port));
            //打开监听器
            selector = Selector.open();
            //将通信信道注册到监听器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("启动tcp监听,端口为:{}", port);
            //监听器会一直监听，如果客户端有请求就会进入相应的事件处理
            while (true) {
                selector.select();//select方法会一直阻塞直到有相关事件发生或超时
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//监听到的事件
                for (SelectionKey key : selectionKeys) {
                    //具体的执行函数
                    handle(key);
                }
                selectionKeys.clear();//清除处理过的事件
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理不同的事件
     *
     * @param selectionKey
     * @throws IOException
     */
    private void handle(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        try {
            int count = 0;

            //ip+端口为唯一识别
            if (selectionKey.isAcceptable()) {
                //每有客户端连接，即注册通信信道为可读
                serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                socketChannel = serverSocketChannel.accept();
                String ip = socketChannel.socket().getInetAddress().getHostAddress();
                log.info("监听到新的tcp链接,ip为:{}",ip);
                //设置为非阻塞
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
                clientMap.put(ip, socketChannel);
            } else if (selectionKey.isReadable()) {
                //获取socket通道
                socketChannel = (SocketChannel) selectionKey.channel();
                //清空读取数据缓存区
                rBuffer.clear();
                //读取数据
                count = socketChannel.read(rBuffer);
                if (count < 0) {
                    //小于0说明断开了连接
                    selectionKey.cancel();
                    //关闭对应的socket通道
                    socketChannel.close();
                    //移除map中的数据
                    String ip = socketChannel.socket().getInetAddress().getHostAddress();
                    int port = socketChannel.socket().getPort();
                    ip += port;
                    clientMap.remove(ip);
                    log.info("有客户端:" + ip + " 断开连接");
                }
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
        } catch (IOException e) {
            //如果捕获到该SelectionKey对应的Channel时出现了异常,即表明该Channel对于的Client出现了问题
            //所以从Selector中取消该SelectionKey的注册
            selectionKey.cancel();
            if (selectionKey.channel() != null) {
                try {
                    selectionKey.channel().close();
                } catch (IOException e1) {
                    log.error("tcp出现异常", e.getMessage());
                }
            }
        }
    }
}
