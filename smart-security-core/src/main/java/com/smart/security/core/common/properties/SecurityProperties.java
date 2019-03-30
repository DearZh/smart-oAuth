package com.smart.security.core.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 全局配置读取
 *
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-30
 */
@ConfigurationProperties(prefix = "smart.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
