package com.example.rtdis.service;

import com.example.rtdis.domain.Flight;
import com.example.rtdis.domain.Flights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    @Autowired
    private KafkaTemplate<String, Flights> kafkaTemplate;

    public void send(String topic, Flights flights){
        kafkaTemplate.send(topic, flights);
    }
}
