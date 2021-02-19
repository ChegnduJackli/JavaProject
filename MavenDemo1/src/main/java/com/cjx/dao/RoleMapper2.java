package com.cjx.dao;

import org.apache.ibatis.annotations.Select;

import com.cjx.entity.role;

public interface RoleMapper2 {
    @Select("select id,role_name as roleName,note from role where id=#{id}")
    public role getRole(Long id);
}
