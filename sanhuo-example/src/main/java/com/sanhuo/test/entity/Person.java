package com.sanhuo.test.entity;

import com.sanhuo.persistent.binding.annotation.Column;
import com.sanhuo.persistent.binding.annotation.Entity;
import com.sanhuo.persistent.binding.annotation.Identifier;
import com.sanhuo.persistent.binding.annotation.Table;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/2:21:37
 */
@Entity
@Table("person")
@Data
public class Person {

    @Identifier
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private Date createTime;
}
