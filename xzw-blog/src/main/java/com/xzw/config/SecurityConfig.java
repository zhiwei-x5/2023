package com.xzw.config;

import com.xzw.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
/**WebSecurityConfigurerAdapter 中我覆写（@Override）了三个configure方法，
  我们一般会通过自定义配置这三个方法来自定义我们的安全访问策略。
 configure(AuthenticationManagerBuilder auth):核心过滤器配置方法
 configure(HttpSecurity http):安全过滤器链配置方法
     (所有的请求访问都需要被授权。
     使用 form 表单进行登陆(默认路径为/login)，也就是前几篇我们见到的登录页。
     防止 CSRF 攻击、 XSS 攻击。
     启用 HTTP Basic 认证)
 configure(AuthenticationManagerBuilder auth):认证管理器配置方法
 * */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**@Override 注解是伪代码，表示子类重写父类的方法。这个注解不写也是可以的*/
    @Override
    @Bean
    /**重写这个方法就能将AuthenticationManager注入到容器中，在前面加入@Bean即可注入
     * 如此一来BlogLoginServiceImpl类中就可以获取AuthenticationManager并调用其authenticate方法*/
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /**@Autowired 顾名思义，就是自动装配，其作用是为了消除代码Java代码里面的getter/setter与bean属性中的property。*/
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf:我们使用的是token不必担心csrf漏洞
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/login").anonymous()
                //.antMatchers("/link/getAllLink").authenticated()
                //注销接口需要认证才能访问
                .antMatchers("/logout").authenticated()
                .antMatchers("/user/userInfo").authenticated()
                //.antMatchers("/upload").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();

        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)//认证异常处理器
                .accessDeniedHandler(accessDeniedHandler);//授权异常处理器

        //关闭默认的注销功能，如此退出登录才能使用
        http.logout().disable();
        //把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中，添加到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.cors();
    }
    /**
     实际项目中我们不会把密码明文存储在数据库中。
     ​	默认使用的PasswordEncoder要求数据库中的密码格式为：{id}password 。它会根据id去判断密码的加密方式。但是我们一般不会采用这种方式。
     ​	所以就需要替换PasswordEncoder。我们一般使用SpringSecurity为我们提供的BCryptPasswordEncoder。
     ​	我们只需要使用把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码校验。
     ​	我们可以定义一个SpringSecurity的配置类，SpringSecurity要求这个配置类要继承WebSecurityConfigurerAdapter。
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
