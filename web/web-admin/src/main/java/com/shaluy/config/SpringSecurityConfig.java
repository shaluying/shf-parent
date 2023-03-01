package com.shaluy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 声明当前类是一个配置类 注意：配置类也需要被扫描
@EnableWebSecurity // 开启Spring Security的自动配置，会给我们生成一个登陆页面
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启controller控制层中方法的权限控制
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //在内存中设置一个认证的用户名和密码
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("");
    }*/

    //创建一个密码加密器放到ioc容器中
    @Bean //相当于在配置文件中的<bean><bean/>标签
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //默认Spring Security不允许iframe嵌套显示，我们需要设置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //目前必须调用父类的configure方法，否则认证过程将失效，除非当前方法配置了认证
//        super.configure(http);

        //配置允许iframe嵌套显示
//        http.headers().frameOptions().disable();
        //配置允许iframe显示
        http.headers().frameOptions().sameOrigin();

        //配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**","/login").permitAll()//允许匿名用户访问的路径
                .anyRequest().authenticated();// 其它页面全部需要验证

        //自定义登录页面
        http.formLogin().loginPage("/login")//用户未登录时，访问任何需要权限的资源都转跳到该路径，即登录页面，此时登陆成功后会继续跳转到第一次访问的资源页面（相当于被过滤了一下）
                        .defaultSuccessUrl("/"); //登录认证成功后默认转跳的路径

        //配置登出的地址及登出成功之后去往的地址
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        //关闭跨域请求伪造
        http.csrf().disable();

        //配置自定义的无权限访问的处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());

    }
}
