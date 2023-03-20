package com.xzw.controller;

import com.xzw.annotation.SystemLog;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.User;
import com.xzw.enums.AppHttpCodeEnum;
import com.xzw.exception.SystemException;
import com.xzw.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "登录信息")
    public ResponseResult login(@RequestBody User user){
        //如果字符串里面的值为null， ""， "   "，那么返回值为false；否则为true
        if(!StringUtils.hasText(user.getUserName())){
            /**虽然前端也需要校验，但有一些可以绕过前端进行访问*/
            //提示 必须要传用户名
            /**报出异常后调用AppHttpCodeEnum枚举类的REQUIRE_USERNAME
           并会被GlobalExceptionHandler捕获，然后以响应统一格式ResponseResult返回前端*/
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
