package com.example.querydsldemo.domain.sys;

import com.example.querydsldemo.domain.sys.bean.Role;
import com.example.querydsldemo.domain.sys.bean.User;
import com.example.querydsldemo.domain.sys.bean.UserResource;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.QSysUser;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.QSysUserResource;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.SysRole;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.SysUser;
import com.example.querydsldemo.interfaces.persistent.database.sys.repository.RoleRepository;
import com.example.querydsldemo.interfaces.persistent.database.sys.repository.UserRepository;
import com.querydsl.core.Fetchable;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.Union;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UserSearch
 *
 * @author ：Zxh
 */
@Component
@RequiredArgsConstructor
public class UserSearch {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JPAQueryFactory queryFactory;
    private final SQLQueryFactory sqlQueryFactory;

    public Page<SysUser> findAllUser(User user, PageRequest pageRequest) {
        return userRepository.findAll(userRepository.toPredicate(user), pageRequest);
    }

    public Page<SysRole> findAllRole(Role role, PageRequest pageRequest) {
        return roleRepository.findAll(roleRepository.toPredicate(role), pageRequest);
    }

    public List<UserResource> findUserResource() {
        return this.userResourceJpaQuery().fetch();
    }

    public Page<UserResource> findUserResource(PageRequest pageRequest) {
        Fetchable<UserResource> limit = this.userResourceJpaQuery()
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize());
        List<UserResource> list = limit.fetch();
        return new PageImpl<>(list, pageRequest, limit.fetchCount());
    }

    /**
     * select sysUser.username, sysUser.name, date(sysUser.created_date), sysUserResource.code as resourceCode, sysUserResource.name as resourceName
     * from sys_user sysUser
     *   left join sys_user_resource sysUserResource on sysUser. username = sysUserResource.user_name
     * @return JPAQuery
     */
    public JPAQuery<UserResource> userResourceJpaQuery() {
        QSysUser user = QSysUser.sysUser;
        QSysUserResource userResource = QSysUserResource.sysUserResource;
        return queryFactory.select(Projections.bean(UserResource.class,
                        user.username, user.name, user.createdDate.as("createdDate"),
                        userResource.code.as("resourceCode"), userResource.name.as("resourceName")))
                .from(user).leftJoin(userResource).on(user.username.eq(userResource.userName));
    }

    /**
     * SQL查询例子
     * SELECT
     * 	username
     * FROM
     * 	sys_user
     * WHERE
     * 	sys_user.username IN (
     * 	SELECT
     * 		username
     * 	FROM
     * 		(
     * 			( SELECT sys_user.username, sys_user.NAME FROM sys_user WHERE sys_user.NAME = '张三' )
     * 			UNION ALL
     * 			(
     * 			SELECT
     * 				sys_user.username,
     * 				sys_user.NAME
     * 			FROM
     * 				sys_user
     * 				LEFT JOIN sys_user_resource ON sys_user.username = sys_user_resource.user_name
     * 			WHERE
     * 				sys_user_resource.CODE = '0001'
     * 			)
     * 		) AS un
     * 	)
     */
    public List<String> sqlQuery() {
        QSysUser user = new QSysUser("sys_user");
        QSysUserResource userResource = new QSysUserResource("sys_user_resource");

        SubQueryExpression<Tuple> credentialsQuery =
                SQLExpressions.select(user.username, user.name)
                        .from(user)
                        .where(user.name.eq("张三"));

        SubQueryExpression<Tuple> credentialsQuery1 =
                SQLExpressions.select(user.username, user.name)
                        .from(user).leftJoin(userResource).on(user.username.eq(Expressions.stringPath("user_resource.user_name")))
                        .where(userResource.code.eq("0001"));

        @SuppressWarnings("unchecked")
        Union<Tuple> unionAll = SQLExpressions.select().unionAll(credentialsQuery, credentialsQuery1);

        SQLQuery<String> unionAllQuery = SQLExpressions.select(Expressions.stringPath("username"))
                .from(unionAll, Expressions.stringPath("un"));

        return sqlQueryFactory.select(user.username).from(user).where(user.username.in(unionAllQuery)).fetch();
    }
}
