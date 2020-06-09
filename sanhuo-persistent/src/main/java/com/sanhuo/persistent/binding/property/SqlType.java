package com.sanhuo.persistent.binding.property;

import com.sanhuo.persistent.binding.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * sql的类型
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/25:21:12
 */
@AllArgsConstructor
@NoArgsConstructor
public enum SqlType {
    /**
     * 查询
     */
    SELECT(Select.class, SelectProvider.class),
    /**
     * 更新
     */
    UPDATE(Update.class, UpdateProvider.class),
    /**
     * 插入
     */
    INSERT(Insert.class, InsertProvider.class),
    /**
     * 删除
     */
    DELETE(Delete.class, DeleteProvider.class);

    @Getter
    private Class basic;

    @Getter
    private Class provider;

}
