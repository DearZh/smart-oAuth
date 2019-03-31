# smart-security-browser

## 配置
+   在使用smart-security-browser的服务中，新增smart.security.browser.loginPage=/example-login.html，
表示自定义当前服务的登录地址，不配置时则使用smart-security-browser中的默认登录地址；
+   新增smart.security.browser.loginType=REDIRECT 表示用户登录成功后，直接跳转到用户登录前的访问地址上；配置为JSON时则表示用户登录成功后返回对应的JSON数据（适用于AJAX登录时做登录后回调使用）
