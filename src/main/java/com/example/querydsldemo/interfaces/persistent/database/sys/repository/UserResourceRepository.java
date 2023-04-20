package com.example.querydsldemo.interfaces.persistent.database.sys.repository;

import com.example.querydsldemo.interfaces.persistent.database.sys.entity.SysUserResource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface UserResourceRepository extends JpaRepository<SysUserResource, String> {

}
