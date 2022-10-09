package com.sanhuo.app.http.invoke.helper;

import com.sanhuo.app.http.annotation.Header;
import com.sanhuo.app.http.annotation.Headers;
import com.sanhuo.commom.basic.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzs
 * @description 请求头解析
 * @date 2022/10/9 12:03
 **/
@AllArgsConstructor
@Slf4j
public class HeaderAnalysisHelper {
    private Headers headers;

    public Map<String, String> analaysis() {
        Map<String, String> headersMap = new HashMap<>();
        if (headers != null) {
            Header[] values = headers.values();
            for (Header header : values) {
                if (StringUtil.isBlank(header.values())) {
                    if (StringUtil.isBlank(header.name()) || StringUtil.isBlank(header.value())) {
                        log.warn("haederParam name or value is blank ,pleade check");
                        continue;
                    }
                    headersMap.put(header.name(), header.value());
                } else {
                    String headerValue = header.values();
                    int index = headerValue.indexOf("=");
                    if (index == -1) {
                        log.warn("please check header format,need 'name = value'");
                    }
                    String name = headerValue.substring(0, index);
                    String paramValue = headerValue.substring(index - 1);
                    headersMap.put(name, paramValue);
                }
            }
        }
        return headersMap;
    }
}
