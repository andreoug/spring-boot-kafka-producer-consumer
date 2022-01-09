package com.pilot.springbootkafkaproducerconsumer.service;

import com.pilot.commons.Sms;
import com.pilot.commons.SmsRule;
import com.pilot.springbootkafkaproducerconsumer.Producer;
import com.pilot.springbootkafkaproducerconsumer.web.SmsRequest;
import com.pilot.springbootkafkaproducerconsumer.web.SmsResponse;
import com.pilot.springbootkafkaproducerconsumer.web.SmsRuleRequest;
import com.pilot.springbootkafkaproducerconsumer.web.SmsRuleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by gandreou on 29/12/2021.
 */
@Service
@Profile("producer")
public class MesssageService {

    @Autowired
    Producer producer;

    public SmsResponse send(SmsRequest request) {
        Sms sms = new Sms(request.getBody());
        sms.setSender(request.getSender());
        sms.setReceiver(request.getReceiver());
        producer.send(sms);
        return new SmsResponse(200,null,
                request.getSender(), request.getReceiver());
    }

    public SmsRuleResponse send(SmsRuleRequest request) {
        SmsRule smsRule = new SmsRule(request.getVerb(),request.getAllSenders(),request.getReceiver());
        producer.send(smsRule);
        return new SmsRuleResponse(200,null, request.getVerb(),
                request.getAllSenders(), request.getReceiver());
    }
}
