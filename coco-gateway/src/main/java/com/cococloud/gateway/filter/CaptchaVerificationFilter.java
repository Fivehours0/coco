package com.cococloud.gateway.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.cococloud.common.constant.CacheConstants;
import com.cococloud.common.constant.SecurityConstants;
import com.cococloud.common.util.CommentResult;
import com.cococloud.gateway.exception.CaptchaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * @author dzh
 */

@Slf4j
@AllArgsConstructor
public class CaptchaVerificationFilter extends AbstractGatewayFilterFactory<Object> {

    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            // 如果不是登录请求，则不进行验证码验证
            if (!StrUtil.contains(request.getURI().getPath(), SecurityConstants.REQUEST_TOKEN)) {
                return chain.filter(exchange);
            }
            // 如果是refresh_token的请求，则不进行验证码验证
            if (StrUtil.isNotBlank(request.getQueryParams().getFirst(SecurityConstants.REFRESH_TOKEN))) {
                return chain.filter(exchange);
            }
            try {
                checkCode(request);
            } catch (Exception e) {
                String errMessage = e.getMessage();

                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                return response.writeWith(Mono.create(mono -> {
                    try {
                        byte[] bytes = objectMapper.writeValueAsBytes(CommentResult.failed(errMessage));
                        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
                        mono.success(dataBuffer);
                    } catch (JsonProcessingException jsonProcessingException) {
                        log.error("验证码异常对象转化失败", jsonProcessingException);
                        mono.error(jsonProcessingException);
                    }
                }));
            }
            return chain.filter(exchange);
        };
    }

    private void checkCode(ServerHttpRequest request) {
        // 获取验证码信息
        String code = request.getQueryParams().getFirst("code");
        if (StrUtil.isBlank(code)) {
            throw new CaptchaException("验证码不能为空");
        }

        String randomStr = request.getQueryParams().getFirst("randomStr");
        String key = CacheConstants.DEFAULT_CODE_KEY + randomStr;
        Object codeSaved = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);

        if (ObjectUtil.isEmpty(codeSaved) || !StrUtil.equals((String) codeSaved, code)) {
            throw new CaptchaException("验证码不合法");
        }
    }
}
