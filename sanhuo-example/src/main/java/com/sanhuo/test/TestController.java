package com.sanhuo.test;

import com.sanhuo.persistent.logging.LogFactory;
import com.sanhuo.test.entity.Person;
import com.sanhuo.test.mapper.PersonMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<Person> persons = personMapper.findAll("1");
        return persons;
    }

}
