package com.example.querydsldemo.interfaces.persistent.database.sys.entity;

import com.example.querydsldemo.interfaces.persistent.database.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 用户
 *
 * @author Zxh
 */
@Entity
@Setter
@Getter
@SuppressWarnings("all")
public class SysUser extends BaseEntity {
    private String username;

    private String password;

    private String name;

    private String rolename;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp createdDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",
            joinColumns = @JoinColumn(name = "sys_user_name", referencedColumnName = "username"), inverseJoinColumns = @JoinColumn(name = "sys_role_id", referencedColumnName = "id"))
    private Set<SysRole> roles;
}
