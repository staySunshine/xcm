package cn.xcm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = "cn.xcm.mapper")
@SpringBootApplication
public class XcmApplication {

    public static void main(String[] args) {
        SpringApplication.run(XcmApplication.class, args);
    }

}
