package com.sanhuo.app.security.infrastructure.persistence.entity;

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

    @Column(name = "c_nickname")
    private String nickname;

    @Column(name = "c_account")
    private String account;

    @Column(name = "a_avatar_url")
    private String avatarUrl;

    @Column(name = "c_password")
    private String password;

    @Column(name = "c_open_id")
    private String openId;

}
