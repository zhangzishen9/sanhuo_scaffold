package com.sanhuo.test.mapper;


import com.sanhuo.persistent.binding.annotation.Mapper;
import com.sanhuo.persistent.binding.annotation.Select;
import com.sanhuo.persistent.builder.SqlSourceBuilder;
import com.sanhuo.persistent.builder.config.annotation.MapperAnnotationBuilder;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.test.TestApplication;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * TestMapperOne
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 17:48
 */
@Mapper(TestApplication.class)

public class TestMapperOne {
    @Select("select * from user where id = {id}")
    private void findById(Integer id){};


    public static void main(String[] args) throws NoSuchMethodException {
        SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(Configuration.init());
        Method method = TestMapperOne.class.getDeclaredMethod("findById",Integer.class);
        Parameter[] p =  method.getParameters();
        MapperAnnotationBuilder mapperAnnotationBuilder = new MapperAnnotationBuilder(Configuration.init(),TestMapperOne.class);
        sqlSourceBuilder.parse("select * from person where id = #{id}",mapperAnnotationBuilder.parseParameterMapping(p));
        System.out.println(p);

    }


}
