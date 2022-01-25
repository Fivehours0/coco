package com.cococloud.upms.common.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.common.constant.ServerNameConstants;
import com.cococloud.common.util.CommentResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId ="remoteTokenService", value = ServerNameConstants.AUTH_NAME)
public interface RemoteTokenService {

    @PostMapping("/token/page")
    CommentResult<Page> getPageToken(@RequestBody Page page);

    @DeleteMapping("/token/{token}")
    CommentResult<Boolean> removeToken(@PathVariable("token") String token);

}
