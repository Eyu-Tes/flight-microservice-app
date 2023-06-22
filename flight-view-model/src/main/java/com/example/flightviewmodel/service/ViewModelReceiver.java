package com.example.flightviewmodel.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ViewModelReceiver {
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(ViewModelReceiver.class);

    private final CsvWriter departureCsvWriter = new CsvWriter("departure.csv");
    private final CsvWriter arrivalCsvWriter = new CsvWriter("arrival.csv");

    public ViewModelReceiver() throws IOException {
    }

    @KafkaListener(topics = "ds_departure")
    public void receiveDepartureMsgs(@Payload String values) throws IOException {
//        JsonNode jsonNode = objectMapper.readTree(values);
//        List<String> stringList = new ArrayList<>();
//        for (JsonNode element : jsonNode) {
//            String value = element.asText();
//            stringList.add(value);
//        }
        List<String> stringList = objectMapper.readValue(values, new TypeReference<>() {});

        // Append the data to the respective CSV file based on the topic
        departureCsvWriter.appendData(stringList);
        logger.info("Departure Data: " + values);
    }

    @KafkaListener(topics = "ds_arrival")
    public void receiveArrivalMsgs(@Payload String values) throws IOException {
        List<String> stringList = objectMapper.readValue(values, new TypeReference<>() {});

        // Append the data to the respective CSV file based on the topic
        arrivalCsvWriter.appendData(stringList);
        logger.info("Arrival Data: " + values);
    }
}