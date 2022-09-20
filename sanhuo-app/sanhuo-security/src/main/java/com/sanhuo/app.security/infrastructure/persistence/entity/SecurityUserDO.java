package com.sanhuo.app.infrastructure.persistence.security.entity;

import com.sanhuo.persistent.entity.BaseDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/20 19:59
 **/
@Entity
@Table(name = "t_security_user")
public class SecurityUserDO extends BaseDO {

    @Column(name = "c_username")
    private String username;

    @Column(name = "c_password")
    private String password;

    @Column(name = "c_open_id")
    private String openId;

}
