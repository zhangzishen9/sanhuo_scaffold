package com.sanhuo.test.mapper;


import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.protocol.Resultset;
import com.sanhuo.commom.basic.ObjectUtil;
import com.sanhuo.persistent.binding.annotation.Mapper;
import com.sanhuo.persistent.binding.annotation.Select;
import com.sanhuo.test.TestApplication;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

/**
 * TestMapperOne
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 17:48
 */
//@Mapper(TestApplication.class)

public class TestMapperOne {
    @Select("select * from user where id = {id}")
    private void findById(Integer id) {
    }




    private Set<String> test() {
        return new LinkedHashSet<>();
    }

    ;

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, SQLException {
//        SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(Configuration.init());
//        Method method = TestMapperOne.class.getDeclaredMethod("findById",Integer.class);
//        Parameter[] p =  method.getParameters();
//        MapperAnnotationBuilder mapperAnnotationBuilder = new MapperAnnotationBuilder(Configuration.init(),TestMapperOne.class);
//        sqlSourceBuilder.parse("select * from person where id = #{id}",mapperAnnotationBuilder.parseParameterMapping(p));
//        System.out.println(p);
        Method method = TestMapperOne.class.getDeclaredMethod("test");
        Class type = method.getReturnType();
//        System.out.println(ObjectUtil.isPrimitive(type));
//        System.out.println(1);
        //
//        Class.forName(Driver.class.getName());
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8", "root", "123456");
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from person");
//        preparedStatement.execute();
//        ResultSet resultset = preparedStatement.getResultSet();
//        System.out.println(1);
        //是否是Collection的子类
        boolean result = Collection.class.isAssignableFrom(type);
        Type[] fanxing =  ParameterizedType.class.cast(method.getGenericReturnType()).getActualTypeArguments();

        System.out.println(result);
    }


}
