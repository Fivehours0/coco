package com.cococloud.admin;

import com.cococloud.upms.common.feign.RemoteLogService;
import com.cococloud.upms.common.feign.RemoteTokenService;
import com.cococloud.upms.common.feign.RemoteUserDetail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableCaching
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(clients = {RemoteLogService.class, RemoteTokenService.class, RemoteUserDetail.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
