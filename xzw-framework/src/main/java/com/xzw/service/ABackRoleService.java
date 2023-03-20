package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Role;


import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-12-05 11:46:56
 */
public interface ABackRoleService extends IService<Role> {
    //根据用户id查询角色关键字（admin、common...)
    List<String> selectRoleKeyByUserId(Long id);
    //根据用户id获取具有的角色id
    List<Long> selectRoleIdByUserId(Long userId);
    //根据用户id获取具有的角色
    List<Role> selectRoleAll();
    //查询角色列表
    ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize);

    //添加角色
    void insertRole(Role role);
}

