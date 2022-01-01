package com.pilot.commons;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by gandreou on 27/12/2021.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    String id;
    Sms sms;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime created;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime updated;
    String status;
    public Action(Sms sms) {
        this.id = UUID.randomUUID().toString().split("-")[0];
        this.sms = sms;
        this.created = LocalDateTime.now();
        this.updated = this.created;
        this.status = Status.CREATED.toString();
    }
}
