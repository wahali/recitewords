package com.eng.recitewords;

import com.eng.recitewords.config.FileStorageConfig;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@MapperScan("com.eng.recitewords.mapper")
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class RecitewordsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecitewordsApplication.class, args);
    }

}
