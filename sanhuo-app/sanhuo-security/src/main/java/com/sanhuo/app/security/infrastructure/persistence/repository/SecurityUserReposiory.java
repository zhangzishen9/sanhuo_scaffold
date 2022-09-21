package com.sanhuo.app.security.infrastructure.persistence.repository;

import com.sanhuo.app.security.infrastructure.persistence.entity.SecurityUserDO;
import com.sanhuo.persistent.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/21 12:46
 **/
@Repository
public interface SecurityUserReposiory extends BaseRepository<SecurityUserDO> {
}
