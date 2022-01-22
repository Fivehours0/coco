package com.cococloud.auth.feign;

import com.cococloud.common.constant.ServerNameConstants;
import com.cococloud.upms.common.dto.UserDetailsDto;
import com.cococloud.common.util.CommentResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author dzh
 */
@FeignClient(value = ServerNameConstants.UPMS_NAME)
public interface RemoteUserDetail {

    @GetMapping("/user/info/{username}")
    CommentResult<UserDetailsDto> loadUser(@PathVariable("username") String username);

}
