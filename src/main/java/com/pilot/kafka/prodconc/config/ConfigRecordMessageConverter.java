package com.pilot.kafka.prodconc.config;

import com.pilot.kafka.prodconc.model.Message;
import com.pilot.kafka.prodconc.model.Sms;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.RecordMessageConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gandreou on 08/12/2021.
 */
@Configuration
@Profile("consumer")
public class ConfigRecordMessageConverter {

    @Bean
    public RecordMessageConverter converter() {
        ByteArrayJsonMessageConverter converter = new ByteArrayJsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("com.pilot.kafka.prodconc.model");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("message", Message.class);
        mappings.put("sms", Sms.class);
        typeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(typeMapper);
        return converter;
    }
}
