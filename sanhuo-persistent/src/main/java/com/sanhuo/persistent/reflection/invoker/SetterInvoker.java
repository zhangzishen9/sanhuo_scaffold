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
public class SetterInvoker implements Invoker {
    /**
     * 具体的字段
     */
    private Field field;

    /**
     * 初始化setter
     */
    public static SetterInvoker init(Field field) {
        field.setAccessible(true);
        SetterInvoker setter = SetterInvoker.builder().field(field).build();
        return setter;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException {
        field.set(target, args[0]);
        return null;
    }
}
