package com.sanhuo.persistent.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/20 20:01
 **/
@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BaseDO implements Serializable, IdAccessor<Integer> {
    @Column(name = "c_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "c_create_time")
    private Date createTime;

    @Column(name = "c_update_time")
    private Date updateTime;

    @Column(name = "c_is_deleted")
    private Integer isDeleted = 0;

    @Override
    public Integer getId() {
        return id;
    }
}
