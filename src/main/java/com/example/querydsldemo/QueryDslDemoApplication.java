package com.example.querydsldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableWebMvc
@EnableJpaAuditing
public class QueryDslDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryDslDemoApplication.class, args);
    }

}
