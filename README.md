# 🚀Id Server
[Id Server](https://github.com/NotFound403/id-server)是一个基于[Spring Authorization Server](https://github.com/spring-projects/spring-authorization-server)的开源的授权服务器，拉取代码直接运行，无需过多配置。欢迎Star，如果有兴趣也可以对本项目发起贡献。
- github: [https://github.com/NotFound403/id-server](https://github.com/NotFound403/id-server)
- gitee: [https://gitee.com/felord/id-server](https://gitee.com/felord/id-server)
- 文档建设中……
## 主要功能
- 开箱即用，只需要少量配置即可使用。
- 创建OAuth2客户端，并对OAuth2客户端进行管理。
- 提供OAuth2授权服务。
- 支持四种客户端认证方式：
  - **CLIENT_SECRET_BASIC**
  - **CLIENT_SECRET_POST**
  - **CLIENT_SECRET_JWT**
  - **PRIVATE_KEY_JWT**
- 支持三种OAuth2授权方式：
  - **AUTHORIZATION_CODE**
  - **CLIENT_CREDENTIALS**
  - **REFRESH_TOKEN**
- 支持以下用户认证方式：
  - 账密登录
  - 手机号验证码登录
  - 小程序登录
- **OIDC 1.0**的支持（完善中）。
- 一键生成配置`yaml`文件。
- 提供UI控制台，降低上手成本。
- 可动态调整管理员的用户角色，对授权服务器进行按钮功能级别的权限控制。
## 环境与技术
- Java 11及以上
- **Spring Boot**
- **Spring Security**
- **Spring Authorization Server**
- **Spring Data JPA**
- **pear admin layui**
- **thymeleaf**
- 数据库
  - **H2**
  - **Mysql**

## 简单用法
- 拉取主分支最新代码到本地。
- 通过`IdServerApplication`来启动授权服务器。管理控制台本地登录路径为`http://localhost:9000/system/login`，最高权限用户为`root`，密码为`idserver`。
- 你可以通过`root`用户做这些事情：
  - 创建角色（角色管理）并为角色绑定权限。
  - 创建控制台管理用户（用户管理），并赋予他们角色。
> 退出功能还未完善，需要通过关闭浏览器来清除session。
## OAuth2 测试方法
- 启动**Id Server**，默认情况下在客户端列表提供了一个内置的OAuth2客户端。
- 样例客户端在`samples`文件夹下，直接启动，浏览器配置文件下的`http://127.0.0.1:8082/foo/bar`，进入登录页，输入用户名`user`和密码`user`即可。
- 你也可以在Id Server中创建一个客户端并模仿DEMO中的配置，主要修改`client-id`,`client-secret`,`client-authentication-method`,`scope`，其它选项除非你比较了解OAuth2，否则先不要动，也可以通过issue咨询。
> `redirect-uri`必须在授权服务器Id Server注册客户端时声明。
### 如何替换内置用户user
首先要正确区分**管理用户**和**普通用户**这两个概念。
#### 管理用户
`root`及其创建的用户为UI控制台的管理用户，超级管理员`root`是目前提供了一个默认用户，具有Id Server的最高权限。如果你需要自定义，可实现`RootUserDetailsService`接口并注入**Spring IoC**。
#### 普通用户
普通用户就是OAuth2中的资源拥有者，主要对OAuth2客户端的授权请求进行授权。默认提供了一个`user`用来演示，开发者可以实现`OAuth2UserDetailsService`接口并注入**Spring IoC**来自定义用户的来源。
### 手机号验证码登录
现在OAuth2授权增加了手机号验证码登录，灵感来自[]()扩展包，不影响原有的OAuth2授权流程。资源拥有者可以在下面的页面选择认证方式：
![](https://asset.felord.cn/blog/20220520111451.png)
#### 关闭验证码认证方式
对于不使用验证码认证方式的，可以通过`OAuth2LoginController#oauth2LoginPage`接口中的`enableCaptchaLogin`参数进行调整，默认值为`true`（开启）。
## 环境
目前**Id Server**提供**H2**和**Mysql**两种数据库环境，分别对应`application-h2.yml`和`application-mysql.yml`两个配置文件。
- **H2**，默认数据库，在**H2**环境下，数据库DDL脚本和DML脚本会自动执行，无需开发者手动执行，该环境主要用来测试、研究、学习。
- **Mysql**，生产推荐，**首次启动时开发者手动执行初始化DML脚本**。
> 目前两种环境的效果是一致的，H2时间长会发生表丢失情况，切换时务必在`pom.xml`中更换对应的数据库驱动程序依赖。
## 截图
![控制台台登录](https://asset.felord.cn/blog/20220512143700.png)
![首页](https://asset.felord.cn/blog/20220512134905.png)
![通过UI创建OAuth2客户端](https://asset.felord.cn/blog/20220512135204.png)
![创建管理用户](https://asset.felord.cn/blog/20220512135249.png)
![一键生成配置](https://asset.felord.cn/blog/20220513141607.gif)
![角色授权](https://asset.felord.cn/blog/20220512135420.png)
![授权登录](https://asset.felord.cn/blog/20220512143317.png)
![授权确认](https://asset.felord.cn/blog/20220512143550.png)


