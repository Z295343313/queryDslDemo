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
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
     * select sysUser.username, sysUser.name, sysUserResource.code as resourceCode, sysUserResource.name as resourceName
     * from sys_user sysUser
     *   left join sys_user_resource sysUserResource on sysUser.username = sysUserResource.user_name
     * @return JPAQuery
     */
    public JPAQuery<UserResource> userResourceJpaQuery() {
        QSysUser user = QSysUser.sysUser;
        QSysUserResource userResource = QSysUserResource.sysUserResource;
        return queryFactory.select(Projections.bean(UserResource.class, user.username, user.name, userResource.code.as("resourceCode"), userResource.name.as("resourceName")))
                .from(user).leftJoin(userResource).on(user.username.eq(userResource.userName));
    }
}
