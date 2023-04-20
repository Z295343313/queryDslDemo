package com.example.querydsldemo.interfaces.persistent.database.entity;

import com.example.querydsldemo.interfaces.persistent.database.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

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

    @ManyToMany(mappedBy = "roles")
    private Collection<SysUser> users;

    public SysRole() {
    }

    public SysRole(String name) {
        this.name = name;
    }
}
