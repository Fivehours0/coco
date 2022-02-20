<p align="center">
 <img src="https://img.shields.io/badge/Spring%20Cloud-2021-blue.svg" alt="Coverage Status">
 <img src="https://img.shields.io/badge/Spring%20Boot-2.6-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/license-apache2.0-blue.svg" alt="License"/>
</p>

## **项目简介**
Coco是一款仿Pig的RBAC **权限管理系统**。与Pig的区别是：Coco是权限管理系统的最小实现，并且包含必要的系统功能。

+ Coco 配套前端代码：https://gitee.com/du_zhi_hui/pig-ui

+ Coco 部署指南：https://www.kancloud.cn/hhdzh/coco/2648995

+ Coco 在线提问：https://github.com/Fivehours0/coco/issues

Coco 基于经典技术组合（SpringBoot、SpringCloud、SpringCloud Alibaba、Spring Security Oauths2、MyBatis），内置模块如：部门管理、角色用户管理、菜单及按钮授权、数据权限、日志管理、令牌管理等。

## **核心依赖**
| 依赖 | 版本 |
| --- | --- |
| Spring Boot | 2.6.1 |
| Spring Cloud | 2021.0.0 |
| Spring Cloud Alibaba | 2021.1 |
| Spring Security OAuth2 | 2.3.4 |
| Mybatis Plus | 3.4.3.4 |
| hutool | 5.7.16 |

## **内置功能**
*   用户管理：该功能完成对用户信息，状态的配置。
*   部门管理：采用树结构展现部门层级结构，并对部门信息进行配置。
*   菜单管理：配置系统菜单，操作权限，按钮权限标识等。
*   角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
*   操作日志：对系统正常操作与异常操作信息进行记录
*   登录日志：对用户登录与登出信息进行记录。
*   终端管理：支持对终端信息进行添加，查看，删除，修改操作
*   服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
*   令牌管理：管理登录用户的令牌信息，可删除令牌信息
*   系统缓存：对用户信息，菜单信息等进行缓存。

## **文件结构**

~~~
com.coco    
├── coco-auth -- 授权服务提供[3100]
├── coco-common  -- 系统公共模块
│       └── coco-common-core  -- 系统常量、枚举类以及工具类
│       └── coco-common-log  -- 操作日志服务
│       └── coco-common-security  -- 安全相关工具类
├── coco-gateway -- 网关服务[9999]
├── coco-monitor -- 监控服务[5001]
├── coco-upms-- 用户权限管理模块
│       └── coco-upms-api  -- 用户权限管理模块相关服务实现[4100]
│       └── coco-upms-common  -- 用户权限管理公共模块
~~~

## **示例图片**
<table>
  <tr>
    <td><img src="https://github.com/Fivehours0/coco/blob/master/img/login.png"></td>
    <td><img src="https://github.com/Fivehours0/coco/blob/master/img/user.png"></td>
  </tr>
    <tr>
    <td><img src="https://github.com/Fivehours0/coco/blob/master/img/auth.png"></td>
    <td><img src="https://github.com/Fivehours0/coco/blob/master/img/log.png"></td>
  </tr>
</table>