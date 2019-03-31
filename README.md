## smart-oAuth简介
smart-oAuth的愿想是实现一个可随拿随用的spring-security构架，基于JDK1.8和SpringBoot；
## smart-oAuth模块介绍
1.  **smart-security**：smart-oAuth基于maven的多模块实现，其中smart-security为父模块，不具备具体的代码实现，作为整体模块的pom引用而存在；
2.  **smart-security-core**：smart-security的核心代码实现，其中smart-security-browser和smart-security-app的重复代码也将会被提取到core中作为公共方法存在；
3.  **smart-security-browser**：如果你的服务需要引入spring-security作为安全模块，不妨直接引入smart-security-browser，它将为你的服务提供最直接的安全过滤，而你的服务则只需要
配置对应的登录页面地址等基本参数即可，详情可查看后续说明；
4.  **smart-security-app**：于smart--security-browser直接开发与浏览器相关的内容不同，smart-security-app针对的客户端对象为非浏览器的对象，如APP，前后端分离后的前端服务，这些服务的交互
并非是直接通过浏览器进行后端请求的交互，而是服务与服务的请求（APP -> 后端，前端服务 -> 后端），那么此时后端服务则需直接引入smart-app即可，它将为你实现用户登录请求的Token生成，Token刷新等，
且支持oAuth2协议（授权码模式，密码模式等）；
5.  **smart-security-example**：顾名思义该项目主要为实例demo项目，在对应的pom.xml中分别引入smart-security-browser，和smart-security-app作为对应的安全模块，
以此展示第三方项目引入browser和app时的对接配置方式；
## 功能介绍
### smart-security-browser
+   [自定义登录页配置（√）](https://github.com/DearZh/smart-oAuth/tree/master/smart-security-browser)
+   [登录后返回结果类型配置（√）](https://github.com/DearZh/smart-oAuth/tree/master/smart-security-browser)
+   基于Social实现第三方服务授权登录（微信，QQ授权登录 --基于oAuth2的客户端对接展示）
+   实现登录时记住我功能
+   实现短信验证码登录
### smart-security-app
+   [实现基于oAuth2的服务认证授权（√）](https://github.com/DearZh/smart-oAuth/tree/master/smart-security-app)
+   基于oAuth2的SSO（多模块授权实现）
