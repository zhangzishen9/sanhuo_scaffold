package com.sanhuo.test.mapper;


import com.sanhuo.persistent.binding.annotation.Mapper;
import com.sanhuo.persistent.binding.annotation.Select;

/**
 * TestMapperTwo
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 17:48
 */
@Mapper
public interface TestMapperTwo {
    @Select("select * from user where id = {id}")
     void findAll(Integer id);
}
