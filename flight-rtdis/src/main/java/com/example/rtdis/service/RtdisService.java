package com.example.rtdis.service;

import com.example.rtdis.domain.Flights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class RtdisService implements IRtdisService{
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private Sender sender;
//    @Value("${airLabs.api-key}")
//    private String apiKey;
//    @Value("${airLabs.base-url}")
//    private String baseUrl;

//    @Value("${ably.api_key}")
//    private String apiKey;
//    @Value("${ably.channel}")
//    private String channel;

    @Scheduled(fixedRate = 100000)
    public void fetchFlight() {
//        Flights flightList = restTemplate.getForObject(baseUrl+"/api/v9/flights?api_key="+apiKey, Flights.class);
//        sender.send("flight-stream-topic", flightList);

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("api-key", apiKey);
//        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(baseUrl + "/api/v9/flights", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {});
//        Map<String, Object> responseBody = responseEntity.getBody();
//
//        // Extract the desired portion from the response
//        assert responseBody != null;
//        List<Map<String, Object>> flights = (List<Map<String, Object>>) responseBody.get("response");

//        String url = String.format("%s/api/v9/flights?api_key=%s", baseUrl, apiKey);
//        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
//        Map responseBody = responseEntity.getBody();
//
//        // Extract the desired portion from the response
//        List<Map<String, Object>> flightData = (List<Map<String, Object>>) responseBody.get("response");


//        String encodedChannel = UriUtils.encodePathSegment(channel, StandardCharsets.UTF_8);

//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://rest.ably.io/channels/" + encodedChannel + "/messages";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(apiKey);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://opensky-network.org/api/flights/all?begin=1517227200&end=1517230200";



//        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
//        String response = responseEntity.getBody();

        Flights flightList = restTemplate.getForObject(url, Flights.class);
//        sender.send("flight-stream-topic", flightList);
    }
}
