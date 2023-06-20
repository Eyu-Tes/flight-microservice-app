package com.example.flightdatadomainview;

import jdk.jfr.FlightRecorder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DomainViewReciever {
    @KafkaListener(topics = "${kafka.topic}")
    public void receive(@Payload String string, @Headers MessageHeaders headers) {
        System.out.println("received message="+ string);

    }


}
