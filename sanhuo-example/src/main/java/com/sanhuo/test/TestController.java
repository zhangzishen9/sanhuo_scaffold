package com.sanhuo.test;

import com.sanhuo.commom.spring.SpringContextManager;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.session.SqlSessionFactory;
import com.sanhuo.test.mapper.PersonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

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
//    @Autowired
    private PersonMapper personMapper;

    @RequestMapping("/test")
    public void test() {
        Configuration configuration = SpringContextManager.getBean(SqlSessionFactory.class).getConfiguration();
        System.out.println(1);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        SpringApplication.run(TestController.class, args);
    }
}
