package com.example.querydsldemo.domain.sys.bean;

import lombok.Data;
import java.sql.Timestamp;

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

    private Timestamp createdDate;
}
