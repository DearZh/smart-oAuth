package com.smart.security.browser.config;

import com.smart.security.core.common.properties.LoginResponseType;
import com.smart.security.core.common.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功操作
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
@Component(value = "browserAuthenticationSuccessHandler")
public class BrowserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
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

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        logger.warn("----------登录失败-------------");

        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            //打印具体的authentication值
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            //则跳转到对应的登录前访问页面
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }

        /*
            SecurityContextPersistenceFilter 拦截器的进出，皆以ThreadLocal和session进行交换,保证请求的共享
            authentication = SecurityContextHolder.getContext().getAuthentication() arnold.zhao 2019/3/30
         */

    }

}
