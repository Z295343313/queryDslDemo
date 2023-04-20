package com.example.querydsldemo.domain.user;

import com.example.querydsldemo.domain.user.bean.Role;
import com.example.querydsldemo.domain.user.bean.User;
import com.example.querydsldemo.interfaces.persistent.database.entity.SysRole;
import com.example.querydsldemo.interfaces.persistent.database.entity.SysUser;
import com.example.querydsldemo.interfaces.persistent.database.repository.RoleRepository;
import com.example.querydsldemo.interfaces.persistent.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

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

    public Page<SysUser> findAll(User user, PageRequest pageRequest) {
        return userRepository.findAll(userRepository.toPredicate(user), pageRequest);
    }

    public Page<SysRole> findAll(Role role, PageRequest pageRequest) {
        return roleRepository.findAll(roleRepository.toPredicate(role), pageRequest);
    }
}
