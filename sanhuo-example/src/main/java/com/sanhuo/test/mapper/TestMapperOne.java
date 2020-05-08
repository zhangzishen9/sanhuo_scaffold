package com.sanhuo.test.mapper;


import com.sanhuo.persistent.mapper.annotation.Mapper;
import com.sanhuo.persistent.mapper.annotation.Select;

/**
 * TestMapperOne
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 17:48
 */
@Mapper
public interface TestMapperOne {
    @Select("select * from user where id = {id}")
    void findById(Integer id);
}
