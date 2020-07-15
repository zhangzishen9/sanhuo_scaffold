package com.sanhuo.persistent.session;

import com.sanhuo.persistent.builder.config.yml.Properties;
import com.sanhuo.persistent.builder.config.yml.PropertiesException;
import com.sanhuo.persistent.builder.config.yml.YmlConfigBuilder;
import com.sanhuo.persistent.enums.DataSourceType;
import com.sanhuo.persistent.exception.ExceptionMessageConstant;
import com.sanhuo.persistent.exception.ExceptionUtil;
import com.sanhuo.persistent.session.defaults.DefaultSqlSessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * SqlSessionFactoryBuilder
 *
 * @author sanhuo
 * @date 2020/2/23 0023 下午 17:12
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory builder() {
        Yaml yml = new Yaml();
        //配置文件路径 -->   变成可选
        try {
            //读取yml
            String path = YmlConfigBuilder.class.getClassLoader().getResource("sanhuo.yml").getPath();
            InputStream reader = new FileInputStream(new File(path));
            Properties properties = yml.loadAs(reader, Properties.class);
            //解析yml生成datasource
            YmlConfigBuilder ymlConfigBuilder = new YmlConfigBuilder(properties);
            System.out.println(properties);
            return build(ymlConfigBuilder.getConfiguration());
        } catch (Exception exception) {
            //数据源类型错误
            if (exception.getMessage().contains(DataSourceType.class.getName())) {
                ExceptionUtil.throwException(PropertiesException.class, ExceptionMessageConstant.DATASOURCE_TYPE_ERROR);
            } else {
                exception.printStackTrace();
            }
            //todo 如果有的默认值没填也会报错
            return null;
        }
    }

    private SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }


}
