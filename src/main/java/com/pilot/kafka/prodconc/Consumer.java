package com.pilot.kafka.prodconc;

import com.pilot.kafka.prodconc.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@Profile("consumer")
public class Consumer {

    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Message message) throws IOException {
        log.info(String.format("#~#: Consumed message -> %s", message));
    }
}
