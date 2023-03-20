package com.xzw.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.entity.RoleMenu;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
public interface ABackRoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}