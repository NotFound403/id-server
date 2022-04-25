# 🚀id-server

一个基于**Spring Authorization Server**的开源的授权服务器。

## 概念
一些概念
### client 

**客户端**指的是**OAuth2 Client**，但又不单单是一个**OAuth2 Client**，连**id server**本身都是一个**客户端**。
#### role

角色必须依附于客户端的存在，本项目的根本就在于管理客户端，所以角色必须有客户端的属性，这样和**SCOPE**的设计才是一致的。

> `role`和`scope`的关系是怎样的？

`role`和`scope`其实是一个东西，只不过面向的对象不一样。`role`针对的是资源拥有者（Resource Owner），而`scope`针对的是**OAuth2客户端**。举个例子，`ROLE_email`是用户具有获取电子邮件信息的接口访问权限，`SCOPE_email`是拥有`ROLE_email`权限的用户授权OAuth2客户端访问获取电子邮件信息接口，用户如果没有这个权限，那他凭什么授权呢？
> 所以结论就是： `ROLE_email`=`SCOPE_email`=`email`。
##### 规则
- 一个客户端下`role`是唯一的。
- 只有生效的`role`才起作用。
- 在`client`中的`scope`必须在`role`中定义。
- 在`role`中定义的角色不一定会在`client`中。
- 用户授权的`role`必须在`client`中声明以及用户必须持有该`role`。
