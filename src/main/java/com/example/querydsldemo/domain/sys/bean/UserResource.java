package com.example.querydsldemo.domain.sys.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;

/**
 * UserResource
 *
 * @author ：Zxh
 */
@Data
public class UserResource {
    private String username;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Timestamp createdDate;
    private String resourceCode;
    private String resourceName;
}
