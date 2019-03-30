package com.smart.security.browser.config;

import com.smart.security.core.common.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求分发
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
@RestController
public class BrowserSecurityController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @Description: 身份认证时的请求分发
     * @Param: [request, response]
     * @return: java.lang.String
     * @Author: Arnold.zhao
     * @Date: 2019/3/30
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Object require(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获取缓存的request
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            //获取跳转前链接，与直接获取head中reference相同
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println(targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                /*
                     此处做页面跳转，后续可以针对各个所接入的服务，做各自单独的html登录页
                    （如：client1和client2都需要有不同的登录页效果，则根据所对应的接入服务，跳转到不同的登录页面中） arnold.zhao 2019/3/30
                 */
                //实现各服务各自跳转对应服务的登录页效果
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }

        }

        List<String> list = new ArrayList<>();
        list.add("服务未经授权，请先执行登录操作");
        return list;
    }
}
