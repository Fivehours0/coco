package com.cococloud.admin.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class MyBatisPlusAutoGenerator {

    public static void main(String[] args) {
        AutoGenerator ag = new AutoGenerator();

        String url = "jdbc:mysql://localhost:3306/pig?useUnicode=true&useSSL=false&characterEncoding=utf8";
        String username = "root";
        String password = "root";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setDbType(DbType.MYSQL);
        ag.setDataSource(dataSourceConfig);

        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath+"/coco-upms/src/main/java");
        gc.setAuthor("dzh");
        gc.setOpen(false);
        // 去Service的I前缀
        gc.setServiceName("%sService");
        gc.setDateType(DateType.ONLY_DATE);
        gc.setBaseResultMap(true);

        ag.setGlobalConfig(gc);

        PackageConfig pc = new PackageConfig();
        pc.setModuleName("admin");
        pc.setParent("com.cococloud");
        ag.setPackageInfo(pc);

        StrategyConfig strategy = new StrategyConfig();
        // 设置要映射的表名
        strategy.setInclude("sys_dept",
                            "sys_dept_relation",
                            "sys_dict",
                            "sys_dict_item",
                            "sys_file",
                            "sys_log",
                            "sys_menu",
                            "sys_oauth_client_details",
                            "sys_role",
                            "sys_role_menu",
                            "sys_user",
                            "sys_user_role");
                strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 自动lombok；
        strategy.setEntityLombokModel(true);
        ag.setStrategy(strategy);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        //         如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/coco-upms/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        ag.setCfg(cfg);

        ag.execute();
    }
}
