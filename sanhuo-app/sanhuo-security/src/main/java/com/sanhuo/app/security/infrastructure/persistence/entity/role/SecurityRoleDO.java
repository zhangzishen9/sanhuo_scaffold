package com.sanhuo.app.security.infrastructure.persistence.entity.role;

import com.sanhuo.persistent.entity.BaseDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zhangzs
 * @description 角色
 * @date 2022/9/20 19:59
 **/
@Entity
@Table(name = "t_security_user")
public class SecurityRoleDO extends BaseDO {

    @Column(name = "c_name")
    private String name;

}
