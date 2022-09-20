package com.sanhuo.app.infrastructure.persistence.security.entity;

import com.sanhuo.persistent.entity.BaseDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zhangzs
 * @description 用户权限
 * @date 2022/9/20 19:59
 **/
@Entity
@Table(name = "t_security_user_role_rel")
public class SecurityUserRoleRelDO extends BaseDO {

    @Column(name = "c_user_id")
    private Integer userId;

    @Column(name = "c_role_id")
    private Integer roleId;

}
