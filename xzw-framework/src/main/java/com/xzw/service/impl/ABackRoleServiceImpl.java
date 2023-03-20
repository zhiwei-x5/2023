package com.xzw.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.constants.SystemConstants;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Role;
import com.xzw.domain.entity.RoleMenu;
import com.xzw.domain.vo.PageVo;
import com.xzw.mapper.RoleMapper;
import com.xzw.service.ABackRoleMenuService;
import com.xzw.service.ABackRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-12-05 11:46:56
 */
@Service
public class ABackRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements ABackRoleService {
    @Autowired
    private ABackRoleMenuService aBackRoleMenuService;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public List<Long> selectRoleIdByUserId(Long userId) {
        return getBaseMapper().selectRoleIdByUserId(userId);    }

    @Override
    public List<Role> selectRoleAll() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemConstants.NORMAL));
    }

    @Override
    public ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize) {
        //第一步：先获取到role信息
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            //角色名称搜索：如果角色搜索框不为null则根据内容进行搜索
        lambdaQueryWrapper.like(StringUtils.hasText(role.getRoleName()),Role::getRoleName,role.getRoleName());
            //角色状态搜索：如果状态框不为null则根据内容进行搜索
        lambdaQueryWrapper.eq(StringUtils.hasText(role.getStatus()),Role::getStatus,role.getStatus());
        lambdaQueryWrapper.orderByAsc(Role::getRoleSort);

        //第二步：封装一个page信息
        Page<Role> rolePage = new Page<>();
        rolePage.setCurrent(pageNum);
        rolePage.setSize(pageSize);

        //第三步：使用role信息、page信息进行查询
        /**注意：此处无需返回值，因为返回对象即为rolePage*/
        page(rolePage,lambdaQueryWrapper);

        //第四步：转换成新的vo
        List<Role> roles = rolePage.getRecords();
        PageVo pageVo = new PageVo();
        pageVo.setTotal(rolePage.getTotal());
        pageVo.setRows(roles);

        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public void insertRole(Role role) {
        save(role);
        System.out.println(role.getId());
        if(role.getMenuIds()!=null&&role.getMenuIds().length>0){
            insertRoleMenu(role);
        }
    }
    private void insertRoleMenu(Role role) {
        List<RoleMenu> roleMenuList = Arrays.stream(role.getMenuIds())
                .map(memuId -> new RoleMenu(role.getId(), memuId))
                .collect(Collectors.toList());
        aBackRoleMenuService.saveBatch(roleMenuList);
    }

}

