package com.example.querydsldemo.web;

import com.example.querydsldemo.domain.sys.UserManager;
import com.example.querydsldemo.domain.sys.UserSearch;
import com.example.querydsldemo.domain.sys.bean.Role;
import com.example.querydsldemo.domain.sys.bean.User;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.SysUser;
import com.example.querydsldemo.interfaces.persistent.database.sys.entity.SysUserResource;
import com.example.querydsldemo.interfaces.persistent.database.sys.repository.UserResourceRepository;
import com.example.querydsldemo.interfaces.persistent.database.sys.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * UserApi
 *
 * @author ：Zxh
 */
@RequiredArgsConstructor
@RestController
public class UserApi {

    private final UserRepository userRepository;
    private final UserResourceRepository userResourceRepository;
    private final UserSearch userSearch;
    private final UserManager userManager;

    /**
     * 保存用户
     */
    @PostMapping(value = "/user")
    public ResponseEntity<?> saveUser(@RequestBody SysUser sysUser) {
        userRepository.save(sysUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 保存用户名称
     */
    @PostMapping(value = "/user/name")
    public ResponseEntity<?> userUpdateName(String sourceName, String targetName) {
        return new ResponseEntity<>(userManager.userUpdateName(sourceName, targetName), HttpStatus.OK);
    }

    /**
     * 保存用户资源
     */
    @PostMapping(value = "/userResource")
    public ResponseEntity<?> saveUserResource(@RequestBody SysUserResource userResource) {
        userResourceRepository.save(userResource);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 保存用户关联的角色
     *
     * @param userName 用户名
     * @param roleIds  角色
     */
    @PostMapping(value = "/userRole")
    public ResponseEntity<?> saveUserRole(String userName, String[] roleIds) {
        userManager.saveUserRole(userName, Arrays.asList(roleIds));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 查询用户
     */
    @GetMapping(value = "/user")
    public ResponseEntity<?> findUser(User user, int page, int size) {
        return new ResponseEntity<>(userSearch.findAllUser(user, PageRequest.of(page, size)), HttpStatus.OK);
    }

    /**
     * 查询角色
     */
    @GetMapping(value = "/role")
    public ResponseEntity<?> findRole(Role role) {
        return new ResponseEntity<>(userSearch.findAllRole(role), HttpStatus.OK);
    }

    /**
     * 查询用户资源
     */
    @GetMapping(value = "/userResource")
    public ResponseEntity<?> findUserResource() {
        return new ResponseEntity<>(userSearch.findUserResource(), HttpStatus.OK);
    }

    /**
     * 查询用户资源
     */
    @GetMapping(value = "/userResourcePage")
    public ResponseEntity<?> userResourcePage(int page, int size) {
        return new ResponseEntity<>(userSearch.findUserResource(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping(value = "/sqlQuery")
    public ResponseEntity<?> sqlQuery() {
        return new ResponseEntity<>(userSearch.sqlQuery(), HttpStatus.OK);
    }
}
