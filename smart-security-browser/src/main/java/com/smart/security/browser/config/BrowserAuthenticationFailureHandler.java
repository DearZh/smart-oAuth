package com.smart.security.browser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.security.core.common.properties.LoginResponseType;
import com.smart.security.core.common.properties.SecurityProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败自定义操作
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
@Component(value = "browserAuthenticationFailureHandler")
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.warn("----------登录失败-------------");
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            //登录失败默认状态码为500
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            //打印具体的authentication值
            response.getWriter().write(objectMapper.writeValueAsString(exception));
        } else {
            //则跳转到对应的登录前访问页面
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
