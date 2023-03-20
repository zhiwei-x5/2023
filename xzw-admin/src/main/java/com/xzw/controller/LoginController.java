package com.xzw.controller;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.LoginUser;
import com.xzw.domain.entity.Menu;
import com.xzw.domain.entity.User;
import com.xzw.domain.vo.AdminUserInfoVo;
import com.xzw.domain.vo.RoutersVo;
import com.xzw.domain.vo.UserInfoVo;
import com.xzw.enums.AppHttpCodeEnum;
import com.xzw.exception.SystemException;
import com.xzw.service.ABackLoginService;
import com.xzw.service.ABackMenuService;
import com.xzw.service.ABackRoleService;
import com.xzw.utils.BeanCopyUtils;
import com.xzw.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private ABackLoginService aBackLoginService;

    @Autowired
    private ABackMenuService aBackMenuService;

    @Autowired
    private ABackRoleService aBackRoleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        System.out.println("打印用户名密码");
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return aBackLoginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return aBackLoginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = aBackMenuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = aBackRoleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //对以上数据进行封装
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = aBackMenuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }
}
