package com.smart.security.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.security.core.common.properties.LoginResponseType;
import com.smart.security.core.common.properties.SecurityProperties;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功操作(登录成功后返回token）
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
@Component(value = "appAuthenticationSuccessHandler")
public class AppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    /*
        SavedRequestAwareAuthenticationSuccessHandler 实现了AuthenticationSuccessHandler接口，
        不同的是，SavedRequestAware提供了默认的跳转方法，即直接跳转到登录前所对应的页面URL中，详情可查看该继承类的具体
        onAuthenticationSuccess()方法的实现；
        SavedRequestAware的实现方式与BrowserSecurityController的实现方式是相似的，都是通过
        requestCache的方式来获取RedirectURL的形式进行指定跳转 end；arnold.zhao 2019/3/30;
     */

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @Description:: JSON转换，也可更改SpringBoot中的JSON默认实现，改而fastJson等的转换器<br/>详情可查看smart-ssm-boot
     * @Author: Arnold.zhao
     * @Date: 2019/3/30
     */
    @Autowired
    private ObjectMapper objectMapper;

    /*@Autowired
    private SecurityProperties securityProperties;
*/
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //6-4 -当前代码参考： BasicAuthenticationFilter 类
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Basic ")) {
            String[] tokens = this.extractAndDecodeHeader(header, request);
            assert tokens.length == 2;
            String clientId = tokens[0];
            String clientSecret = tokens[1];


            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

            if (clientDetails == null) {
                throw new ServletException("第三方clientID不存在：" + clientId);
            } else if (!clientDetails.getClientSecret().equals(clientSecret)) {
                throw new ServletException("第三方clientSecret不匹配：" + clientSecret);
            }

            //自定义授权模式：custom，而非oAuth自定义的授权password等模式
            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

            response.setContentType("application/json;charset=UTF-8");
            //打印具体的authentication值
            response.getWriter().write(objectMapper.writeValueAsString(token));
        } else {
            throw new ServletException("请求头中无client信息");
        }

    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
