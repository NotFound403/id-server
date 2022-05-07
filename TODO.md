## ID Server功能清单

### OAuth2客户端管理

- 客户端列表
- 客户端新增
- 客户端oauth2授权配置（核心）
  - 客户端认证方法 `ClientAuthMethod`，固定枚举，下拉列表，支持多选。
  - 客户端授权方式 `OAuth2GrantType` 固定枚举，下拉列表，支持多选。
  - 重定向URI `RedirectUri`，支持动态输入。
  - 授权范围 `OAuth2Scope`，接口提供，可多选下拉。
  - 客户端其它配置 `OAuth2ClientSettings` 普通输入。
  - 客户端token配置 `OAuth2TokenSettings` 普通输入。

### 关联角色管理

角色是后台管理的角色

### 后台接口权限管理

主要是后台管理用户的资源管理

- 菜单管理
- 接口权限角色管理

### 用户管理

用户分为后台管理用户和普通用户，这几种用户通过角色区分。

- 用户列表

- 用户增删改查

  

