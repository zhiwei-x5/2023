package com.xzw.service.impl;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.LoginUser;
import com.xzw.domain.entity.User;
import com.xzw.domain.vo.BlogUserLoginVo;
import com.xzw.domain.vo.UserInfoVo;
import com.xzw.service.ABackLoginService;
import com.xzw.utils.BeanCopyUtils;
import com.xzw.utils.JwtUtil;
import com.xzw.utils.RedisCache;
import com.xzw.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ABackLoginServiceImpl implements ABackLoginService {
    /**由于在securityconfig中已经暴露了AuthenticationManager的类，因此此处阔以调用authentication方法*/
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //1、获取数据库的user信息
        /**由于authenticate类型的返回值为*/
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authentication)){
            throw new RuntimeException("登录异常");
        }
        //2、获取数据库返回的user信息中的userid，并使用jwt工具生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);

        //3、返回数据后，即可存入到redis中
        redisCache.setCacheObject("login:"+userid,loginUser);

        //4、将token封装到userinfo，并把userinfo转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的值
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
