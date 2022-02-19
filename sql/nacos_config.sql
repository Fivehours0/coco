DROP DATABASE IF EXISTS `nacos_config`;

CREATE DATABASE  `nacos_config` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE nacos_config;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (2, 'application-dev.yaml', 'DEFAULT_GROUP', 'spring:\r\n  redis:\r\n    url: redis://:@coco-redis:6379\r\n\r\nsecurity:\r\n  oauth2:\r\n    ignore:\r\n      - /actuator/**\r\n\r\n# feign 配置\r\nfeign:\r\n  compression:\r\n    request:\r\n      enabled: true\r\n    response:\r\n      enabled: true', '1c13464e527a83e599808523d1fa8f15', '2022-01-27 17:42:31', '2022-02-14 22:46:16', NULL, '0:0:0:0:0:0:0:1', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (7, 'coco-auth-dev.yaml', 'DEFAULT_GROUP', 'spring:\r\n  datasource:\r\n    url: jdbc:mysql://coco-mysql:3306/coco?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.jdbc.Driver\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n\r\n  freemarker:\r\n    allow-request-override: false\r\n    allow-session-override: false\r\n    cache: true\r\n    charset: UTF-8\r\n    check-template-location: true\r\n    content-type: text/html\r\n    enabled: true\r\n    expose-request-attributes: false\r\n    expose-session-attributes: false\r\n    expose-spring-macro-helpers: true\r\n    prefer-file-system-access: true\r\n    suffix: .ftl\r\n    template-loader-path: classpath:/templates/\r\n    \r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \"*\"\r\n\r\n#logging:\r\n#  level:\r\n#    root: debug  # 指定日志基本\r\n#  file:\r\n#    path: E:\\logs\\zm-sms  # 指定日志输出的文件路径', 'd0586437c47c35293631284490e2a77e', '2022-01-27 16:55:00', '2022-02-08 15:31:08', NULL, '0:0:0:0:0:0:0:1', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (8, 'coco-gateway-dev.yaml', 'DEFAULT_GROUP', '# 服务名称\r\nspring:\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          # 开启从注册中心动态创建路由的功能，利用微服务名称进行路由\r\n          enabled: true\r\n\r\n      routes:\r\n        - id: coco-auth\r\n#          uri: http://localhost:3100\r\n          uri: lb://coco-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            - CaptchaVerificationFilter\r\n            - PasswordDecodeFilter\r\n        - id: coco-upms\r\n          uri: lb://coco-upms\r\n          predicates:\r\n            - Path=/admin/**\r\n\r\n# 暴露监控端点\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \"*\"\r\n\r\ncoco:\r\n  gateway:\r\n    encode-key: \'thanks,pig4cloud\'', '9970eebc6b6263dcde85bbd515608ac0', '2022-01-27 17:56:48', '2022-02-09 16:33:14', NULL, '0:0:0:0:0:0:0:1', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (9, 'coco-upms-dev.yaml', 'DEFAULT_GROUP', '# 服务名称\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://coco-mysql:3306/coco?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true\r\n    username: root\r\n    password: root\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n\r\nmybatis-plus:\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: delFlag # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\n#  configuration:\r\n#    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n\r\n# 暴露监控端点\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \"*\"', '8862327c15b093fb62b5362fead908f3', '2022-01-27 18:10:25', '2022-02-08 15:31:30', NULL, '0:0:0:0:0:0:0:1', '', '', 'null', 'null', 'null', 'yaml', 'null');

SET FOREIGN_KEY_CHECKS = 1;
