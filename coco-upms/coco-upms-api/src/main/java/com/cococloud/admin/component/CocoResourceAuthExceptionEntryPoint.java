package com.cococloud.admin.component;

import cn.hutool.http.HttpStatus;
import com.cococloud.common.constant.CommonConstants;
import com.cococloud.common.util.CommentResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * AuthenticationEntryPoint 用以对 AuthenticationException进行处理，由ExceptionTranslationFilter调用处理
 * ExceptionTranslationFilter  用以处理认证异常和授权异常，其传入commence方法的异常类型为InsufficientAuthenticationException
 */
@AllArgsConstructor
public class CocoResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        CommentResult<String> result = new CommentResult<>();
        response.setCharacterEncoding(CommonConstants.UTF8);
        response.setContentType(CommonConstants.JSON_CONTENT_TYPE);
        result.setCode(CommonConstants.FAIL);

        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        if (authException != null) {
            result.setMsg("error");
            result.setData(authException.getMessage());
        }

        // 令牌失效返回特殊的 424
        if (authException instanceof InsufficientAuthenticationException) {
            response.setStatus(org.springframework.http.HttpStatus.FAILED_DEPENDENCY.value());
            result.setMsg("token expire");
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
