package com.smart.security.core.common.properties;

/**
 * 针对浏览器的服务对接时,做配置文件的读取
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
public class BrowserProperties {

    //当前服务未配置登录页时，则使用browser下的默认登录页面
    private String loginPage = "/browser-login.html";

    /**
     * @Description: 设置登录成功或失败后，对应的返回方式（默认为返回JSON）
     * @Author: Arnold.zhao
     * @Date: 2019/3/30
     */
    private LoginResponseType loginType = LoginResponseType.JSON;

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
