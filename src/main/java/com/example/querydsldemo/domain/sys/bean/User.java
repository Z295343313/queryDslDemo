package com.example.querydsldemo.domain.user.bean;

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

    private String roleCode;

    private String roleName;


    public User() {
    }

    public User(String username, String userPassword, String name, String roleCode, String roleName) {
        this.username = username;
        this.userPassword = userPassword;
        this.name = name;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
