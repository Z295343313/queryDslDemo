package com.example.querydsldemo.domain.sys.bean;

import lombok.Data;

/**
 * 用户
 *
 * @author Zxh
 */
@Data
public class User {
    private String username;

    private String userPassword;

    private String name;

    public User() {
    }

    public User(String username, String userPassword, String name) {
        this.username = username;
        this.userPassword = userPassword;
        this.name = name;
    }
}
