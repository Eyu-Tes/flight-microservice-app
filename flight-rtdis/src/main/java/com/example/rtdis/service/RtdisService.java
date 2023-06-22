package com.example.rtdis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

@Service
public class RtdisService implements IRtdisService{
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private Sender sender;
    @Value("${externalApi.baseUrl}")
    private String baseUrl;
    @Value("${externalApi.accessKey}")
    private String accessUrl;
    @Value("${spring.kafka.producer.topic}")
    private String kafkaTopic;
    private static final Logger logger = LoggerFactory.getLogger(RtdisService.class);

    private int offset = 0;

    private final WebClient webClient = WebClient.create();

    @Scheduled(fixedRate = 10000)
    public void fetchFlight() {
        int maxLimit = 10;
        int minLimit = 5;
        int limit = new Random().nextInt((maxLimit - minLimit) + 1) + minLimit;

        webClient.get()
                .uri(baseUrl+"/v1/flights?access_key="+accessUrl+"&offset="+offset+"&limit="+ limit)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> sender.send(kafkaTopic, response));

        offset += limit;

        logger.info("Flight data fetched from AviationStack API", baseUrl, kafkaTopic);
    }
}
