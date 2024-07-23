package com.chat.config;

import java.io.IOException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class GrantedAuthorityDeserializer extends StdDeserializer<GrantedAuthority> {
    public GrantedAuthorityDeserializer() {
        this(null);
    }

    public GrantedAuthorityDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public GrantedAuthority deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String authority = node.get("authority").asText();
        return new SimpleGrantedAuthority(authority);
    }
}
