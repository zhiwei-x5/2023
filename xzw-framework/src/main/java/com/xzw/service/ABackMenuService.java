package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-12-05 10:55:58
 */
public interface ABackMenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
    //查询菜单所有信息
    List<Menu> selectMenuList(Menu menu);

    List<Long> selectMenuListByRoleId(Long roleId);
    //判断是否有子菜单
    boolean hasChild(Long menuId);
}

