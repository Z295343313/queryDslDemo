package com.example.querydsldemo.interfaces.persistent.database.sys.entity;

import com.example.querydsldemo.interfaces.persistent.database.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 用户资源
 *
 * @author ：Zxh
 */
@Entity
@Setter
@Getter
public class SysUserResource extends BaseEntity {
    private String code;
    private String name;
    private String userName;
}
