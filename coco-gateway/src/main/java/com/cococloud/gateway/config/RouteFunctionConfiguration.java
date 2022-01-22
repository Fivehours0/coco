package com.cococloud.gateway.config;

import com.cococloud.gateway.handler.CaptchaHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class RouteFunctionConfiguration {

    private final CaptchaHandler captchaHandler;

    @Bean
    public RouterFunction<ServerResponse> captchRouter() {
        return RouterFunctions.route()
                .GET("/code", accept(MediaType.TEXT_PLAIN), captchaHandler::getCaptcha).build();
    }
}
