package com.sanhuo.test.mapper;


import com.sanhuo.persistent.binding.annotation.Mapper;
import com.sanhuo.persistent.binding.annotation.Select;
import com.sanhuo.test.entity.Person;

/**
 * PersonMapper
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 17:48
 */
@Mapper(TestMapperOne.class)
public interface PersonMapper {


    @Select("select * from user where id = #{id}")
    Person findAll(Integer id);


}
