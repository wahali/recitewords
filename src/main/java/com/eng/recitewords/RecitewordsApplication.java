package com.eng.recitewords;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.eng.recitewords.mapper")
@SpringBootApplication
public class RecitewordsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecitewordsApplication.class, args);
    }

}
