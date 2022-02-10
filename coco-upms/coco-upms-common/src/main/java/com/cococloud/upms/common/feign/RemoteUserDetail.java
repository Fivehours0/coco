package com.cococloud.upms.common.feign;

import com.cococloud.common.constant.SecurityConstants;
import com.cococloud.common.constant.ServerNameConstants;
import com.cococloud.common.util.CommentResult;
import com.cococloud.upms.common.dto.UserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author dzh
 */
@FeignClient(contextId ="remoteUserDetail", value = ServerNameConstants.UPMS_NAME)
public interface RemoteUserDetail {

    @GetMapping("/user/info/{username}")
    CommentResult<UserDetailsDto> loadUser(@PathVariable("username") String username,
                                           @RequestHeader(SecurityConstants.HEAD_INNER) String innerValue);

}
