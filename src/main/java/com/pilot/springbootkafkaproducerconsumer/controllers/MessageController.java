package com.pilot.springbootkafkaproducerconsumer.controllers;

import com.pilot.springbootkafkaproducerconsumer.service.MesssageService;
import com.pilot.springbootkafkaproducerconsumer.web.SmsRequest;
import com.pilot.springbootkafkaproducerconsumer.web.SmsResponse;
import com.pilot.springbootkafkaproducerconsumer.web.SmsRuleRequest;
import com.pilot.springbootkafkaproducerconsumer.web.SmsRuleResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
@Profile("producer")
public class MessageController {

    final MesssageService messsageService;

    public MessageController(MesssageService messsageService) {
        this.messsageService = messsageService;
    }

    @PostMapping("/send-sms")
    public ResponseEntity<SmsResponse> sendSms(@RequestBody SmsRequest request) {
        return new ResponseEntity<>(messsageService.send(request), HttpStatus.OK);
    }

    @PostMapping("/send-rule")
    public ResponseEntity<SmsRuleResponse> sendSmsRule(@RequestBody SmsRuleRequest request) {
        return new ResponseEntity<>(messsageService.send(request), HttpStatus.OK);
    }

}
