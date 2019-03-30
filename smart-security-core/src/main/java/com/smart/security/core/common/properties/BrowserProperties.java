package com.smart.security.core.common.properties;

/**
 * 针对
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
public class BrowserProperties {

    //当前服务未配置登录页时，则使用browser下的默认登录页面
    private String loginPage = "/browser-login.html";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
