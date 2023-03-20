package com.xzw.controller;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.ChangeRoleStatusDto;
import com.xzw.domain.entity.Role;
import com.xzw.service.ABackRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private ABackRoleService aBackRoleService;

    /*@RequestMapping("/listAllRole")
    public ResponseResult listAllRole(){
        //查询所有角色信息
        List<Role> roles = aBackRoleService.selectRoleAll();
        return ResponseResult.okResult(roles);
    }*/

    /**查询角色*/
    @GetMapping("/list")
    public ResponseResult list(Role role, Integer pageNum, Integer pageSize) {
        return aBackRoleService.selectRolePage(role,pageNum,pageSize);
    }
    /**根据角色编号查询角色信息*/
    @GetMapping("/{roleId}")
    public ResponseResult getInfo(@PathVariable Long roleId){
        Role role = aBackRoleService.getById(roleId);
        return ResponseResult.okResult(role);
    }
    /**
     * 新增角色
     */
    @PostMapping
    public ResponseResult add( @RequestBody Role role) {
        aBackRoleService.insertRole(role);
        return ResponseResult.okResult();
    }
    /**
     * 删除角色
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseResult remove(@PathVariable(name = "id") Long id) {
        aBackRoleService.removeById(id);
        return ResponseResult.okResult();
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        return ResponseResult.okResult(aBackRoleService.updateById(role));
    }
}
