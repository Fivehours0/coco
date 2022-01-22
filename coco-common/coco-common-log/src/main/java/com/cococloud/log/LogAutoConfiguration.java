package com.cococloud.log;

import com.cococloud.log.aspect.SysLogAspect;
import com.cococloud.log.event.EventPublisher;
import com.cococloud.log.event.SysLogListener;
import com.cococloud.log.feign.RemoteLogService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author dzh
 */
@EnableFeignClients
@EnableAspectJAutoProxy
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

    private final RemoteLogService remoteLogService;

    @Bean
    public SysLogAspect sysLogAspect(EventPublisher publisher) {
        return new SysLogAspect(publisher);
    }

    @Bean
    public EventPublisher eventPublisher() {
        return new EventPublisher();
    }

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener(remoteLogService);
    }

}
