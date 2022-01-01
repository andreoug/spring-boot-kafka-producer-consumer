package com.pilot.springbootkafkaproducerconsumer.controllers;

import com.pilot.commons.Sms;
import com.pilot.springbootkafkaproducerconsumer.service.MesssageService;
import com.pilot.springbootkafkaproducerconsumer.web.SmsRequest;
import com.pilot.springbootkafkaproducerconsumer.web.SmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pilot.springbootkafkaproducerconsumer.Producer;

@RestController
@RequestMapping(value = "/kafka")
@Profile("producer")
public class MessageController {

    private final Producer producer;

    @Autowired
    MesssageService messsageService;

    @Autowired
    MessageController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/send-sms")
    public ResponseEntity<SmsResponse> withdraw(@RequestBody SmsRequest request) {
        return new ResponseEntity<>(messsageService.send(request), HttpStatus.OK);
    }

}
