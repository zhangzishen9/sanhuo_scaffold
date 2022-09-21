package com.sanhuo.persistent.repository;

import com.sanhuo.persistent.entity.BaseDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/21 12:54
 **/
public interface BaseRepository<T extends BaseDO> extends JpaRepository<T,Integer> {
}
