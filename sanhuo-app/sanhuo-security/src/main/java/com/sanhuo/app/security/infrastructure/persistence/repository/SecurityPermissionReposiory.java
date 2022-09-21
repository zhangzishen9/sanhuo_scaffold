package com.sanhuo.app.security.infrastructure.persistence.repository;

import com.sanhuo.app.security.infrastructure.persistence.entity.SecurityPermissionDO;
import com.sanhuo.app.security.infrastructure.persistence.entity.SecurityRoleDO;
import com.sanhuo.persistent.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/21 12:46
 **/
@Repository
public interface SecurityPermissionReposiory extends BaseRepository<SecurityPermissionDO> {
}
