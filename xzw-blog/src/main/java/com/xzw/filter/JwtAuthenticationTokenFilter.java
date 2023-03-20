package com.xzw.filter;

import com.alibaba.fastjson.JSON;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.LoginUser;
import com.xzw.enums.AppHttpCodeEnum;
import com.xzw.utils.JwtUtil;
import com.xzw.utils.RedisCache;
import com.xzw.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
/**继承OncePerRequestFilter并重写doFilterInternal：让每次代码都会经过过滤器*/
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1、获取请求头中的token
        String token = request.getHeader("token");
        System.out.println("2022.11.28a"+token);
        /**如果里面的值为null,"","   "，那么返回值为false；否则为true*/
        if(!StringUtils.hasText(token)){
            System.out.println("2022.11.28b"+token);
            //说明该接口不需要登录  直接放行：因为有一些页面请求不需要token不需要登录，后面还会有拦截器进行拦截
            /**当一个filter收到请求的时候，调用chain.doFilter才可以访问下一个匹配的filter，若当前的filter是最后一个filter，调用chain.doFilter才能访问目标资源*/
            filterChain.doFilter(request, response);
            return;
        }
        //2、解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            /**统一异常处理是针对controller层的，此处在controller之前因此不会被捕获处理*/
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            /**将字符串渲染到客户端，将对象转化为Json字符串*/
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //3、从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);
        //如果获取不到
        if(Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //4、存入SecurityContextHolder
        /**存入SecurityContextHolder保存认证信息的核心组件，重点是将给定的认证信息(SecurityContext)与当前执行线程关联。
         * 也就是说在同一个线程中可以通过该组件随时方便的获得认证信息,基本操作*/
        /**之后为什么不能拿现在存的LoginUser，因为那个属于局部引用获取不了，所以才要存到SecurityContextHolder往后的过滤器中都能调用*/
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        /**过滤器执行完，要进行放行，还要在SecurityConfig将其配置进过滤链中*/
        filterChain.doFilter(request, response);
    }


}
