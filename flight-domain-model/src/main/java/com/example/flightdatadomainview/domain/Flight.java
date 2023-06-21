package com.example.flightdatadomainview.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
          private String icao24;
          private Long firstSeen;
          private String estDepartureAirport;
          private Long lastSeen;
          private String estArrivalAirport;
          private String callsign;
          private Integer estDepartureAirportHorizDistance;
          private Integer estDepartureAirportVertDistance;
          private Integer estArrivalAirportHorizDistance;
          private Integer estArrivalAirportVertDistance;
          private Integer departureAirportCandidatesCount;
          private Integer arrivalAirportCandidatesCount;

}
