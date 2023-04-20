package com.example.querydsldemo.domain.user;

import com.example.querydsldemo.interfaces.persistent.database.entity.QSysRole;
import com.example.querydsldemo.interfaces.persistent.database.entity.SysRole;
import com.example.querydsldemo.interfaces.persistent.database.repository.RoleRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
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


    /**
     * 修改
     * update sys_role set name = '' where name = ''
     *
     * @param sourceName    原名称
     * @param targetName    明白名称
     * @return  修改条数
     */
    @Transactional(rollbackOn = Exception.class)
    public Long update(String sourceName, String targetName) {
        QSysRole role = QSysRole.sysRole;
        return queryFactory.update(role)
                .set(role.name, targetName)
                .setNull(role.name)
                .where(role.name.eq(sourceName)).execute();
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
