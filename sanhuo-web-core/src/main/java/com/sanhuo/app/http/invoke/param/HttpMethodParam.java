package com.sanhuo.app.http.invoke.param;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.app.http.invoke.type.TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangzs
 * @description 参数
 * @date 2022/10/9 08:59
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpMethodParam {
    /**
     * 方法里的参数名称
     */
    private String name;

    /**
     * 别名，如 :<br/>
     * #{@link  org.springframework.web.bind.annotation.RequestParam} <br/>
     * #{@link org.springframework.web.bind.annotation.PathVariable}<br/>
     * 里的name
     */
    private String alias;

    /**
     * 方法参数的顺序
     */
    private Integer paramIndex;


    /**
     * 参数类型
     */
    private ParamTypeEnum paramType;

    /**
     * 对象
     */
    private JSONObject parentObject;

    /**
     * 默认值
     */
    private Object defaultValue;

    /**
     * 属性处理器
     */
    private TypeHandler typeHandler;

}
