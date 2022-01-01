package com.pilot.springbootkafkaproducerconsumer;

import com.pilot.commons.Action;
import com.pilot.commons.Sms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("producer")
public class Producer {

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<Object, Object> template;

    public void send(Sms sms) {
        Action action = new Action(sms);
        log.info(String.format("#~#: Producing action -> %s", action));
        this.template.send(topic, action );
    }
}
