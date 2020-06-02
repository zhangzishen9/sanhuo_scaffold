package com.sanhuo.persistent.binding.property;

import com.sanhuo.persistent.binding.annotation.Delete;
import com.sanhuo.persistent.binding.annotation.Insert;
import com.sanhuo.persistent.binding.annotation.Select;
import com.sanhuo.persistent.binding.annotation.Update;
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
    SELECT(Select.class),
    /**
     * 更新
     */
    UPDATE(Update.class),
    /**
     * 插入
     */
    INSERT(Insert.class),
    /**
     * 删除
     */
    DELETE(Delete.class);

    @Getter
    private Class value;

}
