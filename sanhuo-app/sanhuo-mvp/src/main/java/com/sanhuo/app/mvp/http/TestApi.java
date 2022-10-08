package com.sanhuo.app.mvp.http;

import com.sanhuo.app.http.annotation.HttpClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Date 2022/9/19
 * @auther by zhangzs@tsintery.com
 */
@HttpClient("www.baidu.com")
public interface TestApi {

}
