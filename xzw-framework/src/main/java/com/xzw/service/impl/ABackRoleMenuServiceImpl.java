package com.xzw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.domain.entity.RoleMenu;
import com.xzw.mapper.ABackRoleMenuMapper;
import com.xzw.service.ABackRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Service
public class ABackRoleMenuServiceImpl extends ServiceImpl<ABackRoleMenuMapper, RoleMenu> implements ABackRoleMenuService {

    @Override
    public void deleteRoleMenuByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        remove(queryWrapper);
    }
}
