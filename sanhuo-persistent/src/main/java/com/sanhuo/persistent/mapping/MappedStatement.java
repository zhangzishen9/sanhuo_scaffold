package com.sanhuo.persistent.mapping;

/**
 * <p>
 * 一个该对象对应一条sql语句
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/8:20:43
 */
public class MappedStatement {
    /**
     * 来自哪个Mapper的接口
     */
    private Class resource;
    /**
     * 节点的id属性加命名空间,如：com.sanhuo.example.dao.UserMapper.findById
     */
    private String id;
}
