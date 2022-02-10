package com.cococloud.security.exception;

import com.cococloud.common.constant.CommonConstans;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.io.IOException;

public class CocoOAuth2ExceptionSerializer extends StdSerializer<OAuth2Exception> {

    public CocoOAuth2ExceptionSerializer() {
        super(OAuth2Exception.class);
    }

    @Override
    public void serialize(OAuth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("code", CommonConstans.FAIL);
        gen.writeStringField("msg", value.getMessage());
        gen.writeStringField("data", value.getOAuth2ErrorCode());
        gen.writeStringField("error_description", value.getMessage());
        gen.writeStringField("error", value.getOAuth2ErrorCode());
        gen.writeEndObject();
    }
}
