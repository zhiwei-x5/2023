package com.xzw.handler.security;

import com.alibaba.fastjson.JSON;
import com.xzw.domain.ResponseResult;
import com.xzw.enums.AppHttpCodeEnum;
import com.xzw.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**响应的格式肯定是不符合我们项目的接口规范的。所以需要自定义异常处理。*/
@Component
/**认证失败*/
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        //InsufficientAuthenticationException：权限不足，未登录
        //BadCredentialsException：登录错误所报的异常，被锁定
        ResponseResult result = null;
        if(authException instanceof BadCredentialsException){
            /**LOGIN_ERROR(505,"用户名或密码错误");*/
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
            /**NEED_LOGIN(401,"需要登录后操作"),*/
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            /**SYSTEM_ERROR(500,"出现错误"),*/
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
