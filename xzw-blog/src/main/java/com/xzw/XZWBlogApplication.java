package com.xzw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.xzw.mapper")
@EnableScheduling /** 使用@EnableScheduling注解开启定时任务功能 */
@EnableSwagger2
public class XZWBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XZWBlogApplication.class,args);
    }
}
