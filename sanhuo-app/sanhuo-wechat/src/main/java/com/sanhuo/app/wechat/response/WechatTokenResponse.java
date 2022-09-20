package com.sanhuo.app.wechat.response;

import com.sanhuo.app.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/20 13:05
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatTokenResponse implements BaseResponse {

    private String access_token;

    private Long expires_in;


}
