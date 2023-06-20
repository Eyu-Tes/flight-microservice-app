package com.example.rtdis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private long startTime = 1514815200;

    @Scheduled(fixedRate = 10000)
    public void fetchFlight() {
        startTime = startTime + 1020;
        long endTime = startTime + 1020;
        String response = restTemplate.getForObject(baseUrl+"/api/flights/all?begin="+startTime+"&end="+endTime, String.class);
        sender.send(kafkaTopic, response);
    }
}
