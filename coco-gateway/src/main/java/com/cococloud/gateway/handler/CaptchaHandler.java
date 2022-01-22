package com.cococloud.gateway.handler;

import com.cococloud.common.constant.CacheConstants;
import com.cococloud.common.constant.CodeConstants;
import com.pig4cloud.captcha.SpecCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CaptchaHandler  {

    private final RedisTemplate<String, Object> redisTemplate;

    public Mono<ServerResponse> getCaptcha(ServerRequest request) {
        SpecCaptcha captcha = new SpecCaptcha(CodeConstants.DEFAULT_IMAGE_WIDTH, CodeConstants.DEFAULT_IMAGE_HEIGHT);
        // 设置字体
        captcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        // 设置类型，纯数字、纯字母、字母数字混合
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        captcha.setLen(CodeConstants.DEFAULT_CODE_LEN);
        String result = captcha.text();

        // 保存验证码信息
        Optional<String> randomStr = request.queryParam("randomStr");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        randomStr.ifPresent(s -> redisTemplate.opsForValue().set(CacheConstants.DEFAULT_CODE_KEY + s, result,
                CodeConstants.CODE_TIME, TimeUnit.SECONDS));

        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        captcha.out(os);

        return ServerResponse.ok().contentType(MediaType.IMAGE_GIF).cacheControl(CacheControl.noCache())
                .body(BodyInserters.fromValue(os.toByteArray()));
    }
}
