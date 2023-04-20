package com.example.querydsldemo.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author ：Zxh
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final JPAQueryFactory queryFactory;


}
