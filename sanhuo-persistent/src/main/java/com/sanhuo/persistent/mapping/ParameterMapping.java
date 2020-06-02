package com.sanhuo.persistent.mapping;

import com.sanhuo.persistent.type.TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 参数映射
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/12:21:01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParameterMapping {
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数类型
     */
    private Class type;
    /**
     * 参数转换器
     */
    private TypeHandler typeHandler;


}
