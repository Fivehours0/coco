package com.cococloud.upms.common.feign;

import com.cococloud.common.constant.ServerNameConstants;
import com.cococloud.common.util.CommentResult;
import com.cococloud.upms.common.entity.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(contextId ="remoteLogService", value = ServerNameConstants.UPMS_NAME)
public interface RemoteLogService {

    @PostMapping("/log")
    CommentResult<Boolean> saveLog(@Valid @RequestBody SysLog log);
}
