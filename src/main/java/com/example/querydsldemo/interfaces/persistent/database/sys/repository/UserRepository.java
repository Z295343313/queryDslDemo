package com.example.querydsldemo.interfaces.persistent.database.sys.repository;

import com.example.querydsldemo.domain.sys.bean.User;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.QSysUser;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.SysUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.util.StringUtils;

/**
 * 用户
 *
 * @author Administrator
 */
public interface UserRepository extends JpaRepository<SysUser, String>, QuerydslPredicateExecutor<SysUser> {

    /**
     * 单表查询
     * where user_name = '' and name like '%%' and date( created_date ) = date( ? )
     *
     * @param query 查询条件
     * @return Predicate
     */
    default Predicate toPredicate(User query) {
        QSysUser user = QSysUser.sysUser;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(query.getUsername())) {
            builder.and(user.username.eq(query.getUsername()));
        }
        if (StringUtils.hasText(query.getName())) {
            builder.and(user.name.like("%" + query.getName() + "%"));
        }
        if (query.getCreatedDate() != null) {
            builder.and(Expressions.stringTemplate("date({0})", user.createdDate).eq(Expressions.stringTemplate("date({0})", query.getCreatedDate())));
        }
        return builder;
    }
}
