package com.smart.security.app.config.oAuth;

import com.smart.security.app.config.SmartUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * oAuth 认证服务器
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-31
 */
@Configuration
@EnableAuthorizationServer
public class SmartAuthenticationServiceConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SmartUserDetailService smartUserDetailService;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
                .withClient("smart")
                .secret("smart-secret")
                .redirectUris("http://localhost:8088")
                .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token",
                        "password", "implicit")
                .scopes("all")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(1200)
                .refreshTokenValiditySeconds(50000);
        */
        clients.inMemory()
                .withClient("smart")
                .secret("smart-secret")
                .redirectUris("http://localhost:8088")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .scopes("all")
                .accessTokenValiditySeconds(7200);
    }

    /**
     * @Description: 令牌刷新机制
     * @Param: [endpoints]
     * @return: void
     * @Author: Arnold.zhao
     * @Date: 2019/3/31
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(smartUserDetailService);

    }
}

