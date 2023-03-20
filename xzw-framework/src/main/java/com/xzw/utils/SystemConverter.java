package com.xzw.utils;


import com.xzw.domain.entity.Menu;
import com.xzw.domain.vo.MenuTreeVo;
import com.xzw.domain.vo.MenuTreeVo2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**系统转换器*/
public class SystemConverter {

    private SystemConverter() {
    }

    //构建菜单树
    public static List<MenuTreeVo> buildMenuSelectTree(List<Menu> menus) {
        /**1、获取需要的元素*/
        List<MenuTreeVo> MenuTreeVos = menus.stream()
                .map(m -> new MenuTreeVo(m.getId(), m.getMenuName(), m.getParentId(), null))
                .collect(Collectors.toList());
        System.out.println("MenuTreeVos");
        System.out.println(MenuTreeVos);
        /**2、筛选为getParentId为0即获取第一层*/
        List<MenuTreeVo> options = MenuTreeVos.stream()
                .filter(o -> o.getParentId().equals(0L))
                .map(o -> o.setChildren(getChildList(MenuTreeVos, o)))
                .collect(Collectors.toList());
        System.out.println("options");
        System.out.println(options);

        return options;
    }
    /**
     * 得到子节点列表
     */
    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        List<MenuTreeVo> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .map(o -> o.setChildren(getChildList(list, o)))
                .collect(Collectors.toList());
        System.out.println("options2");
        System.out.println(options);
        return options;

    }
    //构建菜单树
    public static List<Menu> buildMenuSelectTree2(List<Menu> menus) {
        /**1、获取需要的元素*/
        List<Menu> MenuTreeVos = menus;
        /**2、筛选为getParentId为0即获取第一层*/
        List<Menu> options = MenuTreeVos.stream()
                .filter(o -> o.getParentId().equals(0L))
                .map(o -> o.setChildren(getChildList2(MenuTreeVos, o)))
                .collect(Collectors.toList());
        return options;
    }
    /**
     * 得到子节点列表
     */
    private static List<Menu> getChildList2(List<Menu> list, Menu option) {
        List<Menu> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .map(o -> o.setChildren(getChildList2(list, o)))
                .collect(Collectors.toList());
        return options;
    }


}
