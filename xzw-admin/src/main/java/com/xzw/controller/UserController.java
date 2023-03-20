package com.xzw.controller;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.ChangeUserStatusDto;
import com.xzw.domain.entity.Role;
import com.xzw.domain.entity.User;
import com.xzw.domain.entity.UserRole;
import com.xzw.domain.vo.UserInfoAndRoleIdsVo;
import com.xzw.enums.AppHttpCodeEnum;
import com.xzw.exception.SystemException;
import com.xzw.service.ABackRoleService;
import com.xzw.service.ABackUserRoleService;
import com.xzw.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ABackRoleService aBackRoleService;
    @Autowired
    private ABackUserRoleService aBackUserRoleService;

    /**查询用户列表*/
    @GetMapping("/list")
    public ResponseResult list(User user, Integer pageNum, Integer pageSize){
        System.out.println("打印数据："+user+pageNum+pageSize);
        return userService.selectUserPage(user,pageNum,pageSize);
    }

    /**新增用户*/
    @PostMapping
    public ResponseResult add(@RequestBody User user) {
        //后端验证数据

        //验证用户名是否为空
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        //验证用户名是否唯一
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //验证联系方式是否唯一
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        //验证邮箱是否唯一
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //保存用户信息、用户对应的角色
        return userService.addUser(user);
    }
    /**删除用户*/
    @DeleteMapping("/{id}")
    public ResponseResult remove(@PathVariable(name = "id") Long id) {
        userService.removeById(id);
        return ResponseResult.okResult();
    }
    /**批量删除用户*/
    @DeleteMapping("/ids")
    public ResponseResult removes(@RequestBody ArrayList<String> ids) {
        System.out.println("ids");
        System.out.println(ids);
        userService.removeByIds(ids);
        return ResponseResult.okResult();
    }
    /**修改用户状态*/
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeUserStatusDto changeUserStatusDto){
        User user = new User();
        user.setId(changeUserStatusDto.getUserId());
        user.setStatus(changeUserStatusDto.getStatus());
        return ResponseResult.okResult(userService.updateById(user));
    }

    /**根据用户编号-获取详细信息*/
    @GetMapping(value = { "/{userId}" })
    public ResponseResult getUserInfoAndRoleIds(@PathVariable(value = "userId") Long userId){

        //1、获取用户信息
        User user = userService.getById(userId);
        //2、获取角色id(集合)-根据用户id
        List<Long> roleIds= aBackRoleService.selectRoleIdByUserId(userId);
        //3、获取角色信息-根据角色id
        List<Role> roles = aBackRoleService.selectRoleAll();
        //4、将其封装为新的对象
        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user,roles,roleIds);
        return ResponseResult.okResult(vo);
    }
    /**根据用户编号-修改用户信息*/
    @PutMapping
    public ResponseResult edit(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseResult.okResult();
    }
}
