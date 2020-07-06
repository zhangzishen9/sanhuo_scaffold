package com.sanhuo.test;

import com.sanhuo.commom.spring.SpringContextManager;
import com.sanhuo.persistent.SqlSessionManager;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.session.SqlSession;
import com.sanhuo.persistent.session.SqlSessionFactory;
import com.sanhuo.test.entity.Person;
import com.sanhuo.test.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 * 测试
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/8:21:31
 */
@RestController
public class TestController {

    @Autowired
    private PersonMapper personMapper;

    @RequestMapping("/test")
    public List<Person> test() throws InterruptedException {
        List<Person> persons = personMapper.findAll();
        return persons;
    }

}
