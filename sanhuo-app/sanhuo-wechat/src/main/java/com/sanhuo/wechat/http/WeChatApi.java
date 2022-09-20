package com.sanhuo.wechat.http;

import com.sanhuo.app.http.annotation.HttpClient;
import com.sanhuo.wechat.response.WechatTokenResponse;
import com.sanhuo.wechat.response.WechatloginResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/20 12:52
 **/
@HttpClient("https://api.weixin.qq.com/")
public interface WeChatApi {

    /**
     * 获取token，有效值2小时
     *
     * @param grantType
     * @param appid
     * @param secret
     */
    @GetMapping("/cgi-bin/token")
    WechatTokenResponse getToken(@RequestParam("grant_type") String grantType, String appid, String secret);

    /**
     * 小程序登录
     * @param grantType
     * @param jsCode 小程序前端wx.login获取的code
     * @param appid
     * @param secret
     */
    @GetMapping("/sns/jscode2session")
    WechatloginResponse login(@RequestParam("grant_type") String grantType, @RequestParam("js_code") String jsCode, String appid, String secret);
}
