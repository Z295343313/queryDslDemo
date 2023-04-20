package com.example.querydsldemo.interfaces.persistent.database.repository;

import com.example.querydsldemo.domain.user.bean.Role;
import com.example.querydsldemo.interfaces.persistent.database.entity.QSysRole;
import com.example.querydsldemo.interfaces.persistent.database.entity.SysRole;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.util.StringUtils;

/**
 * 角色
 *
 * @author Administrator
 */
public interface RoleRepository extends JpaRepository<SysRole, String>, QuerydslPredicateExecutor<SysRole> {

    /**
     * 单表查询
     * where name = ''
     *
     * @param query 查询条件
     * @return Predicate
     */
    default Predicate toPredicate(Role query) {
        QSysRole role = QSysRole.sysRole;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(query.getRoleName())) {
            builder.and(role.name.eq(query.getRoleName()));
        }
        return builder;
    }
}
