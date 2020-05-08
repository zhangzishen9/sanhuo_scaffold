package com.sanhuo.test;

import com.sanhuo.test.mapper.TestMapperOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private TestMapperOne testMapperOne;

    @RequestMapping("/test")
    public void test() {
        System.out.println(1);
    }
}
