package com.example.querydsldemo.interfaces.persistent.database.sys.entity;

import com.example.querydsldemo.interfaces.persistent.database.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 角色
 *
 * @author Zxh
 */
@Entity
@Setter
@Getter
public class SysRole extends BaseEntity {
    private String name;

    public SysRole() {
    }

    public SysRole(String name) {
        this.name = name;
    }
}
