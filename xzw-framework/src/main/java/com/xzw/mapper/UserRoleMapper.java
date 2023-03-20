package com.xzw.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzw.domain.entity.UserRole;

import java.util.List;

/**
 * @Author xzw
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    List<Long> selectRoleIdByUserId(Long userId);
}