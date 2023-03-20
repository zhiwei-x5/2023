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
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**@Override 注解是伪代码，表示子类重写父类的方法。这个注解不写也是可以的*/
    @Override
    @Bean
    /**重写这个方法就能将AuthenticationManager注入到容器中，在前面加入@Bean
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
                .antMatchers("/user/login").anonymous()
                //.antMatchers("/link/getAllLink").authenticated()
                //注销接口需要认证才能访问
                .antMatchers("/logout").authenticated()
                .antMatchers("/user/userInfo").authenticated()
                //  .antMatchers("/upload").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();

        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)//认证异常处理器
                .accessDeniedHandler(accessDeniedHandler);//授权异常处理器

        //关闭默认的注销功能
        http.logout().disable();
        /**把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链UsernamePasswordAuthenticationFilter
            之前，如此即可在每次请求之前都进行token的判断，无论token是否还有效都会进行放行
         */
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.cors();
    }
    /**我们只需要使用把BCryptPasswordEncoder对象注入Spring容器中，
     * SpringSecurity就会使用该PasswordEncoder来进行密码校验。*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
