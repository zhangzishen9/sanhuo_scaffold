package com.sanhuo.app.security.infrastructure.persistence.entity;

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
public class SecurityRolePermissionRelDO extends BaseDO {

    @Column(name = "c_permission_id")
    private Integer permission_id;

    @Column(name = "c_role_id")
    private Integer roleId;

}
