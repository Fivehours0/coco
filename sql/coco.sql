DROP DATABASE IF EXISTS `coco`;

CREATE DATABASE  `coco` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE coco;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父节点',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '总经办', 0, '0', 0, '2020-03-13 13:13:16', NULL, '2020-03-13 13:14:31', NULL);
INSERT INTO `sys_dept` VALUES (2, '行政中心', 1, '0', 1, '2020-03-13 13:13:30', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (3, '技术中心', 2, '0', 1, '2020-03-13 13:14:55', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (4, '运营中心', 3, '0', 1, '2020-03-13 13:15:15', NULL, '2022-01-22 19:16:40', 'root');
INSERT INTO `sys_dept` VALUES (5, '研发中心', 5, '0', 3, '2020-03-13 13:15:34', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (6, '产品中心', 6, '0', 3, '2020-03-13 13:15:49', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (7, '测试中心', 7, '0', 3, '2020-03-13 13:16:02', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_relation`;
CREATE TABLE `sys_dept_relation`  (
  `ancestor` int(11) NOT NULL COMMENT '祖先节点',
  `descendant` int(11) NOT NULL COMMENT '后代节点',
  PRIMARY KEY (`ancestor`, `descendant`) USING BTREE,
  INDEX `idx1`(`ancestor`) USING BTREE,
  INDEX `idx2`(`descendant`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept_relation
-- ----------------------------
INSERT INTO `sys_dept_relation` VALUES (1, 1);
INSERT INTO `sys_dept_relation` VALUES (1, 2);
INSERT INTO `sys_dept_relation` VALUES (1, 3);
INSERT INTO `sys_dept_relation` VALUES (1, 4);
INSERT INTO `sys_dept_relation` VALUES (1, 5);
INSERT INTO `sys_dept_relation` VALUES (1, 6);
INSERT INTO `sys_dept_relation` VALUES (1, 7);
INSERT INTO `sys_dept_relation` VALUES (2, 2);
INSERT INTO `sys_dept_relation` VALUES (3, 3);
INSERT INTO `sys_dept_relation` VALUES (3, 5);
INSERT INTO `sys_dept_relation` VALUES (3, 6);
INSERT INTO `sys_dept_relation` VALUES (3, 7);
INSERT INTO `sys_dept_relation` VALUES (4, 4);
INSERT INTO `sys_dept_relation` VALUES (5, 5);
INSERT INTO `sys_dept_relation` VALUES (6, 6);
INSERT INTO `sys_dept_relation` VALUES (7, 7);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务ID',
  `remote_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方式',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作提交的数据',
  `time` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记',
  `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_log_create_by`(`create_by`) USING BTREE,
  INDEX `sys_log_request_uri`(`request_uri`) USING BTREE,
  INDEX `sys_log_type`(`type`) USING BTREE,
  INDEX `sys_log_create_date`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 285 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单权限标识',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端URL',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'VUE页面',
  `sort` int(11) NOT NULL DEFAULT 1 COMMENT '排序值',
  `keep_alive` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0-开启，1- 关闭',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单类型 （0菜单 1按钮）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1000, '权限管理', NULL, '/admin', -1, 'icon-quanxianguanli', NULL, 0, '0', '0', '0', NULL, '2018-09-28 08:29:53', NULL, '2020-03-11 23:58:18');
INSERT INTO `sys_menu` VALUES (1100, '用户管理', NULL, '/admin/user/index', 1000, 'icon-yonghuguanli', NULL, 1, '0', '0', '0', NULL, '2017-11-02 22:24:37', NULL, '2020-03-12 00:12:57');
INSERT INTO `sys_menu` VALUES (1101, '用户新增', 'sys_user_add', NULL, 1100, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 09:52:09', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1102, '用户修改', 'sys_user_edit', NULL, 1100, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 09:52:48', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1103, '用户删除', 'sys_user_del', NULL, 1100, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 09:54:01', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1104, '导入导出', 'sys_user_import_export', NULL, 1100, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 09:54:01', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1200, '菜单管理', NULL, '/admin/menu/index', 1000, 'icon-caidanguanli', NULL, 2, '0', '0', '0', NULL, '2017-11-08 09:57:27', NULL, '2020-03-12 00:13:52');
INSERT INTO `sys_menu` VALUES (1201, '菜单新增', 'sys_menu_add', NULL, 1200, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 10:15:53', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1202, '菜单修改', 'sys_menu_edit', NULL, 1200, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 10:16:23', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1203, '菜单删除', 'sys_menu_del', NULL, 1200, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 10:16:43', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1300, '角色管理', NULL, '/admin/role/index', 1000, 'icon-jiaoseguanli', NULL, 3, '0', '0', '0', NULL, '2017-11-08 10:13:37', NULL, '2020-03-12 00:15:40');
INSERT INTO `sys_menu` VALUES (1301, '角色新增', 'sys_role_add', NULL, 1300, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 10:14:18', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1302, '角色修改', 'sys_role_edit', NULL, 1300, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 10:14:41', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1303, '角色删除', 'sys_role_del', NULL, 1300, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 10:14:59', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1304, '分配权限', 'sys_role_perm', NULL, 1300, NULL, NULL, 1, '0', '1', '0', NULL, '2018-04-20 07:22:55', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1400, '部门管理', NULL, '/admin/dept/index', 1000, 'icon-web-icon-', NULL, 4, '0', '0', '0', NULL, '2018-01-20 13:17:19', NULL, '2020-03-12 00:15:44');
INSERT INTO `sys_menu` VALUES (1401, '部门新增', 'sys_dept_add', NULL, 1400, NULL, NULL, 1, '0', '1', '0', NULL, '2018-01-20 14:56:16', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1402, '部门修改', 'sys_dept_edit', NULL, 1400, NULL, NULL, 1, '0', '1', '0', NULL, '2018-01-20 14:56:59', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (1403, '部门删除', 'sys_dept_del', NULL, 1400, NULL, NULL, 1, '0', '1', '0', NULL, '2018-01-20 14:57:28', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2000, '系统管理', NULL, '/setting', -1, 'icon-xitongguanli', NULL, 1, '0', '0', '0', NULL, '2017-11-07 20:56:00', NULL, '2020-03-11 23:52:53');
INSERT INTO `sys_menu` VALUES (2100, '日志管理', NULL, '/admin/log/index', 2000, 'icon-rizhiguanli', NULL, 5, '0', '0', '0', NULL, '2017-11-20 14:06:22', NULL, '2020-03-12 00:15:49');
INSERT INTO `sys_menu` VALUES (2101, '日志删除', 'sys_log_del', NULL, 2100, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-20 20:37:37', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2102, '导入导出', 'sys_log_import_export', NULL, 2100, NULL, NULL, 1, '0', '1', '0', NULL, '2017-11-08 09:54:01', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2200, '字典管理', NULL, '/admin/dict/index', 2000, 'icon-navicon-zdgl', NULL, 6, '0', '0', '1', NULL, '2017-11-29 11:30:52', NULL, '2020-03-12 00:15:58');
INSERT INTO `sys_menu` VALUES (2201, '字典删除', 'sys_dict_del', NULL, 2200, NULL, NULL, 1, '0', '1', '1', NULL, '2017-11-29 11:30:11', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2202, '字典新增', 'sys_dict_add', NULL, 2200, NULL, NULL, 1, '0', '1', '1', NULL, '2018-05-11 22:34:55', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2203, '字典修改', 'sys_dict_edit', NULL, 2200, NULL, NULL, 1, '0', '1', '1', NULL, '2018-05-11 22:36:03', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2300, '令牌管理', NULL, '/admin/token/index', 2000, 'icon-denglvlingpai', NULL, 11, '0', '0', '0', NULL, '2018-09-04 05:58:41', NULL, '2020-03-13 12:57:25');
INSERT INTO `sys_menu` VALUES (2301, '令牌删除', 'sys_token_del', NULL, 2300, NULL, NULL, 1, '0', '1', '0', NULL, '2018-09-04 05:59:50', NULL, '2020-03-13 12:57:34');
INSERT INTO `sys_menu` VALUES (2400, '终端管理', '', '/admin/client/index', 2000, 'icon-shouji', NULL, 9, '0', '0', '0', NULL, '2018-01-20 13:17:19', NULL, '2020-03-12 00:15:54');
INSERT INTO `sys_menu` VALUES (2401, '客户端新增', 'sys_client_add', NULL, 2400, '1', NULL, 1, '0', '1', '0', NULL, '2018-05-15 21:35:18', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2402, '客户端修改', 'sys_client_edit', NULL, 2400, NULL, NULL, 1, '0', '1', '0', NULL, '2018-05-15 21:37:06', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2403, '客户端删除', 'sys_client_del', NULL, 2400, NULL, NULL, 1, '0', '1', '0', NULL, '2018-05-15 21:39:16', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2500, '服务监控', NULL, 'http://localhost:5001', 2000, 'icon-server', NULL, 10, '0', '0', '0', NULL, '2018-06-26 10:50:32', NULL, '2019-02-01 20:41:30');
INSERT INTO `sys_menu` VALUES (2600, '文件管理', NULL, '/admin/file/index', 2000, 'icon-wenjianguanli', NULL, 10, '0', '0', '1', NULL, '2018-06-26 10:50:32', NULL, '2019-02-01 20:41:30');
INSERT INTO `sys_menu` VALUES (2601, '文件删除', 'sys_file_del', NULL, 2600, NULL, NULL, 1, '0', '1', '1', NULL, '2017-11-29 11:30:11', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2602, '文件新增', 'sys_file_add', NULL, 2600, NULL, NULL, 1, '0', '1', '1', NULL, '2018-05-11 22:34:55', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (2603, '文件修改', 'sys_file_edit', NULL, 2600, NULL, NULL, 1, '0', '1', '1', NULL, '2018-05-11 22:36:03', NULL, '2021-05-25 06:48:34');
INSERT INTO `sys_menu` VALUES (3000, '开发平台', NULL, '/gen', -1, 'icon-shejiyukaifa-', NULL, 3, '1', '0', '1', NULL, '2020-03-11 22:15:40', NULL, '2020-03-11 23:52:54');
INSERT INTO `sys_menu` VALUES (3100, '数据源管理', NULL, '/gen/datasource', 3000, 'icon-mysql', NULL, 1, '1', '0', '1', NULL, '2020-03-11 22:17:05', NULL, '2020-03-12 00:16:09');
INSERT INTO `sys_menu` VALUES (3200, '代码生成', NULL, '/gen/index', 3000, 'icon-weibiaoti46', NULL, 2, '0', '0', '1', NULL, '2020-03-11 22:23:42', NULL, '2020-03-12 00:16:14');
INSERT INTO `sys_menu` VALUES (3300, '表单管理', NULL, '/gen/form', 3000, 'icon-record', NULL, 3, '1', '0', '1', NULL, '2020-03-11 22:19:32', NULL, '2020-03-12 00:16:18');
INSERT INTO `sys_menu` VALUES (3301, '表单新增', 'gen_form_add', NULL, 3300, '', NULL, 0, '0', '1', '1', NULL, '2018-05-15 21:35:18', NULL, '2020-03-11 22:39:08');
INSERT INTO `sys_menu` VALUES (3302, '表单修改', 'gen_form_edit', NULL, 3300, '', NULL, 1, '0', '1', '1', NULL, '2018-05-15 21:35:18', NULL, '2020-03-11 22:39:09');
INSERT INTO `sys_menu` VALUES (3303, '表单删除', 'gen_form_del', NULL, 3300, '', NULL, 2, '0', '1', '1', NULL, '2018-05-15 21:35:18', NULL, '2020-03-11 22:39:11');
INSERT INTO `sys_menu` VALUES (3400, '表单设计', NULL, '/gen/design', 3000, 'icon-biaodanbiaoqian', NULL, 4, '1', '0', '1', NULL, '2020-03-11 22:18:05', NULL, '2020-03-12 00:16:25');
INSERT INTO `sys_menu` VALUES (9999, '系统官网', NULL, 'https://pig4cloud.com/#/', -1, 'icon-guanwangfangwen', NULL, 9, '0', '0', '1', NULL, '2019-01-17 17:05:19', 'admin', '2020-03-11 23:52:57');
INSERT INTO `sys_menu` VALUES (10005, 'df', NULL, 'dfa', 10003, 'icon-web-icon-', NULL, 999, '0', '0', '0', 'admin', '2022-01-19 18:26:58', 'admin', '2022-01-19 18:26:58');
INSERT INTO `sys_menu` VALUES (10006, 'dfsds', NULL, 'sfsfd', -1, 'icon-weibiaoti46', NULL, 999, '0', '0', '1', 'admin', '2022-01-19 18:27:33', 'admin', '2022-01-19 18:27:33');
INSERT INTO `sys_menu` VALUES (10007, 'as', NULL, 's', -1, 'icon-rizhiguanli', NULL, 999, '0', '0', '1', 'admin', '2022-02-07 14:19:31', 'admin', '2022-02-07 14:24:04');
INSERT INTO `sys_menu` VALUES (10008, 'as', NULL, 'asd', -1, 'icon-web-icon-', NULL, 999, '0', '0', '1', 'admin', '2022-02-07 14:19:31', 'admin', '2022-02-07 14:24:04');
INSERT INTO `sys_menu` VALUES (10009, 'q', NULL, 'q', -1, 'icon-xitongguanli', NULL, 999, '0', '0', '1', 'admin', '2022-02-07 14:30:04', 'admin', '2022-02-07 14:30:04');
INSERT INTO `sys_menu` VALUES (10010, 'sada', NULL, 'sad', -1, 'icon-jiaoseguanli', NULL, 999, '0', '0', '1', 'admin', '2022-02-07 15:05:02', 'admin', '2022-02-07 15:05:02');
INSERT INTO `sys_menu` VALUES (10011, 'sad', NULL, 'asd', -1, 'icon-web-icon-', NULL, 999, '0', '0', '1', 'admin', '2022-02-07 15:06:39', 'admin', '2022-02-07 15:06:39');
INSERT INTO `sys_menu` VALUES (10012, 'asd', NULL, 'asdas', -1, NULL, NULL, 999, '0', '0', '1', 'admin', '2022-02-07 15:07:14', 'admin', '2022-02-07 15:07:14');
INSERT INTO `sys_menu` VALUES (10013, 'as', NULL, '/as', -1, 'icon-jiaoseguanli', NULL, 999, '0', '0', '1', 'admin', '2022-02-09 15:46:39', 'admin', '2022-02-09 15:46:39');
INSERT INTO `sys_menu` VALUES (10014, 'sd', NULL, '/asw', 10013, 'icon-web-icon-', NULL, 999, '0', '0', '1', 'admin', '2022-02-09 15:49:32', 'admin', '2022-02-09 15:49:32');

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details`  (
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源列表',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端密钥',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '域',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '认证类型',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色列表',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT 'token 有效期',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT '刷新令牌有效期',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '令牌扩展字段JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否自动放行',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '终端信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details` VALUES ('app', NULL, 'app', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL);
INSERT INTO `sys_oauth_client_details` VALUES ('daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL);
INSERT INTO `sys_oauth_client_details` VALUES ('gen', NULL, 'gen', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL);
INSERT INTO `sys_oauth_client_details` VALUES ('pig', NULL, 'pig', 'server', 'password,app,refresh_token,authorization_code,client_credentials', 'http://localhost:4040/sso1/login,http://localhost:4041/sso1/login', NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL);
INSERT INTO `sys_oauth_client_details` VALUES ('test', NULL, 'test', 'server', 'password,app,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, '2022-01-23 14:06:07', NULL, 'admin');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `role_idx1_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'ROLE_ADMIN', '管理员', '0', '2017-10-29 15:45:51', '2018-12-26 14:09:11', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1000);
INSERT INTO `sys_role_menu` VALUES (1, 1100);
INSERT INTO `sys_role_menu` VALUES (1, 1101);
INSERT INTO `sys_role_menu` VALUES (1, 1102);
INSERT INTO `sys_role_menu` VALUES (1, 1103);
INSERT INTO `sys_role_menu` VALUES (1, 1104);
INSERT INTO `sys_role_menu` VALUES (1, 1200);
INSERT INTO `sys_role_menu` VALUES (1, 1201);
INSERT INTO `sys_role_menu` VALUES (1, 1202);
INSERT INTO `sys_role_menu` VALUES (1, 1203);
INSERT INTO `sys_role_menu` VALUES (1, 1300);
INSERT INTO `sys_role_menu` VALUES (1, 1301);
INSERT INTO `sys_role_menu` VALUES (1, 1302);
INSERT INTO `sys_role_menu` VALUES (1, 1303);
INSERT INTO `sys_role_menu` VALUES (1, 1304);
INSERT INTO `sys_role_menu` VALUES (1, 1400);
INSERT INTO `sys_role_menu` VALUES (1, 1401);
INSERT INTO `sys_role_menu` VALUES (1, 1402);
INSERT INTO `sys_role_menu` VALUES (1, 1403);
INSERT INTO `sys_role_menu` VALUES (1, 2000);
INSERT INTO `sys_role_menu` VALUES (1, 2100);
INSERT INTO `sys_role_menu` VALUES (1, 2101);
INSERT INTO `sys_role_menu` VALUES (1, 2102);
INSERT INTO `sys_role_menu` VALUES (1, 2300);
INSERT INTO `sys_role_menu` VALUES (1, 2301);
INSERT INTO `sys_role_menu` VALUES (1, 2400);
INSERT INTO `sys_role_menu` VALUES (1, 2401);
INSERT INTO `sys_role_menu` VALUES (1, 2402);
INSERT INTO `sys_role_menu` VALUES (1, 2403);
INSERT INTO `sys_role_menu` VALUES (1, 2500);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '随机盐',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
  `lock_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0-正常，9-锁定',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0-正常，1-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `user_idx1_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC', NULL, '17034642996', '', 1, '0', '0', '2018-04-20 07:15:18', '2022-01-22 20:09:47', NULL, 'root');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
