package com.cococloud.gateway.filter;

import com.cococloud.common.constant.SecurityConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;


public class GatewayGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 去除请求头中from 参数
        request = exchange.getRequest().mutate()
                .headers(httpHeaders -> httpHeaders.remove(SecurityConstants.HEAD_INNER)).build();

        /*
         * 将gateway的StripPrefix过滤器功能应用至全局
         */
        addOriginalRequestUrl(exchange, request.getURI());
        String path = request.getURI().getRawPath();
        String newPath = "/"
                + Arrays.stream(StringUtils.tokenizeToStringArray(path, "/"))
                .skip(1L).collect(Collectors.joining("/"));
        newPath += (newPath.length() > 1 && path.endsWith("/") ? "/" : "");
        ServerHttpRequest newRequest = request.mutate().path(newPath).build();

        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR,
                newRequest.getURI());

        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
