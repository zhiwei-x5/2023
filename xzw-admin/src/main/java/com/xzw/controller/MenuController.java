package com.xzw.controller;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Menu;
import com.xzw.domain.vo.MenuTreeVo;
import com.xzw.domain.vo.RoleMenuTreeSelectVo;
import com.xzw.service.ABackMenuService;
import com.xzw.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private ABackMenuService aBackMenuService;

    /**
     * 添加菜单信息
     */
    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        aBackMenuService.save(menu);
        return ResponseResult.okResult();
    }
    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public ResponseResult treeselect() {
        //递归获取信息存储
        List<Menu> menus = aBackMenuService.selectMenuList(new Menu());
        List<Menu> options =  SystemConverter.buildMenuSelectTree2(menus);
        return ResponseResult.okResult(options);
    }
    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = aBackMenuService.selectMenuList(new Menu());
        List<Long> checkedKeys = aBackMenuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }
    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    public ResponseResult list(Menu menu) {
        //递归获取信息存储
        List<Menu> menus = aBackMenuService.selectMenuList(menu);
        List<Menu> options =  SystemConverter.buildMenuSelectTree2(menus);
        return ResponseResult.okResult(options);
    }
    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    public ResponseResult getInfo(@PathVariable Long menuId) {
        return ResponseResult.okResult(aBackMenuService.getById(menuId));
    }
    /**
     * 修改菜单
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        aBackMenuService.updateById(menu);
        return ResponseResult.okResult();
    }
    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (aBackMenuService.hasChild(menuId)) {
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        aBackMenuService.removeById(menuId);
        return ResponseResult.okResult();
    }
}
