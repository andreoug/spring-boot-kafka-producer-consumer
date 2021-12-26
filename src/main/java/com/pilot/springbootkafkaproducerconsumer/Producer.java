package com.pilot.springbootkafkaproducerconsumer;

import com.pilot.commons.Message;
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

    public void send(String m) {
        Message message = new Message(new Sms(m));
        log.info(String.format("#~#: Producing message -> %s", message));
        this.template.send(topic, message );
    }

}
