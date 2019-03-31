package com.smart.security.browser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义用户信息认证
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
@Component
public class SmartUserDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        logger.warn("***********用户登录名称-UserName：*********" + userName);
        //TODO 根据用户名查找对应的用户信息
        //1. Browser服务提供用户查找接口，各具体实现服务：如example 提供具体的实现类，以此实现各服务的定制查询
        //2. 如果可以保证所有服务组件都查找的相同登录db，则此处browser服务直接实现具体的业务操作，（相同业务，相同场景，否则不靠谱）

        logger.warn("**********passwordEncoder.encode(\"123123\")**********" + passwordEncoder.encode("123123"));

        //返回对应的用户信息，db中所存储的：userName，passWord，授权权限集合
        User user = new User(userName, passwordEncoder.encode("123123"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        //User自定义用户行为
        /*
            private final boolean accountNonExpired;        //账户是否过期 NonExpired
            private final boolean accountNonLocked;         //账户是否锁定 NonLocked
            private final boolean credentialsNonExpired;    //密码是否过期 NonExpired
            private final boolean enabled;                  //用户是否可用（用户是否删除）
         */
        //user = new User(userName, "123123",true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        return user;
    }

}
