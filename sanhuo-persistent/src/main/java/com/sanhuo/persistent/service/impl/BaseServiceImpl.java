package com.sanhuo.persistent.service.impl;

import com.sanhuo.persistent.entity.BaseDO;
import com.sanhuo.persistent.repository.BaseRepository;
import com.sanhuo.persistent.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/21 12:56
 **/
public class BaseServiceImpl<T extends BaseDO, R extends BaseRepository<T>> implements BaseService<T, R> {

    @Autowired
    private R repository;

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Iterable<Integer> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public void save(T entity) {
        repository.save(entity);
    }

    @Override
    public void save(Iterable<T> entities) {
        repository.saveAll(entities);
    }

    @Override
    public T findById(Integer id) {
        return repository.getById(id);
    }


}
