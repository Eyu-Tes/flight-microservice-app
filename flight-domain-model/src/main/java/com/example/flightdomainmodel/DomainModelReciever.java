package com.example.flightdomainmodel;

import com.example.flightdomainmodel.domain.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DomainModelReciever {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private Sender sender;

    private Logger logger = Logger.getLogger(DomainModelReciever.class.getName());

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void receive(@Payload String msg, @Headers MessageHeaders headers) throws JsonProcessingException {
        System.out.println("received message="+ msg);

        ObjectMapper objectMapper = new ObjectMapper();

        // Step 1: Deserialize the JSON
        Flight[] objects = objectMapper.readValue(msg, Flight[].class);

        // Step 2: Split the JSON
        List<Long> lastSeenValues = new ArrayList<>();
        List<Long> firstSeenValues = new ArrayList<>();

        for (Flight obj : objects) {
            lastSeenValues.add(obj.getFirstSeen());
            firstSeenValues.add(obj.getLastSeen());
        }

        // Step 3: Create JSON payloads
        String firstSeenJson = objectMapper.writeValueAsString(firstSeenValues);
        String lastSeenJson = objectMapper.writeValueAsString(lastSeenValues);

        // Step 4: Publish to Kafka topics
        kafkaTemplate.send("ds_firstSeen", firstSeenJson);
        kafkaTemplate.send("ds_lastSeen", lastSeenJson);

        logger.info("received message=" + msg);
    }
}
