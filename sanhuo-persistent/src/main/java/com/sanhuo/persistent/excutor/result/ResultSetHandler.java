package com.sanhuo.persistent.excutor.result;

import com.mysql.cj.protocol.Resultset;
import com.sanhuo.commom.basic.CollectionUtil;
import com.sanhuo.commom.basic.ObjectUtil;
import com.sanhuo.persistent.binding.annotation.Column;
import com.sanhuo.persistent.binding.property.ResultMapping;
import com.sanhuo.persistent.logging.Log;
import com.sanhuo.persistent.mapping.MappedStatement;
import com.sanhuo.persistent.reflection.ObjectFactory;
import com.sanhuo.persistent.reflection.meta.MetaObject;
import com.sanhuo.persistent.session.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 结果处理器
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/17:21:48
 */
public class ResultSetHandler {

    private MappedStatement mappedStatement;


    private ObjectFactory objectFactory;


    public ResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
        this.objectFactory = new ObjectFactory();
    }


    public List getResultSet(PreparedStatement pstmt) throws SQLException {
        List results = new LinkedList();
        ResultMapping resultMapping = this.mappedStatement.getResultMapping();
        //结果类型
        Class resultClass = this.mappedStatement.getResultMapping().getType();
        //执行sql
        ResultSet resultset = pstmt.executeQuery();
        //遍历每行
        while (resultset.next()) {
            //是否是包装类型
            if (ObjectUtil.isPrimitive(resultClass)) {
                ResultMapping.Result column = resultMapping.getColumns().get(0);
                results.add(column.getTypeHandler().getResult(resultset, column.getColumnIndex()));
            } else {
                Object result = objectFactory.create(resultClass);
                //源对象 用于设置属性
                MetaObject metaObject = MetaObject.init(result);
                //遍历列
                for (ResultMapping.Result column : resultMapping.getColumns()) {
                    Object field = column.getTypeHandler().getResult(resultset, column.getColumnName());
                    metaObject.setValue(column.getFieldName(), field);
                }
                results.add(metaObject.getOriginalObject());
            }
        }
        return results;
    }
}