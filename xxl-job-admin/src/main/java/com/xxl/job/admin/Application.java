package com.xxl.job.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @author: :MaYong
 * @Date: 2018/5/2 14:48
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.xxl.job.admin.dao"})
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
