package com.sanhuo.persistent.reflection.invoker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GetterInvoker implements Invoker {
    /**
     * 具体的字段
     */
    private Field field;

    /**
     * 初始化getter
     */
    public static GetterInvoker init(Field field) {
        field.setAccessible(true);
        GetterInvoker getter = GetterInvoker.builder().field(field).build();
        return getter;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException {
        return field.get(target);
    }
}
