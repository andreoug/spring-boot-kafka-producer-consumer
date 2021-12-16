package com.pilot.springbootkafkaproducerconsumer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pilot.springbootkafkaproducerconsumer.Producer;

@RestController
@RequestMapping(value = "/kafka")
@Profile("producer")
public class MessageController {

    private final Producer producer;

    @Autowired
    MessageController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void send(@RequestParam("message") String message) {
        this.producer.send(message);
    }
}
