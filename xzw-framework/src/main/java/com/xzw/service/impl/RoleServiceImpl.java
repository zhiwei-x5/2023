package com.xzw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.domain.entity.Role;
import com.xzw.mapper.RoleMapper;
import com.xzw.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-08-09 22:36:47
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


}

