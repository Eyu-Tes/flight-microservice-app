package com.example.rtdis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RtdisService implements IRtdisService{
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private Sender sender;
    @Value("${opensky.base-url}")
    private String baseUrl;
    @Value("${spring.kafka.producer.topic}")
    private String kafkaTopic;

    @Scheduled(fixedRate = 1000000)
    public void fetchFlight() {
        String response = restTemplate.getForObject(baseUrl+"/api/flights/all?begin=1517227200&end=1517228200", String.class);
        sender.send(kafkaTopic, response);
    }
}
