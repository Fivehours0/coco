package com.cococloud.security.component;

import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cococloud.security.annotation.SecurityIgnoreUrl;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Data
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class IgnoreUrlProperties implements InitializingBean, ApplicationContextAware {

    private final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private ApplicationContext applicationContext;

    private List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping map = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = map.getHandlerMethods();

        // 遍历所有的methods，找出被SecurityIgnoreUrl注解注释的方法
        handlerMethods.forEach((key, value) -> {
            SecurityIgnoreUrl methodAnnotation = AnnotationUtils.findAnnotation(value.getMethod(), SecurityIgnoreUrl.class);
            if (methodAnnotation != null) {
                key.getPathPatternsCondition().getPatterns()
                        .forEach(s -> urls.add(ReUtil.replaceAll(s.getPatternString(), PATTERN, StringPool.ASTERISK)));
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
