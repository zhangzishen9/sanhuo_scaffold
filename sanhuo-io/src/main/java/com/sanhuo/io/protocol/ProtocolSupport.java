package com.sanhuo.io.protocol;

import com.sanhuo.io.IOSupport;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangzs
 * @description protocol support interface
 * @date 2022/8/24 18:58
 **/
@Slf4j
public abstract class ProtocolSupport implements IOSupport {

    /**
     * listen port
     *
     * @param port
     */
    void listen(int port) {
        String protocol = this.protocol();
        try {
            this.openChannel(port);
        } catch (Exception e) {
//            log.error("");
        }
    }

    /**
     * protocol name
     *
     * @return
     */
    protected abstract String protocol();

    /**
     * open channel in port
     *
     * @param port
     */
    protected abstract void openChannel(int port) ;

}
