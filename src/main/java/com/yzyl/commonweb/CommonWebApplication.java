package com.yzyl.commonweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CommonWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonWebApplication.class, args);
    }

}
