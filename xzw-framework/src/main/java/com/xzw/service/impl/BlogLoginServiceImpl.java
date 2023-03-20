package com.xzw.service.impl;


import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.LoginUser;
import com.xzw.domain.entity.User;
import com.xzw.domain.vo.BlogUserLoginVo;
import com.xzw.domain.vo.UserInfoVo;
import com.xzw.service.BlogLoginService;
import com.xzw.utils.BeanCopyUtils;
import com.xzw.utils.JwtUtil;
import com.xzw.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        /**UsernamePasswordAuthenticationToken将用户密码转为authentication对象*/
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        /**会自动调用DaoAuthicationProvider然后再自动调用内部的UserDetailsService，因此这里需要自定义UserDetailsService替换原有的，
         这样便可访问数据库中的用户密码，然后返回的过程中会给DaoAuthicationProvider进行加密校验，如果认证通过则返回值不为null*/
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        /**UserDetailsService的loadUserByUsername返回的数据需要用一个类来储存，
         * 因此需要一个LoginUser来实现UserDetails才能存储UserDetailsService返回的数据*/
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        /**生成token用jwt变量名保存*/
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);
        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        /**为什么不能拿之前存过的LoginUser，因为那个属于局部引用获取不了，所以才要存到SecurityContextHolder往后的方法中都能调用*/
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }


}
