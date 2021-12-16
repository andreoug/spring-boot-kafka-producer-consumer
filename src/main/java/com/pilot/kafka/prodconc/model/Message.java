package com.pilot.kafka.prodconc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pilot.kafka.prodconc.utils.Status;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by gandreou on 08/12/2021.
 */

@Data
@Getter
@NoArgsConstructor
public class Message {
    String id;
    Sms sms;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime created;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime updated;
    String status;
    public Message(Sms sms) {
        this.id = UUID.randomUUID().toString().split("-")[0];
        this.sms = sms;
        this.created = LocalDateTime.now();
        this.updated = this.created;
        this.status = Status.CREATED.toString();
    }

}
