package com.xzw.domain.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 菜单权限表(Menu)表实体类
 *
 * @author makejava
 * @since 2022-12-05 10:55:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
@Accessors(chain = true)
public class Menu  {
    //菜单ID@TableId
    @TableId
    private Long id;

    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //是否为外链（0是 1否）
    private Integer isFrame;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;
    //创建者
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新者
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;
    
    private String delFlag;

    /*当我们项目中实体类中，需要该属性，但是却不是数据库中的字段我们可以用：@TableField(exist = false)
    注解加载bean属性上，表示当前属性不是数据库的字段，但在项目中必须使用，这样在新增等使用bean的时候，
    mybatis-plus就会忽略这个，不会报错。@TableField（）中@TableField(exist =true) 默认为true,表明为数据库字段对应的实体类变量。*/
    @TableField(exist = false)
    private List<Menu> children;
}

