package com.cococloud.auth;

import com.cococloud.upms.common.feign.RemoteLogService;
import com.cococloud.upms.common.feign.RemoteUserDetail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(clients = {RemoteUserDetail.class, RemoteLogService.class})
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}
