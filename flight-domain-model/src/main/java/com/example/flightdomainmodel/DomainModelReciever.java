package com.example.flightdomainmodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainModelReciever {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private Sender sender;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void receive(@Payload String msg, @Headers MessageHeaders headers) throws JsonProcessingException {
        System.out.println("received message="+ msg);

        JsonNode jsonNode = objectMapper.readTree(msg);

        // Extract data array
        JsonNode dataArray = jsonNode.get("data");
        if (dataArray != null && dataArray.isArray()) {
            // Step 2: Split the JSON
            List<String> arrivalValues = new ArrayList<>();
            List<String> departureValues = new ArrayList<>();
            for (JsonNode dataObject : dataArray) {
                // Extract scheduled arrival and departure times
                String scheduledArrivalTime = dataObject.at("/arrival/scheduled").asText();
                String scheduledDepartureTime = dataObject.at("/departure/scheduled").asText();
                departureValues.add(scheduledDepartureTime);
                arrivalValues.add(scheduledArrivalTime);
            }
            // Step 3: Create JSON payloads
            String departureJson = objectMapper.writeValueAsString(departureValues);
            String arrivalJson = objectMapper.writeValueAsString(arrivalValues);
            // Step 4: Publish to Kafka topics
            sender.send("ds_departure", departureJson);
            sender.send("ds_arrival", arrivalJson);
        }
    }
}
