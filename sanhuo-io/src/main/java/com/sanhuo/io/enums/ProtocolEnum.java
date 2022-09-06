package com.sanhuo.io.enums;

import com.sanhuo.io.protocol.ProtocolSupport;
import com.sanhuo.io.protocol.tcp.TcpSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangzs
 * @description 协议枚举
 * @date 2022/9/6 12:42
 **/
@AllArgsConstructor
@Getter
public enum ProtocolEnum {
    TCP(TcpSupport.class)
    ;
    private Class<? extends ProtocolSupport> protocol;


}
