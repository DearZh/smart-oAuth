package com.smart.security.browser.config;

import com.smart.security.core.common.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 重写Security适配器方法
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @Description: 用户密码加解密方式
     * @Param: []
     * @return: org.springframework.security.crypto.password.PasswordEncoder
     * @Author: Arnold.zhao
     * @Date: 2019/3/30
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        //注：此处使用passWordEncoder的默认实现 -->BCryptPasswordEncoder implements PasswordEncoder
        //项目中使用MD5，base等其它自定义的密码加密方式时，则自定义一个新的密码转义类，实现上述的PasswordEncoder接口即可,arnold.zhao 2019/3/30
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //增加formLogin的认证方式
        http.formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")//定义formLogin所拦截的登录请求URL；关于formLogin属性可见：https://blog.csdn.net/yin380697242/article/details/51893397
                .and()
                .authorizeRequests()    //授权方式
                .antMatchers("/authentication/require").permitAll()
                .antMatchers(securityProperties.getBrowser().getLoginPage()).permitAll() //此处实现各服务跳转各登录页效果（需给当前服务登录页授权）
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();//关闭跨站伪请求拦截


        //basic认证
        //http.httpBasic();
    }
}
