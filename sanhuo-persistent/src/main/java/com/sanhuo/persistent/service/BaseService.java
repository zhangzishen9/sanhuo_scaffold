package com.sanhuo.persistent.service;

import com.sanhuo.persistent.entity.BaseDO;
import com.sanhuo.persistent.repository.BaseRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/21 12:56
 **/
public interface BaseService<T extends BaseDO, R extends BaseRepository<T>> {
    List<T> findAll();

    List<T> findAll(Iterable<Integer> ids);

    void save(T entity);

    void save(Iterable<T> entities);

    T findById(Integer id);


}
