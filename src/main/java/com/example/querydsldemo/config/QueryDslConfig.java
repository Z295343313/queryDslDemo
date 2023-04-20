package com.example.querydsldemo.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * QueryDslCongif
 *
 * @author ：Zxh
 */
@Component
@RequiredArgsConstructor
public class QueryDslConfig {
    private final EntityManager entityManager;

    @Bean
    public JPAQueryFactory getQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SQLQueryFactory sqlQueryFactory(DataSource dataSource){
        return new SQLQueryFactory(new com.querydsl.sql.Configuration(SQLTemplates.DEFAULT), dataSource);
    }

}
