package com.example.querydsldemo.domain.sys;

import com.example.querydsldemo.interfaces.persistent.database.sys.entity.QSysUser;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.SysRole;
import com.example.querydsldemo.interfaces.persistent.database.sys.repository.RoleRepository;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.*;
import com.querydsl.sql.dml.SQLInsertClause;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * RoleSearch
 *
 * @author ：Zxh
 */
@Component
@RequiredArgsConstructor
public class UserManager {
    private final RoleRepository roleRepository;
    private final JPAQueryFactory queryFactory;
    private final SQLQueryFactory sqlQueryFactory;


    /**
     * 修改
     * update sys_user set name = '' where name = ''
     *
     * @param sourceName 原名称
     * @param targetName 明白名称
     * @return 修改条数
     */
    @Transactional(rollbackOn = Exception.class)
    public Long userUpdateName(String sourceName, String targetName) {
        QSysUser user = QSysUser.sysUser;
        return queryFactory.update(user)
                .set(user.name, targetName)
                //.setNull(user.name)
                .where(user.name.eq(sourceName)).execute();
    }

    /**
     * 保存用户关联的角色
     * <p>
     * sql:
     * 1.delete from sys_user_role where sys_user_name = ?
     * 2.insert into sys_user_role(sys_user_name, sys_role_id) values(?,?)
     *
     * @param userName 用户名
     * @param roleIds  角色
     */
    @Transactional(rollbackOn = Exception.class)
    public void saveUserRole(String userName, List<String> roleIds) {
        //用户角色SQLQuery 的 Q类
        RelationalPath<Object> sysUserRole = new RelationalPathBase<>(Object.class, "sys_user_role", "queryDsl", "sys_user_role");

        //删除该用户下面所有的用户角色表
        sqlQueryFactory.delete(sysUserRole).where(Expressions.stringPath("sys_user_name")
                        .eq(userName))
                .execute();

        //批量插入
        SQLInsertClause insertClause = sqlQueryFactory.insert(sysUserRole);
        for (String roleId : roleIds) {
            insertClause.columns(Expressions.stringPath("sys_user_name"), Expressions.stringPath("sys_role_id"))
                    .values(userName, roleId).addBatch();
        }
        insertClause.execute();
    }

    /**
     * 将默认角色初始化到表
     */
    @PostConstruct
    public void initDefaultRole() {
        if (roleRepository.count() == 0) {
            List<SysRole> roles = Arrays.stream(DefaultRole.values()).map(o -> new SysRole(o.getName())).collect(Collectors.toList());
            roleRepository.saveAll(roles);
        }
    }

    @AllArgsConstructor
    @Getter
    public enum DefaultRole {
        /**
         * 供应商管理员
         */
        SUPPLIER_ADMIN("供应商管理员"),
        /**
         * 门店员工
         */
        SUPPLIER_STORE_STAFF("门店员工"),
        /**
         * 客服
         */
        CUSTOMER_SERVICE("客服"),
        /**
         * 超级管理员
         */
        SUPER("超级管理员");
        private final String name;
    }
}
