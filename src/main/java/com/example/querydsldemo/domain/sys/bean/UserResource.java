package com.example.querydsldemo.domain.sys.bean;

import lombok.Data;

/**
 * UserRole
 *
 * @author ：Zxh
 */
@Data
public class UserRole {
    private String username;
    private String name;
    private String roleName;

    public UserRole() {
    }

    public UserRole(String username, String name, String roleName) {
        this.username = username;
        this.name = name;
        this.roleName = roleName;
    }
}
