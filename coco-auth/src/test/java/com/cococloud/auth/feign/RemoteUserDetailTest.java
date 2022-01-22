package com.cococloud.auth.feign;

import com.cococloud.auth.service.CocoUserDetailsServiceImpl;
import com.cococloud.common.util.CommentResult;
import com.cococloud.upms.common.dto.UserDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;


@SpringBootTest
class RemoteUserDetailTest {

    @Autowired
    private RemoteUserDetail remoteUserDetail;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void testRemoteUserDetail() {
        CommentResult<UserDetailsDto> admin = remoteUserDetail.loadUser("root");
        System.out.println(admin.getData());
        System.out.println(userDetailsService.loadUserByUsername("root"));
    }
}