package com.cococloud.gateway;

import com.cococloud.common.util.CommentResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ObjectMapperTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void objectMapperTest() throws JsonProcessingException {
        CommentResult<String> result = new CommentResult<>(404, "error", "no ok");
        System.out.println(Arrays.toString(objectMapper.writeValueAsBytes(result)));
    }
}
